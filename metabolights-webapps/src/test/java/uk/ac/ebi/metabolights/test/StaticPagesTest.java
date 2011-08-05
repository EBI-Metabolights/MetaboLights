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

	private static String baseUrl = "http://localhost:8080/metabolights";
	//public static String baseUrl   = "http://wwwdev.ebi.ac.uk/metabolights/";
	private static String aboutUrl  = "about";
	private static String loginUrl  = "log in";
	private static String submitUrl = "submit";
	private static String homeUrl   = "home";

	//Logout
	private static String logout = "log out";
	private static String loggedOutSuccess="loggedout";

	//These links has to be on the home page
	private static String[] urls = {homeUrl, submitUrl, aboutUrl, loginUrl};

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


	private WebResponse getLoginResponse(String userName, String password){
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
			
			testLoginForm(userName, password);
			
		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Login failed");
		}

	}


	public void testLoginForm(String userName, String password) throws IOException, SAXException {
		try {

			//Get the login WebResponse
			WebResponse loginResponse = getLoginResponse(userName,password);
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
			WebResponse response = getLoginResponse(userName,password);

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

}
