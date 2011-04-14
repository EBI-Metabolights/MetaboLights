package uk.ac.ebi.metabolights.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontPageController {

	/**
	 * An empty (for now) method to get Spring controlled forward to the index.jsp page.
	 * 
	 * @param map
	 * @return String indicating JSP target
	 */
	@RequestMapping("/index")
	public String useMoreDeodorant (Map<String, Object> map) {
		return "index"; 
	}

}
