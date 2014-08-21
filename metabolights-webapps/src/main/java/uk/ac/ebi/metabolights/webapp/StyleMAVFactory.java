/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/16/12 5:24 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webapp;

import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.log4j.Logger;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Factory for serving a ModelAndView with the header, footer,..etc using the frontier template web service. 
 * @author Pablo Conesa
 *
 */
public class StyleMAVFactory {
	
	private static Logger logger = Logger.getLogger(StyleMAVFactory.class);
	
	// URLs to make the requests
	private String headerURL;
	private String localHeaderURL;
	private String footerURL;
	
	// JSON configuration
    private Resource jsonConfig;
    private String jsonConfigS;

    // Returned html elements
	private String header;
	private String localheader;
	private String footer;	
	

	/**
	 * @return the footerURL
	 */
	public String getFooterURL() {
		return footerURL;
	}

	/**
	 * @param footerURL the footerURL to set
	 */
	public void setFooterURL(String footerURL) {
		this.footerURL = footerURL;
	}

	/**
	 * @return the headerURL
	 */
	public String getHeaderURL() {
		return headerURL;
	}

	/**
	 * @param headerURL the headerURL to set
	 */
	public void setHeaderURL(String headerURL) {
		this.headerURL = headerURL;
	}

	/**
	 * @return the localHeaderURL
	 */
	public String getLocalHeaderURL() {
		return localHeaderURL;
	}

	/**
	 * @param localHeaderURL the localHeaderURL to set
	 */
	public void setLocalHeaderURL(String localHeaderURL) {
		this.localHeaderURL = localHeaderURL;
	}
	
	public void setJsonConfig(Resource jsonConfig) {
    	this.jsonConfig = jsonConfig;
    }

	private String getHtml(String requestUrl){
	    StringBuffer sbf = new StringBuffer();
	    
	    //Access the page
        try {
                URL url = new URL(requestUrl);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;
                while ( (inputLine = in.readLine()) != null) sbf.append(inputLine);
                in.close();
        } catch (Exception e) {
        	logger.info("Failed to get HTML from: " + requestUrl + "\n." + e.getMessage());
        }
        
        return sbf.toString();
	}
		
	private String getHtmlPost(String requestUrl, String jsonObject) {
		
		
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
	public ModelAndView getFrontierMav(String name) {
		
		// Get the global header, local header and footer if not already gotten.
		if (header== null) header = getHeader();
		if (localheader== null) localheader = getHtmlPost(localHeaderURL, getJsonConfigS());
		if (footer == null) footer = getFooter();
	
		ModelAndView mav = new ModelAndView (name);
		mav.addObject("frontierheader", header);
		mav.addObject("localfrontierheader", localheader);
		mav.addObject("frontierfooter", footer);
		
		
		return mav;
	}

	public ModelAndView getFrontierMav(String name, String modelName, Object objectModel) {
		
		ModelAndView mav=  getFrontierMav(name);
		
		mav.addObject(modelName, objectModel);
		
		return mav;
	}
	
	private String getFooter(){
		return getHtml(footerURL);
	}
	private String getHeader(){
		return getHtml(headerURL);
		
	}

	private String getJsonConfigS()  {
	
		if (jsonConfigS == null){
			
			BufferedReader reader = null;
			
			try {		
		
				reader = new BufferedReader(new FileReader(jsonConfig.getFile()));
				
				StringBuilder stringBuilder = new StringBuilder();
				String ls = System.getProperty("line.separator");
		
			    String line;
			    while ((line = reader.readLine()) != null) {
			        stringBuilder.append(line);
			        //stringBuilder.append(ls);
			    }
	
				// Populate the string 
				jsonConfigS = stringBuilder.toString();
	
			} catch (Exception e) {
				// Log the exception
				logger.info("Failed to read JSON file " + jsonConfig.getFilename() + "\n." + e.getMessage());
				
				// In the worse case continue with an empty parameter as json object.
				jsonConfigS = "";
	
			} finally {
				//if (reader != null) reader.close();
			}
	
		}
		
	    return jsonConfigS; 
	}
}
