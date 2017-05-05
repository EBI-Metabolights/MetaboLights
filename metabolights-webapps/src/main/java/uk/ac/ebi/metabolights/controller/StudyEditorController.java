package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kalai on 20/04/2017.
 */
@Controller
public class StudyEditorController extends AbstractController {

    @RequestMapping(value = {"/studyeditor"})
    public ModelAndView showStudyEditorPage(HttpServletRequest request) {

        ModelAndView mav = AppContext.getMAVFactory().getSimpleFrontierMav("studyeditor");

        return mav;

    }
}
