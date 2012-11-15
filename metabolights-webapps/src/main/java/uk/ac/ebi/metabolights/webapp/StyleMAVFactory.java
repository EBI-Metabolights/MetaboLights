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
