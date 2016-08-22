package uk.ac.ebi.metabolights.webservice.controllers;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetaboLightsLabsDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by venkata on 22/08/2016.
 */
@Controller
@RequestMapping("workbench")
public class LabsWorkbenchController {
    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> authenticateUser(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        JSONParser parser = new JSONParser();
        JSONObject loginData = null;

        try {
            loginData = (JSONObject) parser.parse(data);
        } catch (ParseException e) {

        }

        String userToken = (String) loginData.get("token");

        User user = null;

        try {

            UserDAO userDAO = new UserDAO();
            user = userDAO.findByToken(userToken);

        } catch (DAOException e) {
            e.printStackTrace();
        }

        RestResponse<String> restResponse = new RestResponse<>();

        restResponse.setContent(getWorkBenchSettings(user));

        return restResponse;

    }


    private String getWorkBenchSettings(User user){

        String workbenchLocation = PropertiesUtil.getProperty("userSpace") + user.getApiToken();

        return (new MetaboLightsLabsDAO()).getWorkbenchSettings(user, workbenchLocation);

    }

}
