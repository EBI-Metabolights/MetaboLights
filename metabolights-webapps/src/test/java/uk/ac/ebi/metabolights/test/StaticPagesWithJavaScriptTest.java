package uk.ac.ebi.metabolights.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

/**
 * @author kenneth
 * 
 */
public class StaticPagesWithJavaScriptTest extends WebTestCase {

	private String baseUrl;

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

	// Forgotten password form
	private String urlTitlePasswordBefore;
	private String urlTitlePasswordAfter;
	private String resetSuccess;
	private String forgotPw;

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
			
			setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); // use HtmlUnit
			
			readProperties(); 	// Read parameters from properties file
			setLocalValues(); 	// Populate local parameters from properties file
			removeUser(); 		// Remove test user from database

		} catch (Exception e) {
			e.printStackTrace();
		}
		setBaseUrl(baseUrl);
	}

	public void tearDown() throws SQLException {
		removeUser();
		conn.close();
	}

	public void testNewAccountForm() {
		try {

			setScriptingEnabled(false); // turn off JavaScript

			// Test the login form
			System.out.println("Testing accountForm form at '" + baseUrl
					+ newAccount + "' for " + email);

			beginAt("/newAccount");
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
			assertTitleEquals(urlTitleAfter); // Check if we are at the correct
												// page

			System.out.println("Tried to create account for " + email);

			activateUser(); //Update the user in the database
			
			// setScriptingEnabled(true);

		} catch (Exception e) {
			System.err.println("Exception: " + e);
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

			setScriptingEnabled(false); // turn off JavaScript

			beginAt(baseUrl); // Start at the home page
			assertFormPresent("searchForm"); // Is the search form on the page?
			assertFormElementPresent("freeTextQuery"); // Does the form have the search field?
			setTextField("freeTextQuery", searchTerm); // set search term
			submit();
			assertTextPresent(searchTerm); // Can we see the search term on the page?

		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}

	}

	public void testHomePageSearchFormWithFilter() {
		try {

			// Test the search form
			System.out.println("Testing search for " + searchTerm);

			setScriptingEnabled(false); // turn off JavaScript

			beginAt(baseUrl); // Start at the home page
			assertFormPresent("searchForm"); // Is the search form on the page?
			assertFormElementPresent("freeTextQuery"); // Does the form have the
														// search field?
			setTextField("freeTextQuery", searchTerm); // set search term
			submit();
			assertTextPresent(searchTerm); // Can we see the search term on the
											// page?

			// assertCheckboxPresent(); //Can we see the filter form?

		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}

	}

}
