/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 14:50
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import org.apache.xmlbeans.XmlException;
import org.isatools.isacreator.configuration.io.ConfigXMLParser;
import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;
import org.isatools.isatab.configurator.schema.IsaTabConfigurationType;
import org.isatools.isatab.configurator.schema.IsatabConfigFileDocument;

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

        //Load the current settings file
        try {
            InputStream inputStream = new FileInputStream(configurationMSfile);
            IsatabConfigFileDocument configurationFile = IsatabConfigFileDocument.Factory.parse(inputStream);

            ConfigXMLParser parser = new ConfigXMLParser("");

            //Add columns defined in the configuration file
            for (IsaTabConfigurationType doc : configurationFile.getIsatabConfigFile().getIsatabConfigurationArray()) {
                parser.processTable(doc);
            }

            if (parser.getTables().size() > 0) {
                return parser.getTables().get(0);
            }
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableReferenceObject;
    }


}
