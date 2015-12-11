/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/8/14 4:27 PM
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

package uk.ac.ebi.metabolights.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MLStats;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsStatsService;

import java.util.List;

/**
 * Controller for login and related actions.
 * @author Pablo
 *
 */
@Controller
public class StatisticsController extends AbstractController{

    @Autowired
    private MetaboLightsStatsService metaboLightsStatsService;

	@RequestMapping({"/statistics","/stats"})
	public ModelAndView showStatistics() {

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav ("statistics"); //name of jsp page must be same as this

        //General stats, number of....
        List<MLStats> dataList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.DATA.getValue());
        //Which databases the compound/database acc was identified in
        List<MLStats> idList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.IDENTIFIED.getValue());
        //How many submitters, reviewers, curators
        List<MLStats> subList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.SUBMITTERS.getValue());
        List<MLStats> topSubList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.TOPSUBM.getValue());

        //General info
        List<MLStats> infoList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.INFO.getValue());

        mav.addObject("dataList",dataList);
        mav.addObject("identifierList",idList);
        mav.addObject("submittersList",subList);
        mav.addObject("topSubList",topSubList);
        mav.addObject("infoList",infoList);

		return mav;
    }
}
