/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Feb-10
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.controllers;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: conesa
 * Date: 10/02/15
 * Time: 17:11
 */
@Controller
public class BasicController {

    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @RequestMapping(value = "/studyids", method = RequestMethod.POST)
    @ResponseBody
    public static RestResponse getStudyIDs(@RequestBody String data, HttpServletResponse response) {

        // parse login data fields
        JSONParser parser = new JSONParser();
        JSONObject loginData = null;

        try {
            loginData = (JSONObject) parser.parse(data);
        } catch (ParseException e) {

        }

        String token = (String) loginData.get("token");

//        User user = getUser();
        List studyIds = new ArrayList<>();
        RestResponse restResponse = new RestResponse();

        // check if the user exists and valid
        UserServiceImpl usi = null;

        try {
            usi = new UserServiceImpl();
        } catch (DAOException e) {
            response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return restResponse;
        }

        User user = usi.lookupByToken(token);
        Set<LiteStudy> studies = user.getStudies();
        for (LiteStudy study : studies) {
            studyIds.add(study.getStudyIdentifier());
        }
        restResponse.setContent(studyIds);
        return restResponse;
    }

    public static User getUser() {

        User user;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getPrincipal().equals(new String("anonymousUser"))) {

            user = ((SpringUser) auth.getPrincipal()).getMetaboLightsUser();

        } else {

            user = new User();
            user.setUserName(auth.getPrincipal().toString());
        }

        return user;
    }

    @ExceptionHandler
    @ResponseBody
    public RestResponse handleException(Exception e) {

        RestResponse response = new RestResponse(e);

        response.setMessage(explainErrorMessage(e));

        // The error in some cases can't be serialized and therefore an exception is triggered: avoid it
        Exception error = new Exception(e.getMessage());

        response.setErr(error);

        return response;

    }

    private String explainErrorMessage(Exception e) {

        String explanation;

        explanation = e.getMessage();

        return explanation;
    }

}
