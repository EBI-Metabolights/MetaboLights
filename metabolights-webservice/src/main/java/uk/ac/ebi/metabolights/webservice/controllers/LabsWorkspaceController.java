package uk.ac.ebi.metabolights.webservice.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response;
import org.jose4j.lang.JoseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.method.P;
import uk.ac.ebi.metabolights.repository.model.*;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import org.jose4j.json.internal.json_simple.JSONObject;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetaboLightsLabsProjectDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetaboLightsLabsWorkspaceDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;

/**
 * Created by venkata on 22/08/2016.
 */
@Controller
@RequestMapping("labs-workspace")
public class LabsWorkspaceController {

    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);
    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
    private StudyDAO studyDAO;

    /**
     * Initialise the workspace - return the dashboard settings to bootstrap the labs
     * @param data
     * @param response
     * @return
     */
    @RequestMapping(value = "initialise", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> initialise(@RequestBody String data, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

        String asperaUser = PropertiesUtil.getProperty("asperaUser");

        String asperaSecret = PropertiesUtil.getProperty("asperaSecret");

        for ( MLLProject project : mllWorkSpace.getProjects() ) {

            project.setAsperaSettings("{ \"asperaURL\" : \""+ user.getApiToken() + File.separator + project.getId() + "\", \"asperaUser\" : \""+ asperaUser + "\",  \"asperaServer\" : \"ah01.ebi.ac.uk\", \"asperaSecret\" :  \""+ asperaSecret + "\" }");

        }

        restResponse.setContent(mllWorkSpace.getAsJSON());

        response.setHeader("Access-Control-Allow-Origin", "*");

        return restResponse;

    }


    /**
     * Fetch the list of projects in a workspace
     * @param data (User_API_TOKEN)
     * @param response
     * @return
     */
    @RequestMapping(value = "projects", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getProjects(@RequestBody String data, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("api_token");

        User user = authenticateUser(userToken);

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesn't exist error!");

            return restResponse;

        }

        restResponse.setContent(getWorkspaceProjects(user));

        return restResponse;

    }


    /**
     * Fetch the list of projects in a workspace
     * @param data (User_API_TOKEN)
     * @param response
     * @return
     */
    @RequestMapping(value = "mapStudy", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> mapMetaboLightsStudyToLabsProject(@RequestBody String data, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("token");
        String projectId = (String) serverRequest.get("projectId");
        String studyId = (String) serverRequest.get("studyId");

        User user = authenticateUser(userToken);

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesn't exist error!");

            return restResponse;

        }

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

        if (mllWorkSpace == null) {

            logger.warn("Error fetching the workspace info associated with the user");

            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return restResponse;

        }

        MLLProject mllProject = mllWorkSpace.getProject(projectId);

        if(mllProject != null){

            mllProject.setStudyId(studyId);

            mllProject.setBusy(false);

            mllProject.save();

            mllWorkSpace.appendOrUpdateProject(mllProject);

            restResponse.setContent("study mapped successfully");

        }

        return restResponse;

    }

    /**
     * Get array of MLLProject associated with the users workspace
     *
     * @param user
     * @return
     */
    private String getWorkspaceProjects(User user) {

        String root = PropertiesUtil.getProperty("userSpace");

        MetaboLightsLabsWorkspaceDAO metaboLightsLabsDAO = new MetaboLightsLabsWorkspaceDAO(user, root);

        List<MLLProject> mllProjects = metaboLightsLabsDAO.getMllWorkSpace().getProjects();

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.writeValueAsString(mllProjects);

        } catch (JsonProcessingException e) {

            e.printStackTrace();

        }

        return null;
    }

    /**
     * Fetch the list of projects in a workspace
     * @param data (User_API_TOKEN)
     * @param response
     * @return
     */
    @RequestMapping(value = "deleteProject", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> deleteProjectFromWorkspace(@RequestBody String data, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<String>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("project_id");

        User user = SecurityUtil.validateJWTToken(data);

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }


        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesnt exist error!");

            return restResponse;

        }

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

        if (mllWorkSpace == null) {

            logger.warn("Error fetching the workspace info associated with the user");

            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return restResponse;

        }

        MLLProject mllProject = mllWorkSpace.getProject(projectId);

        if(mllProject != null){

            mllWorkSpace.deleteProject(mllProject);

        }

        return restResponse;
    }



    /**
     * Fetch aspera configuration for uploading data to MetaboLights Labs
     *
     * @param data
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws JoseException
     * @throws DAOException
     */
    @RequestMapping(value = "asperaConfiguration", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> asperaConfiguration(@RequestBody String data, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<String>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("api_token");

        String projectId = (String) serverRequest.get("project_id");

        boolean createNewProject = Boolean.valueOf(((String) serverRequest.get("new_project_flag")).replace("\"", "\\\""));

        User user = authenticateUser(userToken);

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesnt exist error!");

            return restResponse;

        }

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

        if (mllWorkSpace == null) {

            logger.warn("Error fetching the workspace info associated with the user token: " + userToken);

            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return restResponse;

        }

        String asperaUser = PropertiesUtil.getProperty("asperaUser");

        String asperaSecret = PropertiesUtil.getProperty("asperaSecret");


        logger.info("Checking if a project with the give projectId exists");
        logger.info("Checking if a project is not null or empty");

        if (projectId != null) {

            if (!projectId.trim().equalsIgnoreCase("")) {

                boolean projectExists = mllWorkSpace.isProjectExist(projectId);

                if (projectExists) {

                    MLLProject mllProject = mllWorkSpace.getProject(projectId);

                    mllProject.setAsperaSettings("{ \"asperaURL\" : \""+ mllWorkSpace.getOwner().getApiToken() + File.separator + mllProject.getId() + "\", \"asperaUser\" : \""+ asperaUser + "\",  \"asperaServer\" : \"ah01.ebi.ac.uk\", \"asperaSecret\" :  \""+ asperaSecret + "\" }");

                    restResponse.setContent(mllProject.getAsperaSettings());

                    return restResponse;

                }
            }

        }

        if (createNewProject) {

            MLLProject mllProject = (new MetaboLightsLabsProjectDAO(mllWorkSpace)).getMllProject();

            mllProject.setAsperaSettings("{ \"asperaURL\" : \""+ mllWorkSpace.getOwner().getApiToken() + File.separator + mllProject.getId() + "\", \"asperaUser\" : \""+ asperaUser + "\",  \"asperaServer\" : \"ah01.ebi.ac.uk\", \"asperaSecret\" :  \""+ asperaSecret + "\" }");

            restResponse.setContent(mllProject.getAsperaSettings());

            return restResponse;

        }

        logger.warn("Error finding the project or creating a new project. Check the logs for more details.");

        response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        return restResponse;

    }

    /**
     * Fetch the user workspace details, initialise if user does not exist.
     * @param user
     * @return
     */
    private MLLWorkSpace getWorkspaceInfo(User user) {

        String root = PropertiesUtil.getProperty("userSpace");

        return (new MetaboLightsLabsWorkspaceDAO(user, root)).getMllWorkSpace();

    }


    /**
     * Fetch the workspace settings
     *
     * @param data (User_API_TOKEN)
     * @param response
     * @return RestResponse
     */
    @RequestMapping(value = "settings", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getWorkspaceSettings(@RequestBody String data, HttpServletResponse response) throws IOException {

        RestResponse<String> restResponse = new RestResponse<>();

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String userToken = (String) serverRequest.get("api_token");

        User user = authenticateUser(userToken);

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesn't exist error!");

            return restResponse;

        }

        logger.info("User exists. Fetching workspace information");

        restResponse.setContent(getWorkspaceInfo(user).getAsJSON());

        return restResponse;

    }

    /**
     * Fetch aspera configuration for uploading data to MetaboLights Labs
     *
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

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }


        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesnt exist error!");

            return restResponse;

        }

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

        if (mllWorkSpace == null) {

            logger.warn("Error fetching the workspace info associated with the user token");

            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return restResponse;

        }


        Boolean cloneProject = (Boolean) serverRequest.get("cloneProject");

        ObjectMapper mapper = new ObjectMapper();


        if (cloneProject) {

            String studyId = (String) serverRequest.get("studyId");

            if (studyId != null || studyId.matches(METABOLIGHTS_ID_REG_EXP)) {

                logger.info("Cloning study: " + studyId);

                try {

                    studyDAO = getStudyDAO();

                    Study study =  studyDAO.getStudy(studyId, user.getApiToken());

                    MLLProject mllProject = (new MetaboLightsLabsProjectDAO(mllWorkSpace, title, description, study)).getMllProject();

                    mllProject.log("Cloning study: " + studyId);

                    restResponse.setContent(mapper.writeValueAsString(mllProject));

                    return restResponse;


                } catch (DAOException e) {

                    e.printStackTrace();

                } catch (IsaTabException e) {

                    e.printStackTrace();

                }

            }

        }


        MLLProject mllProject = (new MetaboLightsLabsProjectDAO(mllWorkSpace, title, description, null)).getMllProject();

        restResponse.setContent(mapper.writeValueAsString(mllProject));

        return restResponse;

    }

    private uk.ac.ebi.metabolights.repository.dao.StudyDAO getStudyDAO() throws DAOException {

        if (studyDAO == null) {

            studyDAO = DAOFactory.getInstance().getStudyDAO();

        }
        return studyDAO;
    }


    /**
     * Authenticate user based on the API_TOKEN
     *
     * @param userToken
     * @return user
     */

    private User authenticateUser(String userToken) {

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