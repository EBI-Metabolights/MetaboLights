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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.dao.MetaboLightsParametersDAO;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsParametersService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 17/01/2013
 * Time: 11:15
 */
@Controller
public class MetaboLightsParametersController extends AbstractController {

    private String paramname = null;
    @Autowired
    MetaboLightsParametersService metaboLightsParametersService;

    @RequestMapping({"/parameters"})
    public ModelAndView showMetaboLightsParameters(@RequestParam(required = false, value ="paramname") String paramname) {
        this.paramname = paramname;
        return getParameters();
    }

    private ModelAndView getParameters() {

        List<MetaboLightsParameters> metaboLightsParameters = metaboLightsParametersService.getAll();

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("parameters"); //name of jsp page must be same as this
        mav.addObject("mtblparamteres",metaboLightsParameters);

        return mav;
    }

    @RequestMapping("/updateParameters")
    public ModelAndView updateMetaboLightsParameters(
            @RequestParam(required = false, value ="paramname") String paramname,
            @RequestParam(required = false, value ="paramvalue") String paramvalue) {

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("updateParameters");

        paramname.trim();
        paramvalue.trim();

        MetaboLightsParameters metaboLightsParameters = new MetaboLightsParameters();
        metaboLightsParameters.setParameterName(paramname);
        metaboLightsParameters.setParameterValue(paramvalue);
        metaboLightsParametersService.update(metaboLightsParameters);

        mav.addObject("paramname", metaboLightsParameters.getParameterName());
        mav.addObject("paramvalue", metaboLightsParameters.getParameterValue());
        return mav;
    }

    @RequestMapping("/insertParameters")
    public ModelAndView insertMetaboLightsParameters(
            @RequestParam(required = false, value ="paramname") String paramname,
            @RequestParam(required = false, value ="paramvalue") String paramvalue){

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("updateParameters");

        paramname.trim();
        paramvalue.trim();

        MetaboLightsParameters metaboLightsParameters = new MetaboLightsParameters();
        metaboLightsParameters.setParameterName(paramname);
        metaboLightsParameters.setParameterValue(paramvalue);
        metaboLightsParametersService.insert(metaboLightsParameters);

        mav.addObject("paramname", metaboLightsParameters.getParameterName());
        mav.addObject("paramvalue", metaboLightsParameters.getParameterValue());
        return mav;
    }

    @RequestMapping(value = "/addParameters")
    public ModelAndView addMetaboLightsParameters(){
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("updateParameters");
        return mav;
    }

    @RequestMapping(value = "/deleteParam")
    public ModelAndView deleteMetaboLightsParameters(
            @RequestParam(required = false, value ="paramname") String paramname,
            @RequestParam(required = false, value ="paramvalue") String paramvalue){

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("updateParameters");

        MetaboLightsParameters metaboLightsParameters = new MetaboLightsParameters();
        metaboLightsParameters.setParameterName(paramname);
        metaboLightsParameters.setParameterValue(paramvalue);
        metaboLightsParametersService.delete(metaboLightsParameters);

        return mav;
    }
}

