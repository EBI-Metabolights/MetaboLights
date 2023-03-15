package uk.ac.ebi.metabolights.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by venkata on 18/11/2015.
 */
// @Controller
// @RequestMapping("beta")
public class BetaController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(CompoundController.class);

    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|compoundId).+";


    // @RequestMapping(value = "/{compoundId:" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}")
    public ModelAndView showCompound(@PathVariable("compoundId") String mtblc, HttpServletRequest request) {

        logger.info("requested compound " + mtblc);

        String view =  "betacompound";

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(view);

        String metabolightsPythonWsUrl = EntryController.getMetabolightsPythonWsUrl();
        mav.addObject("compoundId", mtblc);
        mav.addObject("metabolightsPythonWsUrl", metabolightsPythonWsUrl);

        return mav;

    }

}