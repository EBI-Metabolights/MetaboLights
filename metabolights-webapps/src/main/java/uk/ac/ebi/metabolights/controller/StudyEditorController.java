package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kalai on 20/04/2017.
 */
@Controller
public class StudyEditorController extends AbstractController {

    @RequestMapping(value = {"/studyeditor"})
    public ModelAndView showStudyEditorPage(@RequestParam(required=true,value="studyId") String studyId, HttpServletRequest request) {

//        ModelAndView mav = AppContext.getMAVFactory().getSimpleFrontierMav("studyeditor");
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("studyeditor");
        mav.addObject("studyId", studyId);
        MetabolightsUser user = LoginController.getLoggedUser();
        mav.addObject("apiToken", user.getApiToken());

        return mav;

    }
}
                                                                                                                                                                      s