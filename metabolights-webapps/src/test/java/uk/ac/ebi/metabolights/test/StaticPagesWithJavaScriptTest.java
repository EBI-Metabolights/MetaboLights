/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/12 10:35 AM
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

package uk.ac.ebi.metabolights.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.xml.sax.SAXException;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

/**
 * @author kenneth
 * 
 */
public class StaticPagesWithJavaScriptTest extends WebTestCase {

	private String baseUrl;
	
	// Static pages
	private String aboutUrl;
	private String loginUrl;
	private String submitUrl;
	private String homeUrl;
	private String loginTextLabel;
	private String searchUrl;
	private String contactUrl;

	// Logout
	private String logout;
	private String loggedOutSuccess;
	private String logoutTextLabel;

	// Create new account
	private String newAccount;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String affiliation;
	private String affiliationUrl;
	private String address;
	private String urlTitleBefore;
	private String urlTitleAfter;
	private String accountCreated;

	// Search term for the searchForm
	private String searchTerm;
	private String checkBox1Name;
	private String checkBox1value;
	private String searchTerm2;
	private String checkBox2Name;
	private String checkBox2value;
	private String searchTermFail;

	// Forgotten password form
	private String urlTitlePasswordBefore;
	private String urlTitlePasswordAfter;
	private String resetSuccess;
	private String forgotPw;

	//Terms to search for in the "about" page
	private static String[] searchTerms = 
		{ "MetaboLights", "Steinbeck", "European Bioinformatics Institute", 
		  "Griffin", "Metabolomics Standards Initiative" };
	
	//Contact Us Form
	private String contactMessage;
	private String messageSent;
	
	//Account details form
	private String accountUrl;
	private String accountUpdatedMessage;
	

	protected Connection conn;
	Properties properties = new Properties();

