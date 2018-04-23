/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Dec-16
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author venkat
 * @date 03/11/15
 */
@Controller
public class RheaAndReactomeProxy extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(RheaAndReactomeProxy.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getContent(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getContent(request, response);
    }

    @RequestMapping({"/RheaAndReactomeProxy","/SwaggerProxy"})
    public static void getContent(HttpServletRequest request, HttpServletResponse response) {
        try {
            String reqUrl = request.getParameter("url");
            if (reqUrl == null){
                logger.info("Proxy url empty or not allowed: " + reqUrl);
                response.setStatus(404);
            }

            if (!reqUrl.contains("rhea-db.org") && !reqUrl.contains("reactome.org") && !reqUrl.contains("mtbls/ws/")) return;


            URL url = new URL(reqUrl);

            if (reqUrl.contains("mtbls/ws/"))
                url = new URL("http://localhost:5000/" + reqUrl);   //TODO, add JNDI parameter "swaggerhost"

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);

            if (reqUrl.contains("mtbls/ws/")){
                String user_token = request.getHeader("user_token");
                logger.info("Proxy request to Swagger server " + url + ", with user_token " + user_token);
                con.setRequestProperty("user_token",user_token);
                con.setRequestProperty("api_key",user_token);
            }

            con.setRequestMethod(request.getMethod());
            int clength = request.getContentLength();
            if (clength > 0) {
                con.setDoInput(true);
                byte[] idata = new byte[clength];
                request.getInputStream().read(idata, 0, clength);
                con.getOutputStream().write(idata, 0, clength);
            }
            response.setContentType(con.getContentType());
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            PrintWriter out = response.getWriter();
            while ((line = rd.readLine()) != null) {
                out.println(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
