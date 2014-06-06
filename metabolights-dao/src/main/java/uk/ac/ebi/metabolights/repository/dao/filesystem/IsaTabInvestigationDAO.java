/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 6/6/14 12:28 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.isatools.isacreator.io.importisa.ISAtabFilesImporter;
import org.isatools.isacreator.model.Investigation;
import uk.ac.ebi.metabolights.utils.isatab.IsaTabUtils;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 12:26
 */
public class IsaTabInvestigationDAO {

    private String isaTabRootConfigurationLocation;
    private ISAtabFilesImporter isatabFilesImporter;
    private String lastConfigurationLoaded;

    private static Map<String, String> studyConfigFolderMap = new HashMap<String, String>();

    public IsaTabInvestigationDAO(String isaTabRootConfigurationLocation){
        this.isaTabRootConfigurationLocation = isaTabRootConfigurationLocation;
    }

    /**
     * Import an ISATAB file set into corresponding ISA model objects
     *
     * @param isaTabStudyFolder - Directory containing the ISATAB files, eg. a study
     * @return boolean if successful or not!
     */
    private boolean validateISAtabFiles(String isaTabStudyFolder) {

        try {
            return getIsatabFilesImporter(isaTabStudyFolder).importFile(isaTabStudyFolder);
        } catch (Exception e){
            System.out.print("Error: " + e.getMessage());

        }

        return false;
    }

    /**
     * Get the whole investigation from the study
     * @param isaTabStudyFolder
     * @return ISAcrator Study object
     */
    public Investigation getInvestigation(String isaTabStudyFolder) {

        Investigation investigation = null;

        if (validateISAtabFiles(isaTabStudyFolder))
            investigation = isatabFilesImporter.getInvestigation();

        return investigation;
    }

    private ISAtabFilesImporter getIsatabFilesImporter(String isaTabStudyFolder) throws IOException, ConfigurationException {

        // We need to get the configuration folder for the study
        String configFolder = getConfigurationFolderForStudy(isaTabStudyFolder);

        // If the config required
        if (!configFolder.equals(lastConfigurationLoaded)){
            isatabFilesImporter = null;
        }

        if (isatabFilesImporter == null){
            lastConfigurationLoaded = configFolder;
            isatabFilesImporter = new ISAtabFilesImporter(lastConfigurationLoaded);
        }


        return isatabFilesImporter;
    }

    private String getConfigurationFolderForStudy(String isaTabStudyFolder) throws IOException, ConfigurationException {

        // Try the map...
        String configFolderName = studyConfigFolderMap.get(isaTabStudyFolder);

        // If not found...
        if (configFolderName == null){

            File configFolder = IsaTabUtils.getConfigurationFolderFromStudy(isaTabStudyFolder, isaTabRootConfigurationLocation);

            configFolderName = configFolder.getAbsolutePath();

            // Add the pair study config to the map
            studyConfigFolderMap.put (isaTabStudyFolder, configFolderName);

        }

        return configFolderName;

    }
}
