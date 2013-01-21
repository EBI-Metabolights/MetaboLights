/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsParametersController.java
 *
 * Last modified: 1/17/13 11:29 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 17/01/2013
 * Time: 11:15
 */
@Controller
public class MetaboLightsParametersController extends AbstractController {

    @RequestMapping({"/parameters"})
    public ModelAndView showMetaboLightsParameters() {

        ModelAndView mav = new ModelAndView ("parameters"); //name of jsp page must be same as this
        return mav;
    }
}
