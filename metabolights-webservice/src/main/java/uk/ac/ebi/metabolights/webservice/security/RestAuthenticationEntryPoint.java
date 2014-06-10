package uk.ac.ebi.metabolights.webservice.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: conesa
 * Date: 06/06/2014
 * Time: 15:39
 */
@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence( HttpServletRequest request, HttpServletResponse response,
						  AuthenticationException authException ) throws IOException {
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
	}
}