package uk.ac.ebi.metabolights.test;

import java.io.IOException;
import org.xml.sax.SAXException;
import com.meterware.httpunit.*;

import junit.framework.TestCase;

/**
 * @author kenneth
 *
 */
public class StaticPagesTest extends TestCase {
	
	//String baseUrl = "http://localhost:8080/metabolights";
	String baseUrl   = "http://wwwdev.ebi.ac.uk/metabolights/";
	String aboutUrl  = "about";
	String loginUrl  = "log in";
	String submitUrl = "submit";
	String homeUrl   = "home";
    
	//These links has to be on the home page
	private String[] urls = {homeUrl, submitUrl, aboutUrl, loginUrl};
	
	//Search term for the searchForm
	private String searchTerm = "rat";
	
	//Terms to search for in the "about" page
	private String[] searchTerms = 
		{ "MetaboLights", "Steinbeck", "European Bioinformatics Institute", 
		  "Griffin", "Metabolomics Standards Initiative" };
	
	//Login form test
	private String successUrl = "login-success";
	private String userName   = "metabolights@ebi.ac.uk";
	private String password   = "metabolights";
	
	
	public void testHomePageLinks() throws IOException, SAXException {
	    try {
	        // create the conversation object which will maintain state
	        WebConversation wc = new WebConversation();

	        // Obtain the main page
	        WebRequest request = new GetMethodWebRequest( baseUrl );
	        WebResponse response = wc.getResponse( request );

	        System.out.println( "Testing URL: " +baseUrl );
	        // Loop through the pages defined
	        for (String s: urls){
		        System.out.println( "Trying to click '"+s+"' " );
		        WebLink httpunitLink = response.getFirstMatchingLink( WebLink.MATCH_CONTAINED_TEXT, s );
		        response = httpunitLink.click();
	        }
	        

	      } catch (Exception e) {
	         System.err.println( "Exception: " + e );
	      }
	    
	}
	
	
	public void testAboutPage() throws IOException, SAXException {
	    try {
	    	
	        baseUrl = baseUrl+aboutUrl;
	    	
	        // create the conversation object which will maintain state
	        WebConversation wc = new WebConversation();
	        
	        // Obtain the main page
	        System.out.println( "Testing URL: " +baseUrl );
	        WebRequest request = new GetMethodWebRequest( baseUrl );
	        WebResponse response = wc.getResponse( request );
	        String text = response.getText();
	        System.out.println( "About page text lenght" +text.length() );
	        
	        for (String s: searchTerms){
		        System.out.println( "Trying to find term '"+s+"'" );
		        assertTrue("Should have contained the text '"+s+"'", text.contains(s));
	        }
	        

	      } catch (Exception e) {
	         System.err.println( "Exception: " + e );
	      }
	    
	}
	
	public void testHomePageSearchForm() throws IOException, SAXException {
	    try {
	        // create the conversation object which will maintain state
	        WebConversation wc = new WebConversation();

	        // Obtain the main page
	        WebRequest request = new GetMethodWebRequest( baseUrl );
	        WebResponse response = wc.getResponse( request );

	        //Test the search form
	        System.out.println( "Testing search for "+searchTerm);
	        WebForm form = response.getFormWithName("searchForm");	// select the search form in the page
	        form.setParameter( "freeTextQuery", searchTerm );      	// set search term
	        
	        //TODO, does not handle javascript
	        form.submit();         									// submit the form 
	        
	        WebResponse searchResponse = wc.getCurrentPage();
	        
	        int resultsLenght = searchResponse.getContentLength();
	        TextBlock[] textBlocks = searchResponse.getTextBlocks();

	      } catch (Exception e) {
	         System.err.println( "Exception: " + e );
	      }
	    
	}
	
	public void testLoginForm() throws IOException, SAXException {
	    try {
	    	
	    	// create the conversation object which will maintain state
	        WebConversation wc = new WebConversation();

	        // Obtain the main page
	        WebRequest request = new GetMethodWebRequest( baseUrl+ "login" );
	        WebResponse response = wc.getResponse( request );

	        //Test the login form
	        System.out.println( "Testing login at '"+baseUrl+"login' for "+userName);
	        WebForm form = response.getFormWithName("loginForm");	// select the login form in the page
	        form.setParameter( "j_username", userName );	
	        form.setParameter( "j_password", password );          
	        form.submit();         			// submit the form 
	        
	        WebResponse loginResponse = wc.getCurrentPage();
	        
	        String url = loginResponse.getURL().toString();
	        
	        if (url.equals(baseUrl+successUrl)) {
	        	assertTrue("Login sucessfull", true);
	        } else {
	        	fail("Login failed");
	        }
	        	

	      } catch (Exception e) {
	         System.err.println( "Exception: " + e );
	      }
	    
	}
	
	
}
