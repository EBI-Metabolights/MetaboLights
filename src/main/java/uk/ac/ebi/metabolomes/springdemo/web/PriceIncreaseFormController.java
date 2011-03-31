package uk.ac.ebi.metabolomes.springdemo.web;

/**
 * Next, let's take a look at the controller for this form. The onSubmit(..) method gets control and does some logging before 
 * it calls the increasePrice(..)  method on the ProductManager object. It then returns a ModelAndView passing in a new instance 
 * of a RedirectView created using the URL for the success view.
 */

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.ac.ebi.metabolomes.springdemo.service.ProductManager;
import uk.ac.ebi.metabolomes.springdemo.service.PriceIncrease;

/**
 * Deprecated. as of Spring 3.0, in favor of annotated controllers. TODO look
 * into that! Concrete FormController implementation that provides configurable
 * form and success views, and an onSubmit chain for convenient overriding.
 * Automatically resubmits to the form view in case of validation errors, and
 * renders the success view in case of a valid submission. The workflow of this
 * Controller does not differ much from the one described in the
 * AbstractFormController. The difference is that you do not need to implement
 * showForm and processFormSubmission: A form view and a success view can be
 * configured declaratively.
 * 
 */
public class PriceIncreaseFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private ProductManager productManager;

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {

		logger.info("________Making a new " + PriceIncrease.class);
		PriceIncrease priceIncrease = new PriceIncrease();
		priceIncrease.setPercentage(20);
		return priceIncrease;
	}

	public ModelAndView onSubmit(Object command) throws ServletException {
		int increase = ((PriceIncrease) command).getPercentage();
		logger.info("Increasing prices by " + increase + "%.");
		productManager.increasePrice(increase);
		logger.info("returning from PriceIncreaseForm view to " + getSuccessView());
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	public void setProductManager(ProductManager productManager) {
		logger.info("________ setting product manager "+productManager.hashCode());
		this.productManager = productManager;
	}

	public ProductManager getProductManager() {
		logger.info("________ getting product manager "+productManager.hashCode());
		return productManager;
	}

}