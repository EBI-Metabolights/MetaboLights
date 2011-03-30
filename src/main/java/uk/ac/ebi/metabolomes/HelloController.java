package uk.ac.ebi.metabolomes;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
/**
 * From http://static.springsource.org/docs/Spring-MVC-step-by-step/part1.html
 * 
 * This is a very basic Controller implementation. 
 * In Spring Web MVC, the Controller handles the request and returns a ModelAndView - in this case, one named 'hello.jsp' 
 * which is also the name of the JSP file we will create next. The model that this class returns is actually resolved 
 * via a ViewResolver. Since we have not explicitly defined a ViewResolver, we are going to be given a default one by 
 * Spring that simply forwards to a URL matching the name of the view specified. 
 * We will modify this later on. We have also specified a logger so we can verify that we actually got into the handler. 
 * Using Tomcat, these log messages should show up in the 'catalina.out' log file which can be found in the '${appserver.home}/log' 
 * directory of your Tomcat installation.
 * 
 * @author markr
 *
 */
public class HelloController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Returning hello view");
		System.out.println("ja dit is fijn");
		return new ModelAndView("hello.jsp");
	}

}