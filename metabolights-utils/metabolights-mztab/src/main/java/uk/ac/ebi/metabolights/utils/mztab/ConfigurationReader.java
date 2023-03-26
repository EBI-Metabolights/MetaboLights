/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/16/13 11:03 AM
 * Modified by:   conesa
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

package uk.ac.ebi.metabolights.utils.mztab;

import org.apache.xmlbeans.XmlException;
import org.isatools.isacreator.configuration.io.ConfigXMLParser;
import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationReader {

    private static String configPath = "." + File.separator + "metabolomics_configuration" + File.separator;
    private static String configFile = configPath + "configuration_";
    private static String configurationMSfile = ConfigurationReader.class.getClassLoader().getResource(configFile + "ms.xml").getFile();
    private static String configurationNMRfile = ConfigurationReader.class.getClassLoader().getResource(configFile +"nmr.xml").toString();
    public String NMR = "NMR", MS = "MS";


    public TableReferenceObject getMSConfig() {
        return getConfiguration(configurationMSfile);
    }

    public TableReferenceObject getNMRConfig() {
        return getConfiguration(configurationNMRfile);
    }

    private TableReferenceObject getConfiguration(String fileName){
        TableReferenceObject tableReferenceObject = null;

        // //Load the current settings file
        // try {
        //     InputStream inputStream = new FileInputStream(configurationMSfile);
        //     IsatabConfigFileDocument configurationFile = IsatabConfigFileDocument.Factory.parse(inputStream);

        //     ConfigXMLParser parser = new ConfigXMLParser("");

        //     //Add columns defined in the configuration file
        //     for (IsaTabConfigurationType doc : configurationFile.getIsatabConfigFile().getIsatabConfigurationArray()) {
        //         parser.processTable(doc);
        //     }

        //     if (parser.getTables().size() > 0) {
        //         return parser.getTables().get(0);
        //     }
        // } catch (XmlException e) {
        //     e.printStackTrace();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        return tableReferenceObject;
    }


}
