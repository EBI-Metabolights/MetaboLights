/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/22/14 4:47 PM
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

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.isatools.isacreator.io.importisa.ISAtabFilesImporter;
import org.isatools.isacreator.model.Investigation;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
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
    private boolean loadIsaTabFiles(String isaTabStudyFolder) throws IsaTabException {

        Boolean imported = null;

        try {


            imported = getIsatabFilesImporter(isaTabStudyFolder).importFile(isaTabStudyFolder);


        } catch (Exception e){

        } finally {

            if (!imported) {
                throw new IsaTabException("Can't load isatab files at " + isaTabStudyFolder, isatabFilesImporter.getMessages());
            }

            return imported;
        }
    }

    /**
     * Get the whole investigation from the study
     * @param isaTabStudyFolder
     * @return ISAcrator Study object
     */
    public Investigation getInvestigation(String isaTabStudyFolder) throws IsaTabException {

        Investigation investigation = null;

        if (loadIsaTabFiles(isaTabStudyFolder))
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
