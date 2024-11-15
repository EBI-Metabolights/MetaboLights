/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/9/13 4:37 PM
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

package uk.ac.ebi.metabolights.authenticate;
import uk.ac.ebi.metabolights.authenticate.AppRole;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Process an {@link IsaTabAuthentication} implementation.
 * Used for the user login process.
 *
 * @author Mark Rijnbeek
 */
@Service
@Component
public class IsaTabAuthenticationProvider implements AuthenticationProvider {

	private static Logger logger = LoggerFactory.getLogger(IsaTabAuthenticationProvider.class);

	/**
	 * Authenticates a user following Spring's security framework.
	 * Checks that the user exists, is active and has entered correct password.
     * @param auth Spring Authentication (via login form)
	 */
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		// username / password set?
		if (auth.getCredentials()==null || auth.getCredentials().toString().equals("") || auth.getName()==null ||auth.getName().equals("") ) {
			throw new org.springframework.security.authentication.BadCredentialsException(PropertyLookup.getMessage("msg.reqFieldMissing"));
		}
		RestResponse<MetabolightsUser> response = null;
		MetabolightsUser mtblUser = null;
		try {
			response = this.authenticate(auth.getName(), auth.getCredentials().toString());
			if (response != null && response.getContent() == null){
				if ( response.getMessage().startsWith("Invalid user or credential")){
					throw new InsufficientAuthenticationException(PropertyLookup.getMessage("msg.incorrUserPw"));
				}
				if (response.getMessage().startsWith("Invalid user status")){
					throw new InsufficientAuthenticationException(PropertyLookup.getMessage("msg.accountInactive"));
				}
				if (response.getMessage().startsWith("Invalid user role")){
					throw new InsufficientAuthenticationException(PropertyLookup.getMessage("msg.incorrUserPw"));
				}
				throw new InsufficientAuthenticationException(PropertyLookup.getMessage("msg.authenticationFailed"));
			}			
			mtblUser = response.getContent();
			logger.info("User authenticated "+mtblUser.getUserName()+" ID="+mtblUser.getUserId());
			
			updateJwtExpirationTime(mtblUser);
			JSONObject localObject = new JSONObject();
			localObject.put("userName", mtblUser.getUserName());
			localObject.put("firstName", mtblUser.getFirstName());
			localObject.put("lastName", mtblUser.getLastName());
			localObject.put("fullName", mtblUser.getFullName());
			localObject.put("email", mtblUser.getEmail());
			localObject.put("status", mtblUser.getStatus().name());
			localObject.put("orcid", mtblUser.getOrcId());
			localObject.put("apiToken", mtblUser.getApiToken());
			localObject.put("joinDate", mtblUser.getJoinDate().getTime());
			localObject.put("address", mtblUser.getAddress());
			if (mtblUser.getRole().intValue() == AppRole.ROLE_SUPER_USER.getBit()){
				localObject.put("role", AppRole.ROLE_SUPER_USER.getAuthority());
			} else if (mtblUser.getRole().intValue() == AppRole.ROLE_SUBMITTER.getBit()){
				localObject.put("role", AppRole.ROLE_SUBMITTER.getAuthority());
			} else if (mtblUser.getRole().intValue() == AppRole.ROLE_REVIEWER.getBit()){
				localObject.put("role", AppRole.ROLE_REVIEWER.getAuthority());
			}
			mtblUser.setLocalUserData(localObject.toString()); 
			
			return new IsaTabAuthentication(mtblUser," "); // TODO used 2nd argument ?
		} catch(InsufficientAuthenticationException ex) {
			throw ex;
		} 
		catch (Exception e) {

			throw new InsufficientAuthenticationException(PropertyLookup.getMessage("msg.authenticationFailed"));
		}
	}

	private void updateJwtExpirationTime(MetabolightsUser user) {
		String jwt;
		long expirationTime;
		try {
			jwt = user.getJwtToken();
			String[] parts = jwt.split("\\.");

			// String part0 = new String(Base64.getUrlDecoder().decode(parts[0]));
			String part1 = new String(Base64.getUrlDecoder().decode(parts[1]));
			// String signature = new String(Base64.getUrlDecoder().decode(parts[2]));
			// JSONObject header = new JSONObject(part0);
			JSONObject payload = new JSONObject(part1);

			expirationTime = payload.getLong("exp");
			user.setJwtTokenExpireTime(expirationTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addJwtToUser(MetabolightsUser user) {
		String jwt;
		long expirationTime;
		try {
			jwt = getJwt(user);
			String[] parts = jwt.split("\\.");

			// String part0 = new String(Base64.getUrlDecoder().decode(parts[0]));
			String part1 = new String(Base64.getUrlDecoder().decode(parts[1]));
			// String signature = new String(Base64.getUrlDecoder().decode(parts[2]));
			// JSONObject header = new JSONObject(part0);
			JSONObject payload = new JSONObject(part1);

			expirationTime = payload.getLong("exp");
			user.setJwtToken(jwt);
			user.setJwtTokenExpireTime(expirationTime);
			String localUser = getLocalUser(user, jwt);
			user.setLocalUserData(localUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RestResponse<MetabolightsUser> authenticate(String username, String password)  throws Exception {

		 HttpURLConnection conn = null;
		 OutputStreamWriter out = null;
		 RestResponse<MetabolightsUser> response = null;
		 try {
			 String authPath = "auth/login";
			 String body = "{'email':'"+ username +"', 'secret':'"+ password+"'}";
			 conn = getGetConnection(authPath);
			 conn.setRequestMethod("POST");
			 conn.setRequestProperty("Accept", "application/json");
			 conn.setRequestProperty("content-type", "application/json");
			 conn.setDoOutput(true);
			 out = new OutputStreamWriter(conn.getOutputStream());
			 out.write(body);
			 out.close();
			 out = null;
			 BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			 String responseStr = IOUtils.toString(br);
			 
			 if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				response = MetabolightsWsClient.deserializeJSONString(responseStr, MetabolightsUser.class);
				response.getContent().setJwtToken(conn.getHeaderField("jwt"));
			 } else {
				RestResponse<String> response2 = MetabolightsWsClient.deserializeJSONString(responseStr, String.class);
				response = new RestResponse<MetabolightsUser>();
				response.setContent(null);
				response.setErr(response2.getErr());
				response.setMessage(response2.getMessage());
			 }
			 return response;
			//  throw new Exception("An error occured when reatcing one time token");
		 } catch (Exception e) {
			 BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
			 String responseStr = IOUtils.toString(br);
			 RestResponse<String> response2 = MetabolightsWsClient.deserializeJSONString(responseStr, String.class);
			 response = new RestResponse<MetabolightsUser>();
			 response.setContent(null);
			 response.setErr(response2.getErr());
			 response.setMessage(response2.getMessage());
			 return response;
		 }finally {
			try {
				if (out != null) {
					out.close();
				}
				if (conn != null){
					conn.disconnect();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		 }
	}

	public static HttpURLConnection getPostConnection(String authPath, String body) throws Exception {
		OutputStreamWriter out = null;
		HttpURLConnection conn = null;
		try {
			String wsPrefix = PropertiesUtil.getProperty("metabolightsPythonWsInternalUrl");
			URL url = null;

			if (wsPrefix.endsWith("/")) {
				url = new URL(wsPrefix + authPath);
			} else {
				url = new URL(wsPrefix + "/" + authPath);
			}

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("content-type", "application/json");
			conn.setDoOutput(true);
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(body);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return conn;
	}

	public static HttpURLConnection getGetConnection(String path) throws Exception {
		try {
			String wsPrefix = PropertiesUtil.getProperty("metabolightsPythonWsInternalUrl");
			URL url = null;

			if (wsPrefix.endsWith("/")) {
				url = new URL(wsPrefix + path);
			} else {
				url = new URL(wsPrefix + "/" + path);
			}

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("content-type", "application/json");
			conn.setDoOutput(true);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	public static String getLoginOneTimeToken(MetabolightsUser user) throws Exception {
		if (user == null || user.getJwtToken() == null)
			return null;

		HttpURLConnection conn = null;
		try {
			String authPath = "auth/create-onetime-token";
			conn = getGetConnection(authPath);
			conn.setRequestProperty("Authorization", "Bearer " + user.getJwtToken());
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
				String responseStr = IOUtils.toString(br);

				JSONObject response = new JSONObject(responseStr);
				String oneTimeToken = response.getString("one_time_token");
				return oneTimeToken;
			}

			throw new Exception("An error occured when reatcing one time token");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	public static String getJwt(MetabolightsUser user) throws Exception {
		HttpURLConnection conn = null;
		try {
			String body = String.format("{'token': '%s'}", user.getApiToken());
			String authPath = "auth/login-with-token";
			conn = getPostConnection(authPath, body);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return conn.getHeaderField("jwt");
			}

			throw new Exception("An error occured when creating JWT");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	public static String getLocalUser(MetabolightsUser user, String jwt) throws Exception {
		HttpURLConnection conn = null;
		try {
			String body = String.format("{'jwt': '%s', 'user': '%s' }", jwt, user.getUserName());
			String authPath = "auth/user";
			conn = getPostConnection(authPath, body);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				String responseStr = IOUtils.toString(br);

				JSONObject response = new JSONObject(responseStr);
				String ownerStr = response.getString("content");
				JSONObject owner = new JSONObject(ownerStr);
				String localUser = owner.getString("owner");
				return localUser;
			}

			throw new Exception("An error occured when creating local User");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}


	/**
	 * Encoding of password as is done by IsaTab.
	 * Thank you, SDPG.
	 *
	 * @param plaintext
	 * @return
	 */
	public static String encode(String plaintext) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(plaintext.getBytes("UTF-8"));
			byte raw[] = md.digest();

			String hash = new String(Base64.getEncoder().encode(raw));
			return hash;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Could not encrypt password for ISA db verification");
		}
	}


	/**
	 * Required for implementing interface.
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
