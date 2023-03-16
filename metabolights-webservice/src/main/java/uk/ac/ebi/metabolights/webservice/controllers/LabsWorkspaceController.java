package uk.ac.ebi.metabolights.webservice.controllers;

import java.io.File;
import java.util.List;

import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jose4j.lang.JoseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.model.*;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import org.jose4j.json.internal.json_simple.JSONObject;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import uk.ac.ebi.metabolights.utils.json.FileUtils;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;
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

            project.setAsperaSettings("{ \"asperaURL\" : \""+ user.getApiToken() + File.separator + project.getId() + "\", \"asperaUser\" : \""+ asperaUser + "\",  \"asperaServer\" : \"hx-fasp-1.ebi.ac.uk\", \"asperaSecret\" :  \""+ asperaSecret + "\" }");

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

                    mllProject.setAsperaSettings("{ \"asperaURL\" : \""+ user.getApiToken() + File.separator + mllProject.getId() + "\", \"asperaUser\" : \""+ asperaUser + "\",  \"asperaServer\" : \"hx-fasp-1.ebi.ac.uk\", \"asperaSecret\" :  \""+ asperaSecret + "\" }");

                    restResponse.setContent(mllProject.getAsperaSettings());

                    return restResponse;

                }
            }

        }

        if (createNewProject) {

            MLLProject mllProject = (new MetaboLightsLabsProjectDAO(mllWorkSpace)).getMllProject();

            mllProject.setAsperaSettings("{ \"asperaURL\" : \""+ user.getApiToken() + File.separator + mllProject.getId() + "\", \"asperaUser\" : \""+ asperaUser + "\",  \"asperaServer\" : \"hx-fasp-1.ebi.ac.uk\", \"asperaSecret\" :  \""+ asperaSecret + "\" }");

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

        String title = (String) serverRequest.get("title");

        String url = (String) serverRequest.get("url");

        String apikey = (String) serverRequest.get("apikey");

        String description = (String) serverRequest.get("description");

        String delete = (String) serverRequest.get("delete");

        User user = SecurityUtil.validateJWTToken(data);

        String property = (String) serverRequest.get("property");

        String propertyValue = (String) serverRequest.get("propertyValue");

        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            logger.info("User with the token doesnt exist. Aborting the request");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User doesn't exist error!");

            return restResponse;

        }

        logger.info("User exists. Fetching workspace information");

        MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

        if(property.equalsIgnoreCase("galaxy")){

            JSONObject jsonobject = SecurityUtil.parseRequest(mllWorkSpace.getSettings());

            JSONArray galaxyInstances = null;
            try {
                if(jsonobject.containsKey(property)) {
                    galaxyInstances = (JSONArray) new JSONParser().parse(jsonobject.get(property).toString());
                }else{
                    galaxyInstances = null;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            JSONObject newGalaxyInstance = new JSONObject();

            newGalaxyInstance.put("name", title);
            newGalaxyInstance.put("url", url);
            newGalaxyInstance.put("apikey", apikey);
            newGalaxyInstance.put("description", description);

            if (galaxyInstances == null){

                galaxyInstances = new JSONArray();
                galaxyInstances.add(newGalaxyInstance);

            }else{

                boolean instanceExist = false;
                int instancePosition = 0;
                int selectedInstancePosition = 0;
                for(Object instance: galaxyInstances){

                    JSONObject jsonObject = (JSONObject) instance;

                    if (jsonObject.get("url").toString().equalsIgnoreCase(url)){
                        jsonObject.put("name", title);
                        jsonObject.put("url", url);
                        jsonObject.put("apikey", apikey);
                        jsonObject.put("description", description);
                        instanceExist = true;
                        selectedInstancePosition = instancePosition;
                    }

                    instancePosition = instancePosition + 1;
                }

                if(!instanceExist){

                    galaxyInstances.add(newGalaxyInstance);

                    restResponse.setMessage("Instance added successfully!");

                    response.setStatus(200);

                }else{

                    if(delete != null && delete.equalsIgnoreCase("deleteGalaxyInstance")){

                        galaxyInstances.remove(selectedInstancePosition);

                    }
                }

            }
            mllWorkSpace.setProperty(property, galaxyInstances.toString());

        }else if(property != null && !property.equalsIgnoreCase("galaxy")){

            mllWorkSpace.setProperty(property, propertyValue);

            restResponse.setMessage("Task successful!");

            response.setStatus(200);

        }

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

        Boolean cloneProject = false;

        int cloneType = (int) Integer.parseInt(serverRequest.get("cloneType").toString());

        String studyId = null;

        Boolean byPass = false;

        if(cloneType != 0){
            byPass = true;
            cloneProject = true;
            if(cloneType == 1){
                studyId = (String) serverRequest.get("studyId");
            }else if(cloneType == 2){
                studyId = "MTBLS121";
            }else if(cloneType == 3){
                studyId = "MTBLS130";
            }else if(cloneType == 4){
                studyId = "MTBLS122";
            }else if(cloneType == 5){
                studyId = "MTBLS135";
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        if (cloneProject) {
            if (studyId != null || studyId.matches(METABOLIGHTS_ID_REG_EXP)) {

                logger.info("Cloning study: " + studyId);

                try {

                    studyDAO = getStudyDAO();

                    Study study = null;

                    if(!byPass){
                        study =  studyDAO.getStudy(studyId, user.getApiToken());
                    }else{

                        String configUserToken = PropertiesUtil.getProperty("teamToken");

                        study =  studyDAO.getStudy(studyId, configUserToken);
                    }

                    if(study != null){

                        MLLProject mllProject = (new MetaboLightsLabsProjectDAO(mllWorkSpace, title, description, study)).getMllProject();

                        mllProject.log("Cloning study: " + studyId);

                        restResponse.setContent(mapper.writeValueAsString(mllProject));

                        return restResponse;

                    }else{

                        logger.warn("User might be trying to clone others private study");

                        restResponse.setMessage("Cloning study failed: User might not have the right permission");

                        response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

                        return restResponse;

                    }

                } catch (DAOException e) {

                    logger.warn("User might be trying to clone others private study");

                    restResponse.setMessage("Cloning study failed: User might not have the right permission");

                    response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

                    return restResponse;

                } catch (IsaTabException e) {

                    logger.warn("ISATab Exception");

                    restResponse.setMessage("ISA Tab exception. Please check the study is redering properly");

                    response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

                    return restResponse;

                }

            }

        }


        MLLProject mllProject = (new MetaboLightsLabsProjectDAO(mllWorkSpace, title, description, null)).getMllProject();

        restResponse.setContent(mapper.writeValueAsString(mllProject));

        return restResponse;

    }

//    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public HttpServletResponse downloadFile(@RequestParam("jwt") String jwt, @RequestParam("email") String email, @RequestParam("path") String path, HttpServletResponse response) throws IOException {
//
//        User user = SecurityUtil.getUser(jwt, email);
//
//        if (user != null) {
//
//            if (!user.getRole().equals(AppRole.ANONYMOUS)){
//                MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);
//
//                if (mllWorkSpace != null) {
//
//                    String projectPath = mllWorkSpace.getWorkspaceLocation();
//
//                    String filePath = projectPath + "/" + path;
//
//                    if (FileUtils.checkFileExists(filePath)){
//
//                        File file = new File(filePath);
//
//                        if(file.isFile()){
//
//                            FileUtil.streamFile(file, response);
//
//                        }
//
//                    }
//
//                }
//            }
//        }
//
//        response.setStatus(403);
//
//        return response;
//
//    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void downloadFile(@RequestParam("apikey") String apikey, @RequestParam("path") String path, HttpServletResponse response) throws IOException {

        User user = authenticateUser(apikey);

        if (user != null) {

            if (!user.getRole().equals(AppRole.ANONYMOUS)){
                MLLWorkSpace mllWorkSpace = getWorkspaceInfo(user);

                if (mllWorkSpace != null) {

                    String projectPath = mllWorkSpace.getWorkspaceLocation();

                    String filePath = projectPath + "/" + path;

                    if (FileUtils.checkFileExists(filePath)){

                        File file = new File(filePath);

                        if(file.isFile()){

                            FileUtil.streamFile(file, response);

                        }

                    }

                }
            }
        }

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