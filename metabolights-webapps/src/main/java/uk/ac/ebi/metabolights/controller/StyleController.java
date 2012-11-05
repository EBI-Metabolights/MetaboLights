package uk.ac.ebi.metabolights.controller;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Controller for serving style, header and footers for frontier 
 * @author Pablo Conesa
 *
 */
@Controller
public class StyleController {
	
	
	private static Logger logger = Logger.getLogger(StyleController.class);
	
	static final String footerURL = "http://wwwdev.ebi.ac.uk/frontier/template-service/templates/footer/main";
	static final String headerURL = "http://wwwdev.ebi.ac.uk/frontier/template-service/templates/services/header/main";
	static final String localHeaderURL = "http://wwwdev.ebi.ac.uk/frontier/template-service/templates/header/specific";
	static final String jsonMasterHead = "\"localMasthead\": {\"title\": \"MetaboLights\",\"search\":{\"btnText\": \"Search\",\"action\":\"RefLayerSearch\",\"method\":\"post\",\"inputName\":\"query\"}";
	static final String jsonTabs = "\"tabs\": [{\"selected\": true,\"title\": \"Home\",\"href\": \"/home\",\"tooltip\": \"Home page\"}]";
	static final String customization= "{" + jsonMasterHead + "," + jsonTabs + "}}";
	

	static String header;
	static String localheader;
	static String footer;	
	
	
	/**
	 * to reset the cached style
	 * @return
	 */
	@RequestMapping({"/resetstyle"})
	public ModelAndView resetStyle() {
		
		header=null;
		localheader = null;
		footer = null;
		
		//Empty for now..
		return new ModelAndView();
	 	
	}
	
	
	private static String getHtml(String requestUrl){
	    StringBuffer sbf = new StringBuffer();
	    
	    //Access the page
        try {
                URL url = new URL(requestUrl);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;
                while ( (inputLine = in.readLine()) != null) sbf.append(inputLine);
                in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        
        return sbf.toString();
	}
		
	private static String getHtmlPost(String requestUrl, String jsonObject) {
		
		
		HttpURLConnection conn = null;
		
		String response= "";
		
	    try {
	    
	    	
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	 
			
	 
			OutputStream os = conn.getOutputStream();
			os.write(jsonObject.getBytes());
			os.flush();
	 
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			
			String output;
			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = response + output;
			}
	 
	 
	        
	    }catch (Exception ex) {
	        // handle exception here
	    } finally {
	    	if (conn != null) conn.disconnect();
	    }
		
		
	    return response;
		
	}
	public static ModelAndView getFrontierMav(String name){
		if (header== null) header = getHeader();
		if (localheader== null) localheader = getHtmlPost(localHeaderURL, customization);
		if (footer == null) footer = getFooter();
	
		ModelAndView mav = new ModelAndView (name);
		mav.addObject("frontierheader", header);
		mav.addObject("localfrontierheader", localheader);
		mav.addObject("frontierfooter", footer);
		
		
		return mav;
	}
	private static String getFooter(){
		return getHtml(footerURL);
	}
	private static String getHeader(){
		return getHtml(headerURL);
		
	}
}

