package uk.ac.ebi.metabolights.test;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;


/**
 * @author kenneth
 *
 */
public class StaticPagesWithJavaScriptTest extends WebTestCase {
	
	//Create new account
	private static String newAccount = "newAccount";
	private static String newUserName  = "metabolights-help@ebi.ac.uk";
	private static String password   = "81c808"; //TODO, change before testing. After test, a new password is emailed 
	public static String baseUrl   = "http://localhost:8080/metabolights/";
	
	//Search term for the searchForm
	private static String searchTerm = "rat";

	//Forgotten password form
	private static String resetSuccess = "password was sent";
	
	private static String forgotPw  = "forgotPassword";
	

    public void setUp() {
        try {
			super.setUp();
			setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);    // use HtmlUnit
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setBaseUrl(baseUrl);
    }

	public void testNewAccountForm() {
		try {
			
			setScriptingEnabled(false);  //turn of JavaS(hit)cript
			
			beginAt("/newAccount");
			assertTitleEquals("Create a new account for MetaboLights");

			//Test the login form
			System.out.println( "Testing accountForm form at '"+baseUrl+newAccount+"' for "+newUserName);
			setTextField( "email", newUserName );	 //email and username
			setTextField( "firstName", "Mitt" );       
			setTextField( "lastName", "Etternavn" ); 
			setTextField( "dbPassword", password ); 
			setTextField( "userVerifyDbPassword", password ); 
			setTextField( "affiliation", "EMBL-EBI" ); 
			setTextField( "affiliationUrl", "http://www.ebi.ac.uk/~kenneth" );
			selectOption( "address", "United Kingdom" );  //Country drop-down
			
			submit();     
			setScriptingEnabled(true);
			
			assertTitleEquals("Account has been requested");

		} catch (Exception e) {
			System.err.println( "Exception: " + e );
		}

	}

/*
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
