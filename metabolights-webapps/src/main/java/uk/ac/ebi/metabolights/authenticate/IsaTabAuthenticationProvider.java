/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 09/09/13 09:29
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.authenticate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.UserService;

import java.security.MessageDigest;

/**
 * Process an {@link IsaTabAuthentication} implementation.
 * Used for the user login process.
 *
 * @author Mark Rijnbeek
 */
@Service
public class IsaTabAuthenticationProvider implements AuthenticationProvider {

	private static Logger logger = Logger.getLogger(IsaTabAuthenticationProvider.class);

	@Autowired
	private UserService userService;

	/**
	 * Authenticates a user following Spring's security framework.
	 * Checks that the user exists, is active and has entered correct password.
     * @param auth Spring Authentication (via login form)
	 */
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {

		// username / password set?
		if (auth.getCredentials()==null || auth.getCredentials().toString().equals("") || auth.getName()==null ||auth.getName().equals("") ) {
			throw new org.springframework.security.authentication.BadCredentialsException(PropertyLookup.getMessage("msg.reqFieldMissing"));
		}

		// Does user exist?
		MetabolightsUser mtblUser = userService.lookupByUserName(auth.getName());
		if (mtblUser == null)
			throw new org.springframework.security.authentication.InsufficientAuthenticationException(PropertyLookup.getMessage("msg.incorrUserPw"));

		// Is this user active?
		if (!mtblUser.getStatus().equals(MetabolightsUser.UserStatus.ACTIVE.getValue()))
			throw new org.springframework.security.authentication.InsufficientAuthenticationException(PropertyLookup.getMessage("msg.accountInactive"));


		// Is this the right password?
		logger.debug("comparing given password '"+encode(auth.getCredentials().toString())+"' to db password '"+mtblUser.getDbPassword()+"'" );
		if (! encode(auth.getCredentials().toString()).equals(mtblUser.getDbPassword()))
			throw new org.springframework.security.authentication.InsufficientAuthenticationException(PropertyLookup.getMessage("msg.incorrUserPw"));

		logger.info("authenticated "+mtblUser.getUserName()+" ID="+mtblUser.getUserId());

		return new IsaTabAuthentication(mtblUser," "); // TODO used 2nd argument ?
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
			String hash = (new BASE64Encoder()).encode(raw);
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
