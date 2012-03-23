package uk.ac.ebi.metabolights.controller;


import java.io.File;
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
		
		// Validate end character of path properties
		validationResult.put("uploadDirectory ends with /", (appProps.getProperty("uploadDirectory").endsWith("/")));
		validationResult.put("publicFtpLocation ends with /", (appProps.getProperty("publicFtpLocation").endsWith("/")));
		validationResult.put("privateFtpLocation ends with /", (appProps.getProperty("privateFtpLocation").endsWith("/")));
		validationResult.put("publicFtpStageLocation ends with /", (appProps.getProperty("publicFtpStageLocation").endsWith("/")));
		validationResult.put("privateFtpStageLocation ends with /", (appProps.getProperty("privateFtpStageLocation").endsWith("/")));
		validationResult.put("luceneIndexDirectory ends with /", (appProps.getProperty("luceneIndexDirectory").endsWith("/")));
		validationResult.put("luceneIndexDirectoryShort ends with /", (appProps.getProperty("luceneIndexDirectoryShort").endsWith("/")));
		
		// Validate paths variable exists
		validationResult.put("uploadDirectory existance", (new File(appProps.getProperty("uploadDirectory")).exists()));
		validationResult.put("publicFtpLocation existance", (new File(appProps.getProperty("publicFtpLocation")).exists()));
		validationResult.put("privateFtpLocation existance", (new File(appProps.getProperty("privateFtpLocation")).exists()));
		validationResult.put("publicFtpStageLocation existance", (new File(appProps.getProperty("publicFtpStageLocation")).exists()));
		validationResult.put("privateFtpStageLocation existance", (new File(appProps.getProperty("privateFtpStageLocation")).exists()));
		validationResult.put("luceneIndexDirectory existance", (new File(appProps.getProperty("luceneIndexDirectory")).exists()));
		validationResult.put("luceneIndexDirectoryShort existance", (new File(appProps.getProperty("luceneIndexDirectoryShort")).exists()));
		
		
		
		
		
		
		ModelAndView mav = new ModelAndView("config");
		mav.addObject("appProps", appProps);		
		mav.addObject("hibProps", hibProps);
		mav.addObject("validation", validationResult);
		return mav;

    }

    
}

