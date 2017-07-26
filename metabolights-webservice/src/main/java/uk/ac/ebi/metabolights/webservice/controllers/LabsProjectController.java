package uk.ac.ebi.metabolights.webservice.controllers;

import org.jose4j.json.internal.json_simple.JSONArray;
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
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetaboLightsLabsProjectDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.MLLProject;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by venkata on 22/08/2016.
 */
@Controller
@RequestMapping("labs-project")
public class LabsProjectController {

    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    /**
     * Get the list of files and folders in the given project id
     * @param data
     * @param response
     * @return restResponse
     */
    @RequestMapping(value = "content", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getProjectDetails(@RequestBody String data, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("id");

        String projectLocation = PropertiesUtil.getProperty("userSpace") + File.separator + user.getApiToken() + File.separator + projectId;

        // JSONArray contentJSONArray = new JSONArray(Arrays.asList(FileUtil.getFtpFolderList(projectLocation)));

        List<String> filesList = new ArrayList<>();

        for (String file: FileUtil.listFileTree(new File(projectLocation))) {

            filesList.add(file.replace(projectLocation , ""));

        }

        JSONArray contentJSONArray = new JSONArray(filesList);

        restResponse.setContent(contentJSONArray.toJSONString());

        return restResponse;

    }

    /**
     * Get the project details
     * @param data
     * @param response
     * @return
     */
    @RequestMapping(value = "details", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getProjectdetails(@RequestBody String data, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("id");

        String root = PropertiesUtil.getProperty("userSpace");

        MetaboLightsLabsProjectDAO metaboLightsLabsProjectDAO = new MetaboLightsLabsProjectDAO(user, projectId, root );

        MLLProject mllProject = metaboLightsLabsProjectDAO.getMllProject();

        JSONParser parser = new JSONParser();

        try {

            User mlUser = null;

            JSONObject aseraSettings = (JSONObject) parser.parse(mllProject.getAsperaSettings());

            if (mllProject.getOwner().getApiToken() == null){

                String email = mllProject.getOwner().getEmail();

                UserServiceImpl usi = new UserServiceImpl();

                mlUser = usi.getUserById(email);

                aseraSettings.put("asperaURL", mlUser.getApiToken() + File.separator + mllProject.getId());

            } else {

                aseraSettings.put("asperaURL", mllProject.getOwner().getApiToken() + File.separator + mllProject.getId());

            }

            aseraSettings.put("asperaUser", PropertiesUtil.getProperty("asperaUser"));

            aseraSettings.put("asperaSecret", PropertiesUtil.getProperty("asperaSecret"));

            mllProject.setAsperaSettings(aseraSettings.toJSONString());

        } catch (ParseException e) {

            e.printStackTrace();

        } catch (DAOException e) {
            e.printStackTrace();
        }

        restResponse.setContent(mllProject.getAsJSON());

        return restResponse;

    }

    /**
     * Edit the project configuration
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "editDetails", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> editProjectdetails(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("id");

        String title = (String) serverRequest.get("title");

        String description = (String) serverRequest.get("description");

        String root = PropertiesUtil.getProperty("userSpace");


        MLLWorkSpace mllWorkSpace = new MLLWorkSpace(user, root);

        MLLProject mllProject = mllWorkSpace.getProject(projectId);

        mllProject.setTitle(title);

        mllProject.setDescription(description);

        mllProject.save();

        mllWorkSpace.appendOrUpdateProject(mllProject);

        restResponse.setContent(mllProject.getAsJSON());

        return restResponse;

    }

    /**
     * Get the project logs
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "log", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getProjectLog(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("id");

        String root = PropertiesUtil.getProperty("userSpace");

        MetaboLightsLabsProjectDAO metaboLightsLabsProjectDAO = new MetaboLightsLabsProjectDAO(user, projectId, root);

        MLLProject mllProject = metaboLightsLabsProjectDAO.getMllProject();

        restResponse.setContent(mllProject.getLogs());

        return restResponse;

    }


    /**
     * Authenticate user and delete the file
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "deleteFiles", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> deleteProject(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("id");

        List<String> files = (List<String>)serverRequest.get("files");

        String root = PropertiesUtil.getProperty("userSpace");

        MetaboLightsLabsProjectDAO metaboLightsLabsProjectDAO = new MetaboLightsLabsProjectDAO(user, projectId, root );

        MLLProject mllProject = metaboLightsLabsProjectDAO.getMllProject();

        mllProject.delete(files);

        restResponse.setContent(mllProject.getAsJSON());

        return restResponse;

    }

    /**
     * Authenticate user and convert the mzML2isa
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "convertMZMLToISA", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> convertMZMLToISA(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {

        RestResponse<String> restResponse = new RestResponse<String>();

        User user = SecurityUtil.validateJWTToken(data);

        if(user == null || user.getRole().equals(AppRole.ANONYMOUS)) {

            restResponse.setContent("invalid");

            response.setStatus(403);

            return restResponse;

        }

        JSONObject serverRequest = SecurityUtil.parseRequest(data);

        String projectId = (String) serverRequest.get("id");

        String root = PropertiesUtil.getProperty("userSpace");

        MetaboLightsLabsProjectDAO metaboLightsLabsProjectDAO = new MetaboLightsLabsProjectDAO(user, projectId, root );

        MLLProject mllProject = metaboLightsLabsProjectDAO.getMllProject();

        JSONObject settings= SecurityUtil.parseRequest(mllProject.getSettings());

        if (settings == null || settings.get("jobs") == null){

            String[] commands = {"ssh", "ebi-login-001", "/nfs/www-prod/web_hx2/cm/metabolights/scripts/convert_mzml2isa.sh", "--token", user.getApiToken(), "--project " , mllProject.getId(), "--env", "dev"};

            return executeCommand(commands, response, restResponse, mllProject);

        }else{

            String[] commands = {"ssh", "ebi-login-001", "/nfs/www-prod/web_hx2/cm/metabolights/scripts/convert_mzml2isa.sh", "--token", user.getApiToken(), "--project " , mllProject.getId(), "--env", "dev", "--job" , (String) settings.get("jobs") };

            return executeCommand(commands, response, restResponse, mllProject);

        }

    }

    private RestResponse<String> executeCommand(String[] commands, HttpServletResponse response, RestResponse<String> restResponse, MLLProject project){

        String s;
        Process p;

        Boolean error = false;

        StringBuilder sb = null;

        try {
            p = Runtime.getRuntime().exec(commands);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                logger.info("line: " + s);
                sb.append(s);
            }
            p.waitFor();

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            while ((s = stdError.readLine()) != null) {
                error = true;
                sb.append(s);
                logger.error("line: " + s);
            }

            p.destroy();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

//        if (error){
//
//            restResponse.setMessage(sb.toString());
//
//            response.setStatus(500);
//
//        }else{

            // sb.toString()

           // JSONObject output = SecurityUtil.parseRequest('');

            project.saveJobDetails("jobs", SecurityUtil.parseRequest("{'message': 'Job submitted successfully', 'code': 'PEND', 'jobID': '5134734'}"));

//        }

        return restResponse;

    }

}