package uk.ac.ebi.metabolights.webservice.controllers;

import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Arrays;

/**
 * Created by venkata on 22/08/2016.
 */
@Controller
@RequestMapping("labs-project")
public class LabsProjectController {

    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    /**
     * Authenticate user and generate JWT token if the user is valid
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "content", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getProjectContentdetails(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String id = (String) serverRequest.get("id");

        String folderLocation = PropertiesUtil.getProperty("userSpace") + user.getApiToken() + File.separator + id;

        JSONArray contentJSONArray = new JSONArray(Arrays.asList(FileUtil.getFtpFolderList(folderLocation)));

        restResponse.setContent(contentJSONArray.toJSONString());

        return restResponse;

    }
}