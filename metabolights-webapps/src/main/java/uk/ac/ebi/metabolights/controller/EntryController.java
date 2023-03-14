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
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
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
import org.springframework.web.servlet.view.RedirectView;

import uk.ac.ebi.metabolights.authenticate.IsaTabAuthenticationProvider;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.servlet.http.HttpServletRequest;

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
        private static String editorUrl = null;
        private static String pythonWsUrl = null;
        @Autowired
        private UserService userService;
        public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
        public static final String REVIEWER_OBFUSCATION_CODE_URL = "reviewer{obfuscationCode}";


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
            final MetabolightsWsClient wsClient = MetabolightsWsClient.getInstance(wsUrl);
            wsClient.setUserToken(user_token);
            return wsClient;
        }

        public enum PageActions {
            READ,
            EDIT
        }

        public static String getMetabolightsEditorUrl(){
            if (EntryController.editorUrl != null) {
                return EntryController.editorUrl;
            }
            EntryController.editorUrl = PropertiesUtil.getProperty("metabolightsEditorUrl");
            return EntryController.editorUrl;
        }

        public static String getMetabolightsPythonWsUrl(){
            if (EntryController.pythonWsUrl != null) {
                return EntryController.pythonWsUrl;
            }
            EntryController.pythonWsUrl = PropertiesUtil.getProperty("metabolightsPythonWsUrl");
            return EntryController.pythonWsUrl;
        }

        private static String getWsPath() {
            if (EntryController.wsUrl != null) {
                return EntryController.wsUrl;
            }
            final String host = PropertiesUtil.getProperty("metabolightsJavaWsUrl");
             return composeWSPath(host);
             
        }

        public static String composeWSPath(final String host) {
            if (host.endsWith("/"))
                return host + "webservice/";
            else
                return host + "/webservice/";
        }

        // @RequestMapping({ "/reviewer{obfuscationCode}/assay/{assayNumber}/maf" })
        // public ModelAndView getAltReviewersMetabolitesIdentified(@PathVariable("obfuscationCode") final String obfuscationCode, @PathVariable("assayNumber") final int assayNumber) {
        //     return this.getMetabolitesModelAndView(null, assayNumber, obfuscationCode);
        // }

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
         
        @RequestMapping(value = {"/reviewer*", "/reviewer*/", "/reviewer*/**"})
        public RedirectView showLabsPage(HttpServletRequest request) {
            RedirectView redirectView = new RedirectView();
            String contextPath = request.getServletContext().getContextPath();
            String path = request.getRequestURI().replace(contextPath, "");

            String url = getMetabolightsEditorUrl() + path;
            try {
                MetabolightsUser user = LoginController.getLoggedUser();
                if(user != null && user.getJwtToken() != null){
                    String loginOneTimeToken = IsaTabAuthenticationProvider.getLoginOneTimeToken(user);

                    if(loginOneTimeToken != null){
                        url += "?loginOneTimeToken=" + loginOneTimeToken;
                        redirectView.setUrl(url);
                        return redirectView;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            redirectView.setUrl(url);
            return redirectView;
        }
        // @RequestMapping({ "/reviewer{obfuscationCode}" })
        public ModelAndView showAltReviewerEntryOld(@PathVariable("obfuscationCode") final String obfuscationCode) {
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
        private RedirectView getStudyWSEntryMAV(@PathVariable("metabolightsId") String mtblsId, final HttpServletRequest request) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(getMetabolightsEditorUrl() + "/" + mtblsId);
            try {
                MetabolightsUser user = LoginController.getLoggedUser();
                if(user != null && user.getJwtToken() != null){
                    String loginOneTimeToken = IsaTabAuthenticationProvider.getLoginOneTimeToken(user);

                    if(loginOneTimeToken != null){
                        redirectView.setUrl(getMetabolightsEditorUrl() + "/" + mtblsId + "?loginOneTimeToken=" + loginOneTimeToken);
                        return redirectView;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return redirectView;
        }

        //@RequestMapping({ "/{metabolightsId:(?:MTBLS|mtbls).+}", "/{metabolightsId:(?:MTBLS|mtbls).+}/{tabId}" })
        private ModelAndView getStudyWSEntryMAVOld(@PathVariable("metabolightsId") String mtblsId, final HttpServletRequest request) {
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


                        if(doesUserOwnsStudy(study, user)){
                            isOwner = true;
                        }
                        if(user.isCurator()){
                            isCurator = true;
                        }

                        mav.addObject("isOwner", (Object)(isOwner));
                        mav.addObject("isCurator", (Object)(isCurator));
                        mav.addObject("jwt", (Object) user.getJwtToken());
                        mav.addObject("email", (Object)user.getUserName());                        
                        mav.addObject("jwtExpirationTime", (Object)user.getJwtTokenExpireTime());
                        
                        mav.addObject("localUser", (Object)user.getLocalUserData());
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

        static {
            EntryController.logger = LoggerFactory.getLogger((Class)EntryController.class);
        }

    }