	private void readProperties() {
		try {
			properties.load(new FileInputStream("src/test/resources/webapptest.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setLocalValues() {
		this.newAccount = properties.getProperty("newAccount");
		this.email = properties.getProperty("email");
		this.firstName = properties.getProperty("firstName");
		this.lastName = properties.getProperty("lastName");
		this.password = properties.getProperty("password");
		this.affiliation = properties.getProperty("affiliation");
		this.affiliationUrl = properties.getProperty("affiliationUrl");
		this.address = properties.getProperty("address");
		this.urlTitleBefore = properties.getProperty("urlTitleBefore");
		this.urlTitleAfter = properties.getProperty("urlTitleAfter");
		this.searchTerm = properties.getProperty("searchTerm");
		this.urlTitlePasswordBefore = properties.getProperty("urlTitlePasswordBefore");
		this.urlTitlePasswordAfter = properties.getProperty("urlTitlePasswordAfter");
		this.resetSuccess = properties.getProperty("resetSuccess");
		this.forgotPw = properties.getProperty("forgotPw");
		this.accountCreated = properties.getProperty("accountCreated");
		this.baseUrl = properties.getProperty("baseUrl");
		this.aboutUrl = properties.getProperty("aboutUrl");
		this.loginUrl = properties.getProperty("loginUrl");
		this.submitUrl = properties.getProperty("submitUrl");
		this.homeUrl = properties.getProperty("homeUrl");
		this.logout = properties.getProperty("logout");
		this.loggedOutSuccess = properties.getProperty("loggedOutSuccess");
		this.logoutTextLabel = properties.getProperty("logoutTextLabel");
		this.loginTextLabel = properties.getProperty("loginTextLabel");
		this.searchUrl = properties.getProperty("searchUrl");
		this.checkBox1Name = properties.getProperty("checkBox1Name");	
		this.checkBox1value = properties.getProperty("checkBox1value");
		this.checkBox2Name = properties.getProperty("checkBox2Name");	
		this.checkBox2value = properties.getProperty("checkBox2value");
		this.searchTerm2 = properties.getProperty("searchTerm2");
		this.searchTermFail = properties.getProperty("searchTermFail");
		this.contactUrl = properties.getProperty("contactUrl");
		this.contactMessage = properties.getProperty("contactMessage");
		this.messageSent = properties.getProperty("messageSent");
		this.accountUrl = properties.getProperty("accountUrl");
		this.accountUpdatedMessage = properties.getProperty("accountUpdatedMessage");
	}

	private Connection getDbConnection() throws ClassNotFoundException {

		Connection conn = null;

		try {
			Class.forName(properties.getProperty("jdbc.driverClassName"));
			conn = DriverManager.getConnection(
					properties.getProperty("jdbc.databaseurl"),
					properties.getProperty("jdbc.username"),
					properties.getProperty("jdbc.password"));

			conn.setAutoCommit(true);
			System.out.println("Database driver: " + conn.getMetaData().getDriverName());
			System.out.println("Driver version : " + conn.getMetaData().getDriverVersion());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

	public void removeUser() {
		String sqlStatement = "DELETE FROM user_detail where USERNAME = ? ";

		try {

			PreparedStatement prepared_statement = getDbConnection().prepareStatement(sqlStatement);
			prepared_statement.setString(1, email);
			Integer res = prepared_statement.executeUpdate();

			if (res == 1) {
				System.out.println("User deleted.");
			} else {
				System.out.println("User *not* deleted or did not exist.");
			}
			prepared_statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void activateUser() {
		String sqlStatement = "UPDATE user_detail SET STATUS = 'ACTIVE' where USERNAME = ? ";

		try {

			PreparedStatement prepared_statement = getDbConnection().prepareStatement(sqlStatement);
			prepared_statement.setString(1, email);
			Integer res = prepared_statement.executeUpdate();

			if (res == 1) {
				System.out.println("User activated.");
			} else {
				System.out.println("User not activated or did not exist.");
			}
			prepared_statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUp() {
		try {
			super.setUp();
			
			readProperties(); 	// Read parameters from properties file
			setLocalValues(); 	// Populate local parameters from properties file

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); // use HtmlUnit
		setBaseUrl(baseUrl);
		
	}

	public void tearDown() throws SQLException {
		
	}
	
	public void testHomePageLinks() throws IOException, SAXException {
		try {

			setScriptingEnabled(false); // turn off JavaScript
			
			beginAt("/");  // Obtain the main page

			//These links has to be on the home page
			for (String s: new String[]{homeUrl, submitUrl, aboutUrl, loginTextLabel}){
				System.out.println( "Seeing if link '"+s+"' is present" );
				assertLinkPresentWithText(s);
				System.out.println( "Trying to click '"+s+"' " );
				clickLinkWithText(s,0); //Click first link on the page with this text
			}

			System.out.println( "testHomePageLinks done!" );
			
		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Home page link test failed");
		}

	}
	
	public void testAboutPage() throws IOException, SAXException {
		try {

			setScriptingEnabled(false); // turn off JavaScript
			
			beginAt(contactUrl);  // Obtain the main page

			for (String s: searchTerms){
				System.out.println( "Trying to find term '"+s+"'" );
				assertTextPresent(s); 
			}

			System.out.println( "testAboutPage done!" );

		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("About page failed");
		}

	}
	
	public void testAboutConcatcUsPage() throws IOException, SAXException {
		try {

			setScriptingEnabled(false); // turn off JavaScript
			
			beginAt(contactUrl);  // Obtain the main page
			assertFormPresent("emailForm"); 		
			
			setTextField("firstName", firstName);
			setTextField("lastName", lastName);
			setTextField("emailAddress", email);
		
			setTextField("affiliation", affiliation);
			setTextField("affiliationUrl", affiliationUrl);
			setTextField("message", contactMessage); 

			submit(); // Submit the form
			
			assertTextPresent(messageSent); // Check if user got message that email was sent

			System.out.println( "testAboutConcatcUsPage done!" );

		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("About -> Contact Us page failed");
		}

	}
	
	public void testAccountDetailsPage() throws IOException, SAXException {
		try {

			setScriptingEnabled(false); // turn off JavaScript
			beginAt(baseUrl);  // Obtain the main page
			
			testNewAccountForm(false); 			// Create the account defined in "email", do not remove the new user
			getLoginResponse(email, password); 	// log in
			
			clickLinkWithText(accountUrl);  // Go to the account page
			
			setTextField("firstName", firstName + "_upd");
			setTextField("lastName", lastName + "_upd");
			setTextField("dbPassword", password);
			setTextField("userVerifyDbPassword", password);
			setTextField("affiliation", affiliation);
			setTextField("affiliationUrl", affiliationUrl);
			selectOption("address", address); // Country drop-down

			submit(); // Submit the form
			
			assertTextPresent(accountUpdatedMessage); // Check if user got message that email was sent

			System.out.println( "testAccountDetailsPage done!" );

		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Account Details page failed");
		}

	}
	
	
	public void testLoginForm() throws IOException, SAXException {
		try {
			testNewAccountForm(false); //Create the account defined in "email", do not remove the new user
			getLoginResponse(email, password);
			
		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Login failed");
		}

	}
	
	private void getLoginResponse(String userName, String password){
		try {

			setScriptingEnabled(false); // turn off JavaScript
			
			beginAt(loginUrl);  // Obtain the login page

			//Test the login form
			System.out.println( "Testing login at '"+baseUrl+loginUrl+" for "+userName);

			setTextField("username", userName);
	        setTextField("password", password);
	        submit(); // submit the form 


		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("Login failed");
		}
	}
	

	/*
	 * Test the login form.  Create a new user and activate the account
	 */
	public void testNewAccountForm(Boolean remove) {
		try {
			
			removeUser(); 		// Remove test user from database

			setScriptingEnabled(false); // turn off JavaScript

			System.out.println("Testing accountForm form at '" + baseUrl
					+ newAccount + "' for " + email);

			beginAt("/"+newAccount);
			assertTitleEquals(urlTitleBefore);

			setTextField("email", email);
			setTextField("firstName", firstName);
			setTextField("lastName", lastName);
			setTextField("dbPassword", password);
			setTextField("userVerifyDbPassword", password);
			setTextField("affiliation", affiliation);
			setTextField("affiliationUrl", affiliationUrl);
			selectOption("address", address); // Country drop-down

			submit(); // Submit the form
			assertTextPresent(accountCreated); // Check if user was created
			assertTitleEquals(urlTitleAfter);  // Check if we are at the correct page
			
			activateUser(); // Update the user in the database

			System.out.println("Tried to create account for " + email);
			
			if (remove) //Should we remove the user when completed?
				removeUser();
			
			//conn.close();

		} catch (Exception e) {
			removeUser();
			System.err.println("Exception: " + e);
		}

	}
	
	public void testLogout() throws IOException, SAXException {
		try {

			setScriptingEnabled(false); // turn off JavaScript

			//First you need to be logged in
			beginAt(loginUrl);
			assertLinkNotPresent(logout);			// Check that logout is not present
			testLoginForm();						// Set up new user	

			//Try to find and click on "log out"			
			assertLinkPresentWithText(logoutTextLabel);		// Can we see the log out link?
			clickLinkWithText(logoutTextLabel,0); 	// Click first logout link
			assertTextPresent(loggedOutSuccess); 	// Did we display the correct text?
			assertLinkNotPresent(logout);			// Check that logout is not present after logged out	
			assertTrue(true);

		} catch (Exception e) {
			System.err.println( "Exception: " + e );
			fail("logout failed");
		}

	}	
	

	public void testForgottenPasswordForm() {
		try {

			setScriptingEnabled(false); // turn off JavaScript

			// Test the login form
			System.out.println("Testing Forgotten Password form at '" + baseUrl
					+ forgotPw + "' for " + email);

			beginAt(forgotPw);
			assertTitleEquals(urlTitlePasswordBefore); // Check that the password page has rendered

			setTextField("emailAddress", email);
			submit(); // Submit the form

			assertTitleEquals(urlTitlePasswordAfter); // Correct page?
			assertTextPresent(resetSuccess); // Did we display the correct text?

			System.out.println("Tried to reset password for " + email);

		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}

	}

	public void testHomePageSearchForm() {
		try {

			// Test the search form
			System.out.println("Testing search for " + searchTerm);

			setScriptingEnabled(false); 						// turn off JavaScript

			beginAt(baseUrl); 									// Start at the Search page
			assertTablePresent("contentspane");					// Is the main table present
			assertFormPresent("searchForm"); 					// Is the search form on the page?
			setWorkingForm("searchForm");						// Switch to the correct form
			assertFormElementPresent("freeTextQuery");			// Does the form have a search field?
			setTextField("freeTextQuery", searchTerm);			// Set search term
			assertTextFieldEquals("freeTextQuery", searchTerm);	// Has the search string been set?			

			submit(); 											// Submit the form
			
			assertTextPresent(searchTerm); 						// Can we see the search term on the page?

		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}

	}

	public void testHomePageSearchFormWithFilter() {
		try {

			// Test the search form
			System.out.println("Testing search for " + searchTerm);

			setScriptingEnabled(false); 						// turn off JavaScript

			beginAt(searchUrl); 								// Start at the Search page
			assertTablePresent("contentspane");					// Is the main table present
			assertFormPresent("searchForm"); 					// Is the search form on the page?
			
			setWorkingForm("searchForm");						// Switch to the correct form
			assertFormElementPresent("freeTextQuery");			// Does the form have a search field?
			
			//Search term 1
			setTextField("freeTextQuery", searchTerm);			// Set search term
			assertTextFieldEquals("freeTextQuery", searchTerm);	// Has the search string been set?			
			submit(); 											// Submit the form
			
			assertTextPresent(searchTerm); 						// Can we see the search term on the page?
		
			//Check 1st checkbox
			assertFormPresent("searchFilter"); 						// Is the filter form on the page? 
			setWorkingForm("searchFilter");	
			assertCheckboxPresent(checkBox1Name,checkBox1value);	// Can we see the filter form for organism and <search term>?
			
			try { 	//Just to catch and ignore problems with JavaScript
				setScriptingEnabled(true);
				checkCheckbox(checkBox1Name,checkBox1value);
			} catch (Exception e) {
			}
			setScriptingEnabled(false); 
			
			assertTextPresent(searchTerm); 							// Can we still see the search term on the page?
			
			//Check 2nd checkbox
			assertCheckboxPresent(checkBox2Name,checkBox2value);
			try {	//Just to catch and ignore problems with JavaScript
				setScriptingEnabled(true);
				checkCheckbox(checkBox2Name,checkBox2value);		// Check a non-value in the second checkbox (technology section)
			} catch (Exception e) {
			}
			setScriptingEnabled(false); 
			
			assertTextPresent(searchTerm); 							// Can we still see the search term on the page?
			
			//String pagesS = getPageSource();  // For debug
			
			//Combination of 1 and 2 should give no results
			assertTextPresent(searchTermFail); 						// Confirm no results, eg. our text when no results
			
			//2nd search
			System.out.println("Testing search for " + searchTerm2);
			setWorkingForm("searchForm");	
			setTextField("freeTextQuery", searchTerm2);				// Try another search term, differs from first term
			submit(); 
			assertTextPresent(searchTerm2); 
			setWorkingForm("searchFilter");	
			assertCheckboxNotPresent(checkBox1Name,checkBox1value); // Should *not* see the same checkbox as for first search
			

		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}

	}

}
