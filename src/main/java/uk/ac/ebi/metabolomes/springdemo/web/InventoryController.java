package uk.ac.ebi.metabolomes.springdemo.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.ac.ebi.metabolomes.springdemo.service.ProductManager;

/**
 * See springapp-servlet.xml
 *     <bean name="/hello.htm" class="uk.ac.ebi.metabolomes.springdemo.web.InventoryController">
 *        <property name="productManager" ref="productManager"/>
 *     </bean>
 * The InventoryController holds a reference to the ProductManager class. 
 * We also add code to have the controller pass some product information to the view. 
 * The getModelAndView() method now returns a Map with both the date and time and the products list obtained from 
 * the manager reference.
 *
 */
public class InventoryController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private ProductManager productManager;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String now = (new java.util.Date()).toString();
        logger.info("returning hello view with " + now);

        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);
        myModel.put("products", this.productManager.getProducts());
        
		logger.info("Product manager is object "+productManager.hashCode());


	    // param viewName name of the View to render, to be resolved
	    // param modelName name of the single entry in the model
	    // param modelObject the single model object
        return new ModelAndView("hello", "model", myModel); // "hello" will be made into "/jsp/hello.jsp" by the viewResolver
                                                            // "model" is used in that jsp page
                                                            //  myModel contains 'now' and 'products', a hashmaps of objects prone to run time errors :D
                                                       
    }


    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

}