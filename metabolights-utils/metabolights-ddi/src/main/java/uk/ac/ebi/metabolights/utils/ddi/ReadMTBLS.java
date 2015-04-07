/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Mar-30
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

package uk.ac.ebi.metabolights.utils.ddi;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import java.util.ArrayList;
import java.util.List;

public class ReadMTBLS {

    String CURATOR_TOKEN = System.getenv("CURATOR_TOKEN");

    private String wsClientURL = "http://wwwdev.ebi.ac.uk/metabolights/webservice/";
    private String searchURL = wsClientURL+ "search";
    private MetabolightsWsClient wsClient;

    public MetabolightsWsClient getWsClient() {
        if (wsClient== null)
            wsClient = new MetabolightsWsClient(wsClientURL);
        return wsClient;
    }

    public static void main(String[] args){

    }

    public ReadMTBLS(){}


    public List<Study> getAllStudies(){

        List studyList = new ArrayList();

        RestResponse<Study> response = getWsClient().getStudy("MTBLS1");
        Study study = response.getContent();
        if (study != null)
            studyList.add(study);


        return studyList;

    }


}
