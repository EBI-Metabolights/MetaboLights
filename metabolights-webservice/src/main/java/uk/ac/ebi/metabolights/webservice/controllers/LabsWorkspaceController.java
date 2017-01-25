package uk.ac.ebi.metabolights.webservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetaboLightsLabsProjectDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetaboLightsLabsWorkspaceDAO;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Created by venkata on 22/08/2016.
 */
@Controller
@RequestMapping("labs-workspace")
public class LabsWorkspaceController {

    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);
    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
    private StudyDAO studyDAO;

    @RequestMapping(value = "initialise", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> initialise(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        restResponse.setContent(getWorkspaceInfo(user, true).getAsJSON());

        response.setHeader("Access-Control-Allow-Origin", "*");

        return restResponse;

    }


    /**
     * Fetch the workspace settings
     * @param data (User_API_TOKEN)
     * @param request
     * @param response
     * @return RestResponse
     */
    @RequestMapping(value = "settings", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getWorkspaceSettings(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("api_token");

        User user = authenticateUser(userToken);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesn't exist error!");

            return restResponse;

        }

        logger.info("User exists. Fetching workspace information");

        restResponse.setContent(getWorkspaceInfo(user, true).getAsJSON());

        return restResponse;

    }

    /**
     * Fetch the user workspace details, initialise if its not.
     * @param user
     * @param initialiseWorkspaceFlag
     * @return
     */
    public static MLLWorkSpace getWorkspaceInfo(User user, boolean initialiseWorkspaceFlag){

        String workspaceLocation = null;

        workspaceLocation = PropertiesUtil.getProperty("userSpace") + user.getApiToken();

        MetaboLightsLabsWorkspaceDAO metaboLightsLabsDAO = new MetaboLightsLabsWorkspaceDAO();

        return metaboLightsLabsDAO.getWorkSpaceInfo(user, workspaceLocation, initialiseWorkspaceFlag);

    }

    /**
     * Fetch the list of projects in a workspace
     * @param data (User_API_TOKEN)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "projects", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getProjects(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("api_token");

        User user = authenticateUser(userToken);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesn't exist error!");

            return restResponse;

        }

        restResponse.setContent(getWorkspaceProjects(user));

        return restResponse;

    }

    /**
     * Get array of MLLProject associated with the users workspace
     * @param user
     * @return
     */
    private String getWorkspaceProjects(User user){

        String workspaceLocation = null;

        workspaceLocation = PropertiesUtil.getProperty("userSpace") + user.getApiToken();

        MetaboLightsLabsWorkspaceDAO metaboLightsLabsDAO = new MetaboLightsLabsWorkspaceDAO();

        List<MLLProject> mllProjects = metaboLightsLabsDAO.getWorkSpaceInfo(user, workspaceLocation, false).getProjects();

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.writeValueAsString(mllProjects);

        } catch (JsonProcessingException e) {

            e.printStackTrace();

        }

        return workspaceLocation;

    }

    /**
     * Fetch aspera configuration for uploading data to MetaboLights Labs
     * @param data
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws JoseException
     * @throws DAOException
     */
    @RequestMapping(value = "asperaConfiguration", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> asperaConfiguration(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<String>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("api_token");
        String projectId = (String) serverRequest.get("project_id");
        // replace all double quotes in the string - python boolean issue #todo
        boolean createNewProject = Boolean.valueOf(((String) serverRequest.get("new_project_flag")).replace("\"", "\\\""));

        User user = authenticateUser(userToken);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesnt exist error!");

            return restResponse;

        }


        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user, createNewProject);

        if (mllWorkSpace == null){

            logger.warn("Error fetching the workspace info associated with the user token: " + userToken);

            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return restResponse;

        }

        logger.info("Checking if a project with the give projectId exists");

        MetaboLightsLabsWorkspaceDAO metaboLightsLabsDAO = new MetaboLightsLabsWorkspaceDAO();

        logger.info("Checking if a project is not null or empty");

        if(projectId != null){

            if(!projectId.trim().equalsIgnoreCase("")) {

                boolean projectExists = metaboLightsLabsDAO.isProjectExist(mllWorkSpace, projectId);

                if (projectExists) {

                    MLLProject mllProject = createOrGetMLLProject(mllWorkSpace, projectId, user.getApiToken(), false);

                    restResponse.setContent(mllProject.getAsperaSettings());

                    return restResponse;

                }
            }

        }

        if (createNewProject){

            MLLProject mllProject = createOrGetMLLProject(mllWorkSpace, projectId, user.getApiToken(), true);

            restResponse.setContent(mllProject.getAsperaSettings());

            return restResponse;

        }

        logger.warn("Error finding the project or creating a new project. Check the logs for more details.");

        response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        return restResponse;

    }

    /**
     * Fetch aspera configuration for uploading data to MetaboLights Labs
     * @param data
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws JoseException
     * @throws DAOException
     */
    @RequestMapping(value = "createProject", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> createProject(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<String>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String title = (String) serverRequest.get("title");

        String description = (String) serverRequest.get("description");

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }


        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesnt exist error!");

            return restResponse;

        }

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user, true);

        if (mllWorkSpace == null){

            logger.warn("Error fetching the workspace info associated with the user token");

            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return restResponse;

        }

        MetaboLightsLabsWorkspaceDAO metaboLightsLabsDAO = new MetaboLightsLabsWorkspaceDAO();

        MLLProject mllProject = createMLLProject(mllWorkSpace, user.getApiToken(), title, description);


        if(mllProject != null){

            Boolean cloneProject = (Boolean) serverRequest.get("cloneProject");

            if(cloneProject){

                String studyId = (String) serverRequest.get("studyId");

                if (studyId != null || studyId.matches(METABOLIGHTS_ID_REG_EXP)){

                    logger.info("Cloning study: " + studyId);

                    String studyLocation = "";

                    try{

                        studyDAO = getStudyDAO();

                        // Get the study

                            Study study = studyDAO.getStudy(studyId, user.getApiToken(), false);



                            studyLocation = study.getStudyLocation();

                            String projectLocation = mllProject.getProjectLocation();

                    }  catch (DAOException ex) {

                        logger.error("Error looking for study {}: {}", studyId, ex.getMessage());

                    }

                }

            }

        }


        ObjectMapper mapper = new ObjectMapper();

        restResponse.setContent(mapper.writeValueAsString(mllProject));

        return restResponse;

    }

    private uk.ac.ebi.metabolights.repository.dao.StudyDAO getStudyDAO() throws DAOException {

        if (studyDAO == null){

            studyDAO = DAOFactory.getInstance().getStudyDAO();

        }
        return studyDAO;
    }


    private MLLProject createMLLProject(MLLWorkSpace mllWorkSpace, String userToken, String title, String description){

        String workspaceLocation = PropertiesUtil.getProperty("userSpace") + userToken;

        String asperaUser = PropertiesUtil.getProperty("asperaUser") ;

        String asperaSecret = PropertiesUtil.getProperty("asperaSecret") ;

        MetaboLightsLabsProjectDAO metaboLightsLabsProjectDAO = new MetaboLightsLabsProjectDAO();

        return  metaboLightsLabsProjectDAO.createMLLProject(workspaceLocation, mllWorkSpace, userToken, asperaUser, asperaSecret, title, description);

    }



    private MLLProject createOrGetMLLProject(MLLWorkSpace mllWorkSpace, String projectId, String userToken, boolean createNewFlag){

        String workspaceLocation = PropertiesUtil.getProperty("userSpace") + userToken;

        String asperaUser = PropertiesUtil.getProperty("asperaUser") ;

        String asperaSecret = PropertiesUtil.getProperty("asperaSecret") ;

        MetaboLightsLabsProjectDAO metaboLightsLabsProjectDAO = new MetaboLightsLabsProjectDAO();

        return  metaboLightsLabsProjectDAO.getMLLProject(projectId, workspaceLocation, mllWorkSpace, createNewFlag, userToken ,asperaUser, asperaSecret);

    }




    /**
     * Authenticate user based on the API_TOKEN
     * @param userToken
     * @return
     */

    private User authenticateUser(String userToken){

        UserServiceImpl usi = null;

        try {

            usi = new UserServiceImpl();

        } catch (DAOException e) {

            e.getStackTrace();
        }

        logger.info("Requesting user with the token" + userToken);

        User user = usi.lookupByToken(userToken);

        return user;

    }

}
