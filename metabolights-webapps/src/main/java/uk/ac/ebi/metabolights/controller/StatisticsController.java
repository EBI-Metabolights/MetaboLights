/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/8/14 9:23 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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
        List<MLStats> idList   = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.IDENTIFIED.getValue());
        //How many submitters, reviewers, curators
        List<MLStats> subList  = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.SUBMITTERS.getValue());

        mav.addObject("dataList",dataList);
        mav.addObject("identifierList",idList);
        mav.addObject("submittersList",subList);

		return mav;
    }
}
