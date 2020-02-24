/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-18
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.authenticate.AppRole;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Controller for entry (=study) details.
 */
@Controller
public class EntryController extends AbstractController {


    private static final String ALTERNATIVE_ENTRY_PREFIX = "";
    private static Logger logger = LoggerFactory.getLogger(EntryController.class);
    private
    @Value("#{OrcidClaimServiceURL}")
    String orcidServiceURL;
    private
    @Value("#{OrcidRetreiveClaimsURL}")
    String orcidRetreiveClaimsServiceURL;
    private static String wsUrl;
    private final String DESCRIPTION = "descr";

    @Autowired
    private UserService userService;

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
    public static final String REVIEWER_OBFUSCATION_CODE_URL = "reviewer{obfuscationCode}";
    private static MetabolightsWsClient metabolightsWsClient;

    public static MetabolightsWsClient getMetabolightsWsClient(String instance) {
        return null;
    }

    public enum PageActions {
        READ,
        EDIT
    }

    /**
     * To get the WS client...this is not the right place, should it be a service?
     */
    public static MetabolightsWsClient getMetabolightsWsClient() {
        return getMetabolightsWsClient(LoginController.getLoggedUser());
    }

    public static MetabolightsWsClient getMetabolightsWsClient(MetabolightsUser user) {

        //compose the ws url..
        String wsUrl = getWsPath();


        // If the user is null use the Logged user
        if (user == null) user = LoginController.getLoggedUser();

        return getMetabolightsWsClient(user.getApiToken(), wsUrl);
    }

    public static MetabolightsWsClient getMetabolightsWsClient(String user_token, String wsUrl) {

        MetabolightsWsClient wsClient = new MetabolightsWsClient(wsUrl);

        // Use user token ...
        wsClient.setUserToken(user_token);
        return wsClient;
    }


    private static String getWsPath() {

        if (wsUrl != null) return wsUrl;

        String host = PropertiesUtil.getHost();

        // Add the webservice part...
        wsUrl = composeWSPath(host);

        return wsUrl;

    }

    public static String composeWSPath(String host) {

        return host + "webservice/";
    }


    @RequestMapping(value = "/" + ALTERNATIVE_ENTRY_PREFIX + REVIEWER_OBFUSCATION_CODE_URL + "/assay/{assayNumber}/maf")
    public ModelAndView getAltReviewersMetabolitesIdentified(
            @PathVariable("obfuscationCode") String obfuscationCode,
            @PathVariable("assayNumber") int assayNumber) {

        return getMetabolitesModelAndView(null, assayNumber, obfuscationCode);
    }

    @RequestMapping(value = "/" + ALTERNATIVE_ENTRY_PREFIX + "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP + "}/assay/{assayNumber}/maf")
    public ModelAndView getAltMetabolitesIdentified(
            @PathVariable("studyIdentifier") String studyIdentifier,
            @PathVariable("assayNumber") int assayNumber) {

        return getMetabolitesModelAndView(studyIdentifier, assayNumber, null);

    }

    private ModelAndView getMetabolitesModelAndView(String mtblsId, int assayNumber, String obfuscationCode) {

        MetabolightsWsClient wsClient = getMetabolightsWsClient();


        RestResponse<MetaboliteAssignment> response;

        if (obfuscationCode == null) {
            response = wsClient.getMetabolites(mtblsId, assayNumber);
        } else {
            response = wsClient.getMetabolitesByObfuscationCode(obfuscationCode, assayNumber);
        }

        MetaboliteAssignment metaboliteAssignment = response.getContent();

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("metabolitesIdentified");

        mav.addObject("metaboliteAssignment", metaboliteAssignment);
        mav.addObject("assayNumber", assayNumber);

        return mav;
    }

    @RequestMapping(value = {"/" + ALTERNATIVE_ENTRY_PREFIX + REVIEWER_OBFUSCATION_CODE_URL})
    public ModelAndView showAltReviewerEntry(@PathVariable("obfuscationCode") String obfuscationCode) {


        return getWSEntryMAV(null, obfuscationCode, "study");

    }

