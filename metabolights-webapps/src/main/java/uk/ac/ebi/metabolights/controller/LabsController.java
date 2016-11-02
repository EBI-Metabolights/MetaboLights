package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by venkata on 18/11/2015.
 */
@Controller
public class LabsController extends AbstractController {

    @RequestMapping(value = {"/labs", "/labs/login" })
    public ModelAndView showLabsPage(HttpServletRequest request) {

        ModelAndView mav = AppContext.getMAVFactory().getSimpleFrontierMav("labs");

        return mav;

    }

}