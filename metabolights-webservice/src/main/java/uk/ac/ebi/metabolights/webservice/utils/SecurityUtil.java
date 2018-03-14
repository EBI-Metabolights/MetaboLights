package uk.ac.ebi.metabolights.webservice.utils;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

/**
 * Created by venkata on 26/10/2016.
 */
public class SecurityUtil {

    public static User validateJWTToken(HttpServletRequest request){

        String jwt = request.getHeader("jwt");
        String email = request.getHeader("user");

        return getUser(jwt, email);

    }

    public static User validateJWTToken(String data){

        JSONObject serverRequest = parseRequest(data);

        String jwt = (String) serverRequest.get("jwt");

        String email = (String) serverRequest.get("user");

        return getUser(jwt, email);

    }

    /**
     * Parse the input data into a JSON Object
     * @param data
     * @return
     */
    public static JSONObject parseRequest(String data){

        JSONParser parser = new JSONParser();
        JSONObject uploadRequest = null;

        try {
            uploadRequest = (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return uploadRequest;

    }

    public static User getUser(String jwt, String email){
        try {

            UserServiceImpl usi = new UserServiceImpl();
            User user = usi.getUserById(email);
            String tokenSecret = user.getApiToken();
            Key key = new HmacKey(tokenSecret.getBytes("UTF-8"));

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireSubject()
                    .setExpectedIssuer("Metabolights")
                    .setExpectedAudience("Metabolights Labs")
                    .setVerificationKey(key)
                    .setRelaxVerificationKeyValidation() // relaxes key length requirement
                    .build();

            try {

                JwtClaims processedClaims = jwtConsumer.processToClaims(jwt);
                return user;

            } catch (InvalidJwtException e) {

                return null;

            }

        }catch (Exception e){

            return null;

        }
    }

}
