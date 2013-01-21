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

        List<MLStats> dataList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.DATA.getValue());            //General stats, number of....
        List<MLStats> idList   = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.IDENTIFIED.getValue());      //Which databases the compound was identified
        List<MLStats> subList  = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.SUBMITTERS.getValue());      //How many submitters

        mav.addObject("dataList",dataList);
        mav.addObject("identifierList",idList);
        mav.addObject("submittersList",subList);

		return mav;    
    }
}