package uk.ac.ebi.metabolights.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller management stuff 
 * @author Pablo Conesa
 *
 */
@Controller
public class ManagerController extends AbstractController{
	
	/**
	 * Show config properties and check if some of them are consistent
	 * @return
	 */
	@RequestMapping({"/config"})
	public ModelAndView config() {
		
		Properties appProps = null,hibProps= null;
		
		try {
			
			// Load application properties
			Resource resource = new ClassPathResource("/application.properties");
			appProps = PropertiesLoaderUtils.loadProperties(resource);
			
			// Load hibernate properties
			resource = new ClassPathResource("/hibernate.properties");
			hibProps = PropertiesLoaderUtils.loadProperties(resource);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		// Validation result
		Map<String,Boolean> validationResult = new HashMap<String,Boolean>();
		
		// Validate lucene index
		validationResult.put("Lucene index consistency", (hibProps.getProperty("hibernate.search.default.indexBase").equals(appProps.getProperty("luceneIndexDirectoryShort"))));
		
		ModelAndView mav = new ModelAndView("config");
		mav.addObject("appProps", appProps);		
		mav.addObject("hibProps", hibProps);
		mav.addObject("validation", validationResult);
		return mav;

    }

    
}

