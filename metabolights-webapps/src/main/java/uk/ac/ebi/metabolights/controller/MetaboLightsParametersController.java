/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/15/13 4:38 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

