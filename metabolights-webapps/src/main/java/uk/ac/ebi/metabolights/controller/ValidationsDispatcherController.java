/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 20160129
 * Modified by:   jrmacias
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
//import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
//import uk.ac.ebi.metabolights.webservice.client.Metabolights---WsClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Controller for dispatching study validations .
 *
 * @author jrmacias
 * @date 20160129
 */
@Controller
public class ValidationsDispatcherController  extends AbstractController{


    private static final String URL_4_VALIDATIONS = "validations";

    /**
     * Send a mail to the submitter with a Validations Status Report
     * with all the current validations results,
     * ordered by status, so the submitter can find what is failing to pass
     * and accordingly update the study.
     *
     * @param studyId the ID of the study
     * @author: jrmacias
     * @date: 20160216
     */
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_VALIDATIONS + "/statusReportByMail")
    public ModelAndView sendValitationReportByEmail(@PathVariable("studyId") String studyId) {

        logger.info("Sending the Validations Status report for the study {} to the submitter by email.", studyId);

        // Using the WebService-client to do the job
//        MetabolightsWs---Client wsClient = EntryController.getMetabolights---WsClient();
//        RestResponse<String> rslt = wsClient.sendValitationReportByEmail(studyId);

        // parse WS response for user feedback
        List<String> msg = new LinkedList<>();
//        String[] str = rslt.getMessage().split("\\|");
//        for (String line:str){
//            msg.add(line);
//        }

        return printMessage("Sending the Validations Status report for Study...", msg, null, studyId);
    }
}