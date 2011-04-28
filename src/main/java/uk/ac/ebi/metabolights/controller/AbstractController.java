package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.authenticate.IsaTabAuthentication;

/**
 * Asbtract controller providing common functionality to the real controllers.
 * @author markr
 *
 */
public abstract class AbstractController {
	private static Logger logger = Logger.getLogger(AbstractController.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAnyException(Exception ex) {
		logger.error("Exception encountered");
		
		//TODO dump stacktrace in log4j
		
		
		return new ModelAndView("error", "errorMessage", getErrorStackAsHTML(ex));
	}

	protected String getErrorStackAsHTML(Throwable throwable) {
		StringBuffer sb = new StringBuffer(throwable.getMessage() + "<br>");
		StackTraceElement[] st = throwable.getStackTrace();
		for (int i = 0; i < st.length; i++) {
			StackTraceElement stackTraceElement = st[i];
			sb.append("\tat " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + " ( " +
					stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " )" + "<br>");
		}
		if (throwable.getCause() != null) {
			sb.append("<br>" +
					getErrorStackAsHTML(throwable.getCause()));
		}
		return sb.toString();
	}

}
