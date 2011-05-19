package uk.ac.ebi.metabolights.authenticate;

import java.security.MessageDigest;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sun.misc.BASE64Encoder;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.UserService;

/**
 * Class to process an {@link IsaTabAuthentication} implementation. 
 * @author Mark Rijnbeek
 */
@Service
public class IsaTabAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		if (auth.getCredentials()==null || auth.getCredentials().toString().equals("") || auth.getName()==null ||auth.getName().equals("") ) {
			throw new org.springframework.security.authentication.BadCredentialsException(PropertyLookup.getMessage("msg.reqFieldMissing"));
		}
		
		MetabolightsUser mtblUser = userService.lookupByName(auth.getName());
		if (mtblUser == null)
			throw new org.springframework.security.authentication.InsufficientAuthenticationException(PropertyLookup.getMessage("msg.incorrUserPw"));

		if (! encode(auth.getCredentials().toString()).equals(mtblUser.getDbPassword()))	
			throw new org.springframework.security.authentication.InsufficientAuthenticationException(PropertyLookup.getMessage("msg.incorrUserPw"));
		
		return new IsaTabAuthentication(mtblUser," "); // TODO used 2nd argument ?
	}

	/**
	 * Encoding of password as is done by IsaTab.
	 * Thank you, SDPG.
	 * 
	 * @param plaintext
	 * @return
	 */
	private String encode(String plaintext) {

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
