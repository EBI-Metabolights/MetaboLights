package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.TextUtils;

/**
 * Abstract controller providing common functionality to the real controllers.
 * @author markr
 *
 */
public abstract class AbstractController {
	private static Logger logger = Logger.getLogger(AbstractController.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAnyException(Exception ex) {
		logger.error("Exception encountered");
		
		//TODO dump stacktrace in log4j

		ModelAndView mav = new ModelAndView("error");
		mav.addObject("errorStack", TextUtils.getErrorStackAsHTML(ex));
		mav.addObject("errorMainMessage", ex.getMessage());
		return mav;
	}
	public ModelAndView printMessage(String title, String message){
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("message");
		mav.addObject("title",title);
		mav.addObject("message", message);
		
		return mav;
	}
	public ModelAndView printMessageFromLookup(String titleTag, String messageTag){
		
		return printMessage(PropertyLookup.getMessage(titleTag), PropertyLookup.getMessage(messageTag));
	}

}
