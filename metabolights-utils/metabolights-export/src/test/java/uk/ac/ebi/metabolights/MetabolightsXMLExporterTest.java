/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-22
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

package uk.ac.ebi.metabolights;

import junit.framework.TestCase;
import uk.ac.ebi.metabolights.utils.exporter.MetabolightsXMLExporter;

public class MetabolightsXMLExporterTest  extends TestCase{

    MetabolightsXMLExporter xmlExporter = new MetabolightsXMLExporter();
    String ISATabRootDirectory = MetabolightsXMLExporterTest.class.getClassLoader().getResource(".").getPath();
    String fileName = ISATabRootDirectory;
    String wsURL = "https://www.ebi.ac.uk/metabolights/webservice/";
    Boolean fileExported = false, includeCompounds = true, detailedTags = false;

    public void testXMLExporter(){
        try {

            if (includeCompounds)
                fileName = fileName + "eb-eye_metabolights.xml";
            else
                fileName = fileName + "eb-eye_metabolights_studies.xml";

            if (detailedTags)
                fileName = ISATabRootDirectory + "thomsonreuters_metabolights_studies.xml";

            fileExported = xmlExporter.writeFile(fileName, includeCompounds, detailedTags, wsURL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue( fileExported );
    }
}