    /**
     * Display a different screen if the user is not logged in and trying to access a private study
     *
     * @param mtblsId
     * @return
     */
    private ModelAndView notLoggedIn(String mtblsId) {

        /// The current user is not allowed to access the study...
        // If there isn't a logged in user...
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) {
            // redirect force login...
            return new ModelAndView("redirect:securedredirect?url=" + mtblsId);

            // The user is logged in but it's not authorised.
        } else {
            return new ModelAndView("redirect:/index?message=" + PropertyLookup.getMessage("msg.studyAccessRestricted") + " (" + mtblsId + ")");
        }

    }


    @RequestMapping(value = {"/" + ALTERNATIVE_ENTRY_PREFIX + "{metabolightsId:" + METABOLIGHTS_ID_REG_EXP + "}"})
    public ModelAndView showWSEntry(@PathVariable("metabolightsId") String mtblsId, HttpServletRequest request) {
        return getWSEntryMAV(mtblsId, null, "study", request);
    }

    @RequestMapping(value = {"beta/" + ALTERNATIVE_ENTRY_PREFIX + "{metabolightsId:" + METABOLIGHTS_ID_REG_EXP + "}"})
    public ModelAndView showStudyBetaPage(@PathVariable("metabolightsId") String mtblsId, HttpServletRequest request) {
        return getWSEntryMAV(mtblsId, null, "study", request);
    }

    private ModelAndView getWSEntryMAV(String mtblsId, String obfuscationCode, String view, HttpServletRequest request) {
        ModelAndView modelAndView = getWSEntryMAV(mtblsId, obfuscationCode, view);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("currentpage", mtblsId);
        return modelAndView;
    }

    private ModelAndView getWSEntryMAV(String mtblsId, String obfuscationCode, String view) {

        // Get the user
        MetabolightsUser user = LoginController.getLoggedUser();

        MetabolightsWsClient wsClient = getMetabolightsWsClient(user);


        RestResponse<uk.ac.ebi.metabolights.repository.model.Study> response;

        if (obfuscationCode == null) {

            logger.info("requested entry " + mtblsId);

            mtblsId = mtblsId.toUpperCase(); //This method maps to both MTBLS and mtbls, so make sure all further references are to MTBLS

            response = wsClient.getStudy(mtblsId);
        } else {

            logger.info("requested entry by obfuscation " + obfuscationCode);

            response = wsClient.getStudybyObfuscationCode(obfuscationCode);
        }

        uk.ac.ebi.metabolights.repository.model.Study study = response.getContent();


        // In case of reviewer mode, the user will not be anonymous.
        // Change: ws is not returning the study anymore if private it returns null and an error message/object
        // For now I'm assuming if it's null == it's private. Bu we may want to check the error message instead.
        if (study == null) {

            if (user.getUserName().equals(LoginController.ANONYMOUS_USER.toLowerCase())) {
                return notLoggedIn(ALTERNATIVE_ENTRY_PREFIX + mtblsId);
            }

            // study is null because it couldn't be found by the WS
            if (response.getMessage().equalsIgnoreCase("Study not found")) {
                return new ModelAndView("redirect:/index?message=" + mtblsId + " can not be accessed or does not exist");
                //return new ModelAndView("redirect:/errors/404");
            }

            // study is null because any other reason
            return new ModelAndView("redirect:/errors/500");

        }

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(view);

        mav.addObject("pageTitle", study.getStudyIdentifier() + ":" + study.getTitle());

        mav.addObject("study", study);

        if (obfuscationCode != null) {

            logger.info("adding the parameter obfuscation code " + obfuscationCode);

            mav.addObject("obfuscationCode", obfuscationCode);

        } else {

            logger.info("obfuscation code not found setting the parameter to null");

            mav.addObject("obfuscationCode", "null");

        }

        mav.addObject("liteStudy", wsClient.searchStudy(study.getStudyIdentifier()));

        mav.addObject("studyStatuses", LiteStudy.StudyStatus.values());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(study.getValidations());
            mav.addObject("validationJson", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        // Things that don't come from the web service:
        FileDispatcherController fdController = new FileDispatcherController();

        mav.addObject("files", fdController.getStudyFileList(study.getStudyIdentifier()));

        // Applies only to studies with status SUBMITTED, when accessed by a user with roles CURATOR or SUBMITTER
        if (study.getStudyStatus() == Study.StudyStatus.SUBMITTED && (user.isCurator() || user.getRole() == AppRole.ROLE_SUBMITTER.ordinal())) {
            mav.addObject("ftpFiles", fdController.getPrivateFtpFileList(study.getStudyIdentifier()));
            mav.addObject("hasPrivateFtpFolder", fdController.hasPrivateFtpFolder(study.getStudyIdentifier()));
        }

        if (user.isCurator()) {
            mav.addObject("curatorAPIToken", user.getApiToken());
        }
        if (user != null) {
            mav.addObject("editorToken", user.getApiToken());
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(study.getStudyPublicReleaseDate());
        mav.addObject("releaseYear", calendar.get(Calendar.YEAR));
        mav.addObject("userOrcidID", user.getOrcId());
        mav.addObject("orcidServiceUrl", orcidServiceURL);
        mav.addObject("orcidRetrieveClaimsServiceUrl", orcidRetreiveClaimsServiceURL);
        mav.addObject("userHasEditRights", canEditQuickly(user, study));


        return mav;
    }

    public static boolean canUserEditStudy(String study) {
        return canUserDoThisToStudy(study, null, PageActions.EDIT);
    }


    /**
     * @param studyId
     * @param user
     * @param action  - Actions could be "EDIT" or "READ"
     * @return
     */
    public static boolean canUserDoThisToStudy(String studyId, MetabolightsUser user, PageActions action) {

        // Get the user if not passsed
        if (user == null) {

            user = LoginController.getLoggedUser();
        }

        // Return true if curator: curators can do anything.
        if (user.isCurator()) {
            return true;
        } else {

            // Get the study
            LiteStudy study = EntryController.getMetabolightsWsClient().searchStudy(studyId);

            boolean userOwnstudy = doesUserOwnsTheStudy(user.getUserName(), study);

            // If action READ
            if (action == PageActions.READ) {

                // If public...
                if (study.isPublicStudy()) {
                    // Allowed to any.
                    return true;
                } else {

                    // Allowed to owner only.
                    return userOwnstudy;
                }
                // Write action or null or whatever (more restrictive), just in case.
            } else {

                return userOwnstudy;

            }
        }
    }

    public static boolean doesUserOwnsTheStudy(String userName, LiteStudy study) {

        for (User user : study.getUsers()) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }

        return false;
    }

    public static boolean canEdit(MetabolightsUser user, LiteStudy study) {
        if (user.isCurator()) {
            return true;
        } else {
            return doesUserOwnsTheStudy(user.getUserName(), study);
        }
    }

    /**
     * @param studyId
     * @return
     */
    public static boolean canUserQuicklyEditThisToStudy(String studyId) {


        MetabolightsUser user = LoginController.getLoggedUser();

        // Return true if curator: curators can do anything.
        if (user.isCurator()) {
            return true;
        } else {

            // Get the study
            MetabolightsWsClient wsClient = getMetabolightsWsClient(user);
            RestResponse<uk.ac.ebi.metabolights.repository.model.Study> response = wsClient.getStudy(studyId);
            uk.ac.ebi.metabolights.repository.model.Study study = response.getContent();
            return canEditQuickly(user, study);
        }
    }

    public static boolean canEditQuickly(MetabolightsUser user, LiteStudy study) {
        if (user.isCurator()) {
            return true;
        } else {
            boolean userOwnstudy = doesUserOwnsTheStudy(user.getUserName(), study);
            // Study is in Submitted status
            if (userOwnstudy) {
                if (study.getStudyStatus().equals(LiteStudy.StudyStatus.SUBMITTED)) {
                    return userOwnstudy;
                }
            }
        }
        return false;
    }

}
