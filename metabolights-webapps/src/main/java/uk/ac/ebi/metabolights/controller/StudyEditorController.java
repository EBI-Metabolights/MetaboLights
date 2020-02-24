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

    @RequestMapping(value = {"/editor" , "/editor/login", "/editor/study/{studyId}", "/editor/study/{studyId}/{tabId}", "/editor/console", "/editor/create", "/editor/guide/{studyId}", "/editor/guide/{studyId}/{step}", "/editor/upload/{studyId}", "/editor/info/{studyId}", "/editor/assays/{studyId}", "/editor/validate/{studyId}", "/editor/submit/{studyId}" })
    public ModelAndView showLabsPage(HttpServletRequest request) {
        ModelAndView mav = AppContext.getMAVFactory().getSimpleFrontierMav("editor");
        MetabolightsUser user = LoginController.getLoggedUser();
        mav.addObject("editorToken", "{\"apiToken\":\"" + user.getApiToken() +"\"}");
        return mav;
    }
}
