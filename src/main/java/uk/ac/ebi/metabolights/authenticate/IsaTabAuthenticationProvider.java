package uk.ac.ebi.metabolights.authenticate;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;
import uk.ac.ebi.metabolights.service.UserService;

@Service
public class IsaTabAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {

		//TODO user properties

		if (auth.getCredentials()==null || auth.getCredentials().toString().equals("") || auth.getName()==null ||auth.getName().equals("") ) {
			throw new org.springframework.security.authentication.BadCredentialsException("Required field(s) are missing!");
		}
		
		MetabolightsUser mtblUser = userService.lookupByName(auth.getName());
		if (mtblUser == null)
			throw new org.springframework.security.authentication.InsufficientAuthenticationException("Incorrect username/password");

		if (! encode(auth.getCredentials().toString()).equals(mtblUser.getDbPassword()))	
			throw new org.springframework.security.authentication.InsufficientAuthenticationException("Incorrect username/password");
		
		return new IsaTabAuthentication(mtblUser," "); // TODO 2nd argument is ..?

	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

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
}
