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
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import org.jose4j.keys.HmacKey;
import org.slf4j.LoggerFactory;
import org.jose4j.jwt.JwtClaims;
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
        private static Logger logger;
        @Value("#{OrcidClaimServiceURL}")
        private String orcidServiceURL;
        @Value("#{OrcidRetreiveClaimsURL}")
        private String orcidRetreiveClaimsServiceURL;
        private static String wsUrl;
        private final String DESCRIPTION = "descr";
        @Autowired
        private UserService userService;
        public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
        public static final String REVIEWER_OBFUSCATION_CODE_URL = "reviewer{obfuscationCode}";
        private static MetabolightsWsClient metabolightsWsClient;

        public static MetabolightsWsClient getMetabolightsWsClient(final String instance) {
            return null;
        }

        public static MetabolightsWsClient getMetabolightsWsClient() {
            return getMetabolightsWsClient(LoginController.getLoggedUser());
        }

        public static MetabolightsWsClient getMetabolightsWsClient(MetabolightsUser user) {
            final String wsUrl = getWsPath();
            if (user == null) {
                user = LoginController.getLoggedUser();
            }
            return getMetabolightsWsClient(user.getApiToken(), wsUrl);
        }

        public static MetabolightsWsClient getMetabolightsWsClient(final String user_token, final String wsUrl) {
            final MetabolightsWsClient wsClient = new MetabolightsWsClient(wsUrl);
            wsClient.setUserToken(user_token);
            return wsClient;
        }

        public enum PageActions {
            READ,
            EDIT
        }

        private static String getWsPath() {
            if (EntryController.wsUrl != null) {
                return EntryController.wsUrl;
            }
            final String host = PropertiesUtil.getHost();
            return EntryController.wsUrl = composeWSPath(host);
        }

        public static String composeWSPath(final String host) {
            return host + "webservice/";
        }

        @RequestMapping({ "/reviewer{obfuscationCode}/assay/{assayNumber}/maf" })
        public ModelAndView getAltReviewersMetabolitesIdentified(@PathVariable("obfuscationCode") final String obfuscationCode, @PathVariable("assayNumber") final int assayNumber) {
            return this.getMetabolitesModelAndView(null, assayNumber, obfuscationCode);
        }

        @RequestMapping({ "/{studyIdentifier:(?:MTBLS|mtbls).+}/assay/{assayNumber}/maf" })
        public ModelAndView getAltMetabolitesIdentified(@PathVariable("studyIdentifier") final String studyIdentifier, @PathVariable("assayNumber") final int assayNumber) {
            return this.getMetabolitesModelAndView(studyIdentifier, assayNumber, null);
        }

        private ModelAndView getMetabolitesModelAndView(final String mtblsId, final int assayNumber, final String obfuscationCode) {
            final MetabolightsWsClient wsClient = getMetabolightsWsClient();
            RestResponse<MetaboliteAssignment> response;
            if (obfuscationCode == null) {
                response = (RestResponse<MetaboliteAssignment>)wsClient.getMetabolites(mtblsId, assayNumber);
            }
            else {
                response = (RestResponse<MetaboliteAssignment>)wsClient.getMetabolitesByObfuscationCode(obfuscationCode, assayNumber);
            }
            final MetaboliteAssignment metaboliteAssignment = (MetaboliteAssignment)response.getContent();
            final ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("metabolitesIdentified");
            mav.addObject("metaboliteAssignment", (Object)metaboliteAssignment);
            mav.addObject("assayNumber", (Object)assayNumber);
            return mav;
        }

        @RequestMapping({ "/reviewer{obfuscationCode}" })
        public ModelAndView showAltReviewerEntry(@PathVariable("obfuscationCode") final String obfuscationCode) {
            final MetabolightsUser user = LoginController.getLoggedUser();
            final MetabolightsWsClient wsClient = getMetabolightsWsClient(user);
            final ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("study");
            String studyId = null;
            Study study = null;
            RestResponse<Study> response;
            if (obfuscationCode != null) {
                EntryController.logger.info("requested entry by obfuscation " + obfuscationCode);
                response = (RestResponse<Study>) wsClient.getStudybyObfuscationCode(obfuscationCode);

                study = (Study)response.getContent();

                studyId = study.getStudyIdentifier();

                if (studyId == null || study.getStudyStatus() != Study.StudyStatus.INREVIEW) {
                    return new ModelAndView("redirect:/index?message=Study can not be accessed or does not exist");
                }
            }

            if(studyId != null){
                mav.addObject("obfuscationCode", obfuscationCode);
                mav.addObject("studyId", studyId);
                return mav;
            }

            return new ModelAndView("redirect:/errors/500");
        }

        private ModelAndView notLoggedIn(final String mtblsId) {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getPrincipal().equals("anonymousUser")) {
                return new ModelAndView("redirect:securedredirect?url=" + mtblsId);
            }
            return new ModelAndView("redirect:/index?message=" + PropertyLookup.getMessage("msg.studyAccessRestricted") + " (" + mtblsId + ")");
        }

        @RequestMapping({ "/{metabolightsId:(?:MTBLS|mtbls).+}", "/{metabolightsId:(?:MTBLS|mtbls).+}/{tabId}" })
        private ModelAndView getStudyWSEntryMAV(@PathVariable("metabolightsId") String mtblsId, final HttpServletRequest request) {
            final MetabolightsUser user = LoginController.getLoggedUser();
            final MetabolightsWsClient wsClient = getMetabolightsWsClient(user);
            mtblsId = mtblsId.toUpperCase();
            boolean isOwner = false;
            boolean isCurator = false;
            final RestResponse<Study> response = (RestResponse<Study>)wsClient.getStudy(mtblsId);
            if (mtblsId == null) {
                if (user.getUserName().equals("anonymousUser".toLowerCase())) {
                    return this.notLoggedIn("" + mtblsId);
                }
                if (response.getMessage().equalsIgnoreCase("Study not found")) {
                    return new ModelAndView("redirect:/index?message=" + mtblsId + " can not be accessed or does not exist");
                }
                return new ModelAndView("redirect:/errors/500");
            }
            else {
                final Study study = (Study)response.getContent();
                if (study == null) {
                    if (user.getUserName().equals("anonymousUser".toLowerCase())) {
                        return this.notLoggedIn("" + mtblsId);
                    }
                    if (response.getMessage().equalsIgnoreCase("Study not found")) {
                        return new ModelAndView("redirect:/index?message=" + mtblsId + " can not be accessed or does not exist");
                    }
                    return new ModelAndView("redirect:/errors/500");
                }
                else {
                    if (study != null && !study.isPublicStudy() && user.getUserName().equals("anonymousUser".toLowerCase())) {
                        return new ModelAndView("redirect:/securedredirect?url=" + mtblsId);
                    }
                    final ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("study");
                    if (!user.getUserName().equals("anonymousUser".toLowerCase())) {
                        final JwtClaims claims = new JwtClaims();
                        claims.setSubject(user.getEmail());
                        claims.setIssuer("Metabolights");
                        claims.setAudience("Metabolights Labs");
                        claims.setClaim("Name", (Object)user.getFullName());
                        Key key = null;
                        final String token = user.getApiToken();
                        try {
                            key = (Key)new HmacKey(token.getBytes("UTF-8"));
                        }
                        catch (UnsupportedEncodingException e) {
                            return new ModelAndView("redirect:/errors/500");
                        }
                        final JsonWebSignature jws = new JsonWebSignature();
                        jws.setPayload(claims.toJson());
                        jws.setAlgorithmHeaderValue("HS256");
                        jws.setKey(key);
                        jws.setDoKeyValidation(false);
                        String jwt = null;
                        try {
                            jwt = jws.getCompactSerialization();
                        }
                        catch (JoseException e2) {
                            return new ModelAndView("redirect:/errors/500");
                        }


                        if(doesUserOwnsStudy(study, user)){
                            isOwner = true;
                        }
                        if(user.isCurator()){
                            isCurator = true;
                        }

                        mav.addObject("isOwner", (Object)(isOwner));
                        mav.addObject("isCurator", (Object)(isCurator));
                        mav.addObject("jwt", (Object)jwt);
                        mav.addObject("email", (Object)user.getEmail());
                    }
                    return mav;
                }
            }
        }

        private boolean doesUserOwnsStudy(Study study, MetabolightsUser user){

            for (User owner : study.getUsers()) {
                if (owner.getUserName().equals(user.getUserName())) {
                    return true;
                }
            }

            return false;
        }


        private ModelAndView getWSEntryMAV(final String mtblsId, final String obfuscationCode, final String view, final HttpServletRequest request) {
            final ModelAndView modelAndView = this.getWSEntryMAV(mtblsId, obfuscationCode, view);
            final HttpSession httpSession = request.getSession();
            httpSession.setAttribute("currentpage", (Object)mtblsId);
            return modelAndView;
        }

        private ModelAndView getWSEntryMAV(String mtblsId, final String obfuscationCode, final String view) {
            final MetabolightsUser user = LoginController.getLoggedUser();
            final MetabolightsWsClient wsClient = getMetabolightsWsClient(user);
            RestResponse<Study> response;
            if (obfuscationCode == null) {
                EntryController.logger.info("requested entry " + mtblsId);
                mtblsId = mtblsId.toUpperCase();
                response = (RestResponse<Study>)wsClient.getStudy(mtblsId);
            }
            else {
                EntryController.logger.info("requested entry by obfuscation " + obfuscationCode);
                response = (RestResponse<Study>)wsClient.getStudybyObfuscationCode(obfuscationCode);
            }
            final Study study = (Study)response.getContent();
            if (study != null) {
                final ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(view);
                mav.addObject("pageTitle", (Object)(study.getStudyIdentifier() + ":" + study.getTitle()));
                mav.addObject("study", (Object)study);
                if (obfuscationCode != null) {
                    EntryController.logger.info("adding the parameter obfuscation code " + obfuscationCode);
                    mav.addObject("obfuscationCode", (Object)obfuscationCode);
                }
                else {
                    EntryController.logger.info("obfuscation code not found setting the parameter to null");
                    mav.addObject("obfuscationCode", (Object)"null");
                }
                mav.addObject("liteStudy", (Object)wsClient.searchStudy(study.getStudyIdentifier()));
                mav.addObject("studyStatuses", (Object)LiteStudy.StudyStatus.values());
                final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                try {
                    final String json = ow.writeValueAsString((Object)study.getValidations());
                    mav.addObject("validationJson", (Object)json);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                final FileDispatcherController fdController = new FileDispatcherController();
                mav.addObject("files", (Object)fdController.getStudyFileList(study.getStudyIdentifier()));
                if (study.getStudyStatus() == LiteStudy.StudyStatus.SUBMITTED && (user.isCurator() || user.getRole() == AppRole.ROLE_SUBMITTER.ordinal())) {
                    mav.addObject("ftpFiles", (Object)fdController.getPrivateFtpFileList(study.getStudyIdentifier()));
                    mav.addObject("hasPrivateFtpFolder", (Object)fdController.hasPrivateFtpFolder(study.getStudyIdentifier()));
                }
                if (user.isCurator()) {
                    mav.addObject("curatorAPIToken", (Object)user.getApiToken());
                }
                if (user != null) {
                    mav.addObject("editorToken", (Object)user.getApiToken());
                }
                final Calendar calendar = new GregorianCalendar();
                calendar.setTime(study.getStudyPublicReleaseDate());
                mav.addObject("releaseYear", (Object)calendar.get(1));
                mav.addObject("userOrcidID", (Object)user.getOrcId());
                mav.addObject("orcidServiceUrl", (Object)this.orcidServiceURL);
                mav.addObject("orcidRetrieveClaimsServiceUrl", (Object)this.orcidRetreiveClaimsServiceURL);
                mav.addObject("userHasEditRights", (Object)canEditQuickly(user, (LiteStudy)study));
                return mav;
            }
            if (user.getUserName().equals("anonymousUser".toLowerCase())) {
                return this.notLoggedIn("" + mtblsId);
            }
            if (response.getMessage().equalsIgnoreCase("Study not found")) {
                return new ModelAndView("redirect:/index?message=" + mtblsId + " can not be accessed or does not exist");
            }
            return new ModelAndView("redirect:/errors/500");
        }

        public static boolean canUserEditStudy(final String study) {
            return canUserDoThisToStudy(study, null, EntryController.PageActions.EDIT);
        }

        public static boolean canUserDoThisToStudy(final String studyId, MetabolightsUser user, final EntryController.PageActions action) {
            if (user == null) {
                user = LoginController.getLoggedUser();
            }
            if (user.isCurator()) {
                return true;
            }
            final LiteStudy study = getMetabolightsWsClient().searchStudy(studyId);
            final boolean userOwnstudy = doesUserOwnsTheStudy(user.getUserName(), study);
            if (action == EntryController.PageActions.READ) {
                return study.isPublicStudy() || userOwnstudy;
            }
            return userOwnstudy;
        }

        public static boolean doesUserOwnsTheStudy(final String userName, final LiteStudy study) {
            for (final User user : study.getUsers()) {
                if (user.getUserName().equals(userName)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean canEdit(final MetabolightsUser user, final LiteStudy study) {
            return user.isCurator() || doesUserOwnsTheStudy(user.getUserName(), study);
        }

        public static boolean canUserQuicklyEditThisToStudy(final String studyId) {
            final MetabolightsUser user = LoginController.getLoggedUser();
            if (user.isCurator()) {
                return true;
            }
            final MetabolightsWsClient wsClient = getMetabolightsWsClient(user);
            final RestResponse<Study> response = (RestResponse<Study>)wsClient.getStudy(studyId);
            final Study study = (Study)response.getContent();
            return canEditQuickly(user, (LiteStudy)study);
        }

        public static boolean canEditQuickly(final MetabolightsUser user, final LiteStudy study) {
            if (user.isCurator()) {
                return true;
            }
            final boolean userOwnstudy = doesUserOwnsTheStudy(user.getUserName(), study);
            return userOwnstudy && study.getStudyStatus().equals((Object)LiteStudy.StudyStatus.SUBMITTED) && userOwnstudy;
        }

        static {
            EntryController.logger = LoggerFactory.getLogger((Class)EntryController.class);
        }

    }