package uk.ac.ebi.metabolights.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kalai on 20/04/2017.
 */
@Controller
public class StudyEditorController extends AbstractController {


    @RequestMapping(value = {"/studyeditor"})
    public ModelAndView showStudyEditorPage(@RequestParam(required = true, value = "studyId") String studyId, HttpServletRequest request) {
        MetabolightsUser user = LoginController.getLoggedUser();
        if (user.getUserName().equals(LoginController.ANONYMOUS_USER.toLowerCase())) {
            return notLoggedIn(studyId);
        }
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("studyeditor");
        mav.addObject("studyId", studyId);
        if (EntryController.canUserQuicklyEditThisToStudy(studyId)) {
            mav.addObject("apiToken", user.getApiToken());
            mav.addObject("setApiToken", true);
        }else{
            mav.addObject("setApiToken", false);
        }

        return mav;

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

}
                                                                                                                                                                      