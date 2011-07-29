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

	//TODO, read properties file, like messages_en.properties for string matching

	//String baseUrl = "http://localhost:8080/metabolights";
	private static String baseUrl   = "http://wwwdev.ebi.ac.uk/metabolights/";
	private static String aboutUrl  = "about";
	private static String loginUrl  = "log in";
	private static String submitUrl = "submit";
	private static String homeUrl   = "home";
	private static String forgotPw  = "forgotPassword";
	
	//Logout
	private static String logout = "log out";
	private static String loggedOutSuccess="loggedout";

	//Create new account
	private static String newAccount = "newAccount";
	private static String newUserName  = "metabolights-help@ebi.ac.uk";
	private static String newAccountRequested = "accountRequested";
	
	//These links has to be on the home page
	private static String[] urls = {homeUrl, submitUrl, aboutUrl, loginUrl};
	
	//Search term for the searchForm
	private static String searchTerm = "rat";
	
	//Forgotten password form
	private static String resetSuccess = "password was sent";
	
	//Terms to search for in the "about" page
	private static String[] searchTerms = 
		{ "MetaboLights", "Steinbeck", "European Bioinformatics Institute", 
			"Griffin", "Metabolomics Standards Initiative" };

	//Login form test
	private static String loginSuccessUrl = "login-success";
	private static String userName   = "metabolights@ebi.ac.uk";
	private static String password   = "81c808"; //TODO, change before testing. After test, a new password is emailed 
	
	// create the conversation object which will maintain state
	private WebConversation wc = new WebConversation();

	private WebResponse getWebResponse(String url){
		try {
			
			String lBaseUrl = baseUrl;
			
			if (!url.isEmpty())
				lBaseUrl = lBaseUrl+url;
			
			System.out.println( "Testing URL: " +lBaseUrl );
			WebRequest request = new GetMethodWebRequest( lBaseUrl );
			return wc.getResponse( request );
			
		} catch (Exception e) {
			System.err.println( "Exception: " + e );
		}
		return null;
		
	}

	public void testHomePageLinks() throws IOException, SAXException {
		try {

			// Obtain the main page
			WebResponse response = getWebResponse("");

			// Loop through the pages defined
			for (String s: urls){
				System.out.println( "Trying to click '"+s+"' " );
				WebLink httpunitLink = response.getFirstMatchingLink( WebLink.MATCH_CONTAINED_TEXT, s );
				response = httpunitLink.click();
			}

		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Home page link test failed");
		}

	}


	public void testAboutPage() throws IOException, SAXException {
		try {

			// Obtain the main page
			WebResponse response = getWebResponse(aboutUrl);
			
			String text = response.getText();
			System.out.println( "About page text lenght" +text.length() );

			for (String s: searchTerms){
				System.out.println( "Trying to find term '"+s+"'" );
				assertTrue("Should have contained the text '"+s+"'", text.contains(s));
			}


		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("About page failed");
		}

	}


	private WebResponse getLoginResponse(){
		try {
		
			// Obtain the main page
			WebResponse response = getWebResponse("login");

			//Test the login form
			System.out.println( "Testing login at '"+baseUrl+"login' for "+userName);
			WebForm form = response.getFormWithName("loginForm");	// select the login form in the page
			form.setParameter( "j_username", userName );	
			form.setParameter( "j_password", password );          
			form.submit();         			// submit the form 

			return wc.getCurrentPage();
			
		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Login failed");
		}
		return null;
	}

	
	public void testLoginForm() throws IOException, SAXException {
		try {

			//Get the login WebResponse
			WebResponse loginResponse = getLoginResponse();
			String url = loginResponse.getURL().toString();

			if (url.equals(baseUrl+loginSuccessUrl)) {
				assertTrue("Login sucessfull", true);
			} else {
				fail("Login failed");
			}


		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Login failed");
		}

	}

	
	public void testlogout() throws IOException, SAXException {
		try {

			//First you need to be logged in
			WebResponse response = getLoginResponse();
			
			//Try to find and click on "log out"
			WebLink httpunitLink = response.getFirstMatchingLink( WebLink.MATCH_CONTAINED_TEXT, logout );
			response = httpunitLink.click();

			WebResponse logoutResponse = wc.getCurrentPage();

			String url =  logoutResponse.getURL().toString();

			System.out.println( "Checking if logout URL ("+url+") = "+baseUrl+loggedOutSuccess);
			if (url.equals(baseUrl+loggedOutSuccess)) {
				assertTrue("Logout sucessfull", true);
			} else {
				fail("logout failed");
			}


		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("logout failed");
		}

	}

	/*

	public void testNewAccountForm() throws IOException, SAXException {
		try {
			
			// Obtain the main page
			WebResponse response = getWebResponse(newAccount);

			//Test the login form
			System.out.println( "Testing accountForm form at '"+baseUrl+newAccount+"' for "+userName);
			WebForm form = response.getFormWithName("accountForm");	// select the accountForm form in the page
			form.setParameter( "email", newUserName );	 //email and username
			form.setParameter( "firstName", "Mitt" );       
			form.setParameter( "lastName", "Etternavn" ); 
			form.setParameter( "dbPassword", password ); 
			form.setParameter( "userVerifyDbPassword", password ); 
			form.setParameter( "affiliation", "EMBL-EBI" ); 
			form.setParameter( "affiliationUrl", "http://www.ebi.ac.uk/~kenneth" );
			form.setParameter( "address", "GB" ); 
			
			form.submit();         			// submit the form 

			String url = response.getURL().toString();  //Where did we end up

			if (url.equals(baseUrl+newAccountRequested)) {
				assertTrue("Accunt requested sucessfull", true);
			} else {
				fail("Account request failed");
			}
			
		} catch (Exception e) {
			System.err.println( "Exception: " + e );
		}

	}
	
	
	public void testForgottenPasswordForm() throws IOException, SAXException {
	    try {
	        
	        // Obtain the main page
			WebResponse response = getWebResponse(forgotPw);

	        //Test the login form
	        System.out.println( "Testing '"+baseUrl+forgotPw+"' for "+userName);
	        WebForm form = response.getFormWithName("resetForm");	// select the login form in the page
	        form.setParameter( "emailAddress", userName );	      
	        form.submit();         			// submit the form 
	        
	        WebResponse resetResponse = wc.getCurrentPage(); //Read the new page text
	        String text = resetResponse.getText();
	        
	        assertTrue("Should have contained the text '"+resetSuccess+"'", text.contains(resetSuccess));
	        	

	      } catch (Exception e) {
	         System.err.println( "Exception: " + e );
	      }
	    
	} 
	
	
	public void testHomePageSearchForm() throws IOException, SAXException {
	    try {
	        // Obtain the main page
			WebResponse response = getWebResponse("");

	        //Test the search form
	        System.out.println( "Testing search for "+searchTerm);
	        WebForm form = response.getFormWithName("searchForm");	// select the search form in the page
	        form.setParameter( "freeTextQuery", searchTerm );      	// set search term
	        
	        //TODO, does not handle javascript
	        //form.submit();         									// submit the form 
	        
	        //WebResponse searchResponse = wc.getCurrentPage();
	        //int resultsLenght = searchResponse.getContentLength();
	        //TextBlock[] textBlocks = searchResponse.getTextBlocks();

	      } catch (Exception e) {
	         System.err.println( "Exception: " + e );
	      }
	    
	} 
	
	*/
	
}
