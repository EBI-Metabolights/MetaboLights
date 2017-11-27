/*
 * EBI MetaboLights - https://www.ebi.ac.uk/metabolights
 * Metabolomics team
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2017-Oct-18
 * Modified by:   kalai
 *
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kalai on 18/10/2017.
 */
@Controller
public class AdvancedSearchController extends AbstractController {
    @RequestMapping(value = {"/advancedsearch"})
    public ModelAndView showAdvancedSearchPage(HttpServletRequest request) {
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("advancedSearch");
//        mav.addObject("studyId", studyId);
//        MetabolightsUser user = LoginController.getLoggedUser();
//        mav.addObject("apiToken", user.getApiToken());

        return mav;

    }
}
