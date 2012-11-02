package uk.ac.ebi.metabolights.controller;

import java.util.Hashtable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for login and related actions.  
 * @author Tejas
 *
 */
@Controller
public class ReferenceLayerController extends AbstractController{
	
	@RequestMapping({"/rsearch"})
	public ModelAndView searchAndDisplay() {
	    
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("Alanine", "Amino acid");
		table.put("Gaunine", "Amino acid");
		table.put("Cytosine", "Amino acid");
		table.put("Thymine", "Amino acid");
		
		
		ModelAndView mav = new ModelAndView ("RefLayerSearch"); //name of jsp page must be same as this
		//mav.addObject("results", "Alanine");
		//mav.addObject("results", "Guanine");
		mav.addObject("results", table);
		return mav;
    }
}