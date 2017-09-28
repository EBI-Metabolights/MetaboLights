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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;
import org.xmlsoap.schemas.soap.encoding.Boolean;

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
	
	private static Logger logger = LoggerFactory.getLogger(StyleMAVFactory.class);
	
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

    private @Value("#{staticHeaderFooter}") boolean staticHeaderFooter;
	

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

        if (!staticHeaderFooter) {
            if (header == null) header = getHeader();
            if (localheader == null) localheader = getHtmlPost(localHeaderURL, getJsonConfigS());
            if (footer == null) footer = getFooter();
        }else{
            //if (header == null) header = "<div id=\"global-masthead\" class=\"masthead grid_24\"><!--This has to be one line and no newline characters--><a href=\"//www.ebi.ac.uk/\" title=\"Go to the EMBL-EBI homepage\"><img src=\"//www.ebi.ac.uk/web_guidelines/images/logos/EMBL-EBI/EMBL_EBI_Logo_white.png\" alt=\"EMBL European Bioinformatics Institute\"></a><nav><ul id=\"global-nav\"><!-- set active class as appropriate --><li id=\"services\" class=\" first active\"><a href=\"//www.ebi.ac.uk/services\" title=\"Services\">Services</a></li><li id=\"research\" class=\"\"><a href=\"//www.ebi.ac.uk/research\" title=\"Research\">Research</a></li><li id=\"training\" class=\"\"><a href=\"//www.ebi.ac.uk/training\" title=\"Training\">Training</a></li><li id=\"industry\" class=\"\"><a href=\"//www.ebi.ac.uk/industry\" title=\"Industry\">Industry</a></li><li id=\"about\" class=\" last\"><a href=\"//www.ebi.ac.uk/about\" title=\"About us\">About us</a></li></ul></nav></div>";
            if (localheader == null) localheader = "<div id=\"local-masthead\" class=\"masthead grid_24 nomenu\"><!-- local-title --><!-- NB: for additional title style patterns, see http://frontier.ebi.ac.uk/web/style/patterns --><div class=\"grid_12 alpha logo-title\" id=\"local-title\"><a href=\"index\" title=\"Back to MetaboLights homepage\"><img src=\"/metabolights/img/MetaboLightsLogo.png\" alt=\"MetaboLights\" width=\"64\" height=\"64\"></a><span><h1><a href=\"index\" title=\"Back to MetaboLights homepage\">MetaboLights</a></h1></span></div><!-- /local-title --><!-- local-search --><!-- NB: if you do not have a local-search, delete the following div, and drop the class=\"grid_12 alpha\" class from local-title above --><div class=\"grid_12 omega\"><form id=\"local-search\" name=\"local-search\" action=\"/metabolights/search\" method=\"post\"><fieldset><div class=\"left\"><label><input type=\"text\" name=\"freeTextQuery\" id=\"local-searchbox\"></label><!-- Include some example searchterms - keep them short and few! --><span class=\"examples\">Examples: <a href=\"/metabolights/search?freeTextQuery=alanine\">alanine</a>, <a href=\"/metabolights/search?freeTextQuery=Homo sapiens\">Homo sapiens</a>, <a href=\"/metabolights/search?freeTextQuery=urine\">urine</a>, <a href=\"/metabolights/search?freeTextQuery=MTBLS1\">MTBLS1</a></span></div><div class=\"right\"><input type=\"submit\" name=\"submit\" value=\"Search\" class=\"submit\"></div></fieldset></form></div><!-- /local-search --><!-- local-nav --><nav><ul class=\"grid_24\" id=\"local-nav\"><li class=\" first\"><a href=\"/metabolights/index\" title=\"Home page\">Home</a></li><li class=\"\"><a href=\"/metabolights/studies\" title=\"Browse all Studies/Experiments\">Browse Studies</a></li><li class=\"\"><a href=\"/metabolights/reference\" title=\"Browse all metabolites\">Browse Compounds</a></li><li class=\"\"><a href=\"/metabolights/species\" title=\"Browse all species\">Browse Species</a></li><li class=\"\"><a href=\"/metabolights/analysis\" title=\"Analysis tools\">Analysis</a></li><li class=\"\"><a href=\"/metabolights/download\" title=\"Download\">Download</a></li><li class=\"\"><a href=\"help\" title=\"Help\">Help</a></li><li class=\"\"><a href=\"/metabolights/contact\" title=\"Please give us feedback\">Give us feedback</a></li><li class=\" last\"><a href=\"/metabolights/about\" title=\"About MetaboLights\">About</a></li><!-- If you need to include functional (as opposed to purely navigational) links in your local menu, add them here, and give them a class of \"functional\". Remember: you'll need a class of \"last\" for whichever one will show up last... For example: --><li class=\"functional last\"><a href=\"/metabolights/login\" class=\"icon icon-functional\" data-icon=\"l\" title=\"Log in to MetaboLights\">Login</a></li><li class=\"functional\"><a href=\"/metabolights/presubmit\" class=\"icon icon-functional\" data-icon=\"_\" title=\"Submit data to MetaboLights\">Submit Study</a></li></ul></nav><!-- /local-nav --></div>";
            if (footer == null) footer = "<footer class='grid_24 clearfix'>\n" +
                    "  <div id=\"global-footer\" class=\"global-footer col-md-12\">\n" +
                    "    <nav id=\"global-nav-expanded\" class=\"global-nav-expanded row\">\n" +
                    "      <div class=\"col-md-2 col-sm-6\">\n" +
                    "        <a href=\"//www.ebi.ac.uk\" title=\"EMBL-EBI\">\n" +
                    "          <span class=\"ebi-logo\"></span>\n" +
                    "        </a>\n" +
                    "      </div>\n" +
                    "      <div class=\"col-md-2 col-sm-6\">\n" +
                    "        <h5 class=\"services\">\n" +
                    "          <a class=\"services-color\" href=\"//www.ebi.ac.uk/services\">Services</a>\n" +
                    "        </h5>\n" +
                    "        <ul>\n" +
                    "          <li class=\"first\"><a href=\"//www.ebi.ac.uk/services\">By topic</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/services/all\">By name (A-Z)</a></li>\n" +
                    "          <li class=\"last\"><a href=\"//www.ebi.ac.uk/support\">Help &amp; Support</a></li>\n" +
                    "        </ul>\n" +
                    "      </div>\n" +
                    "      <div class=\"col-md-2 col-sm-6\">\n" +
                    "        <h5 class=\"research\">\n" +
                    "          <a class=\"research-color\" href=\"//www.ebi.ac.uk/research\">Research</a>\n" +
                    "        </h5>\n" +
                    "        <ul>\n" +
                    "          <li>\n" +
                    "            <a href=\"//www.ebi.ac.uk/research/publications\">Publications</a>\n" +
                    "          </li>\n" +
                    "          <li>\n" +
                    "            <a href=\"//www.ebi.ac.uk/research/groups\">Research groups</a>\n" +
                    "          </li>\n" +
                    "          <li class=\"last\">\n" +
                    "            <a href=\"//www.ebi.ac.uk/research/postdocs\">Postdocs</a> &amp; <a href=\"//www.ebi.ac.uk/research/eipp\">PhDs</a>\n" +
                    "          </li>\n" +
                    "        </ul>\n" +
                    "      </div>\n" +
                    "      <div class=\"col-md-2 col-sm-6\">\n" +
                    "        <h5 class=\"training\">\n" +
                    "          <a class=\"training-color\" href=\"//www.ebi.ac.uk/training\">Training</a>\n" +
                    "        </h5>\n" +
                    "        <ul>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/training/handson\">Train at EBI</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/training/roadshow\">Train outside EBI</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/training/online\">Train online</a></li>\n" +
                    "          <li class=\"last\"><a href=\"//www.ebi.ac.uk/training/contact-us\">Contact organisers</a></li>\n" +
                    "        </ul>\n" +
                    "      </div>\n" +
                    "      <div class=\"col-md-2 col-sm-6\">\n" +
                    "        <h5 class=\"industry\"><a class=\"industry-color\" href=\"//www.ebi.ac.uk/industry\">Industry</a></h5>\n" +
                    "        <ul>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/industry/private\">Members Area</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/industry/workshops\">Workshops</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/industry/sme-forum\"><abbr title=\"Small Medium Enterprise\">SME</abbr> Forum</a></li>\n" +
                    "          <li class=\"last\"><a href=\"//www.ebi.ac.uk/industry/contact\">Contact Industry programme</a></li>\n" +
                    "        </ul>\n" +
                    "      </div>\n" +
                    "      <div class=\"col-md-2 col-sm-6\">\n" +
                    "        <h5 class=\"about\"><a class=\"ebi-color\" href=\"//www.ebi.ac.uk/about\">About EMBL-EBI</a></h5>\n" +
                    "        <ul>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/about/contact\">Contact us</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/about/events\">Events</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/about/jobs\" title=\"Jobs, postdocs, PhDs...\">Jobs</a></li>\n" +
                    "          <li class=\"first\"><a href=\"//www.ebi.ac.uk/about/news\">News</a></li>\n" +
                    "          <li><a href=\"//www.ebi.ac.uk/about/people\">People &amp; groups</a></li>\n" +
                    "        </ul>\n" +
                    "      </div>\n" +
                    "    </nav>\n" +
                    "    <section id=\"ebi-footer-meta\" class=\"ebi-footer-meta\">\n" +
                    "        <p class=\"address\">EMBL-EBI, Wellcome Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK. +44 (0)1223 49 44 44</p>\n" +
                    "        <p class=\"legal\">Copyright © EMBL-EBI 2017 | EMBL-EBI is <a href=\"http://www.embl.org/\">part of the European Molecular Biology Laboratory</a> | <a href=\"//www.ebi.ac.uk/about/terms-of-use\">Terms of use</a>\n" +
                    "          <a class=\"readmore pull-right\" href=\"http://intranet.ebi.ac.uk\">Intranet</a></p>\n" +
                    "    </section>\n" +
                    "  </div>\n" +
                    "</footer>";
        }
	
		ModelAndView mav = new ModelAndView (name);
		mav.addObject("frontierheader", header);
		mav.addObject("localfrontierheader", localheader);
		mav.addObject("frontierfooter", footer);
		
		return mav;
	}

	public ModelAndView getSimpleFrontierMav(String name) {

		ModelAndView mav = new ModelAndView (name);

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
