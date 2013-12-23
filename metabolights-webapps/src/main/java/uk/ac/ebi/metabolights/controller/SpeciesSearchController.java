package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.service.AppContext;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
public class SpeciesSearchController extends AbstractController {

    private static Logger logger = Logger.getLogger(SpeciesSearchController.class);



    @RequestMapping(value = "/species")
    public ModelAndView showSpecies() {


        //ModelAndView mav = new ModelAndView("compound");
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("species");

        return mav;

    }

}