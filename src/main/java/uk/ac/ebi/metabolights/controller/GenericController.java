package uk.ac.ebi.metabolights.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Generic class to get Spring controlled forwards to a java server page.<br><br>
 * Handler methods that are annotated with @RequestMapping can have very flexible signatures, see more on<br>
 * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-arguments
 */

@Controller
public class GenericController {

	/** 
	 * Forwards to the jsp based on the last part of the requested URL.
	 *  
	 * @param request
	 * @return String indicating JSP target
	 */
	@RequestMapping(value={"/index","/about"})
	public String useMoreDeodorant (HttpServletRequest request) {
		return lastPartOfUrl(request);
	}

	/** 
	 * Forwards to the jsp based on the last part of the requested URL.
	 *  
	 * @param request
	 * @return String indicating JSP target
	 */
	public static String lastPartOfUrl (HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();
		String target=requestUrl.replaceFirst("^(.)*/", "");
		System.out.println("target is "+target);

		return target!=null&&!target.equals("")?target:"index"; 

	}
}


