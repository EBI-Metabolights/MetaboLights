package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.WebCompound;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by venkata on 18/11/2015.
 */
@Controller
@RequestMapping("old")
public class OldController extends AbstractController {

    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|compoundId).+";


    @RequestMapping(value = "/{compoundId:" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}")
    public ModelAndView showCompound(@PathVariable("compoundId") String mtblc, HttpServletRequest request) {

        logger.info("requested compound " + mtblc);
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("oldCompound");
        RestResponse<Compound> response = EntryController.getMetabolightsWsClient().getCompound(mtblc);

        Compound compound = response.getContent();

        if (compound == null)
            return printMessage("Couldn't get the requested compound: "+ mtblc, response.getErr().getMessage());

        WebCompound webCompound = new WebCompound(compound);


        mav.addObject("compound", webCompound);
        mav.addObject("pageTitle", mtblc + " - " + webCompound.getMc().getName());
        return mav;

    }

}