/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/10/14 11:57 AM
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.utils.ClobJsonUtils;
import uk.ac.ebi.metabolights.repository.utils.FileAuditUtil;
import uk.ac.ebi.metabolights.repository.utils.IsaTab2MetaboLightsConverter;
import uk.ac.ebi.metabolights.repository.utils.validation.StudyValidationUtilities;

import java.io.File;
import java.io.FilenameFilter;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 14:41
 */
public class StudyDAO {

    private IsaTabInvestigationDAO isaTabInvestigationDAO;
    private static File studiesFolder;
    private final static Logger logger = LoggerFactory.getLogger(StudyDAO.class.getName());

    public StudyDAO(String isaTabRootConfigurationFolder, String studiesFolder) {
        this.isaTabInvestigationDAO = new IsaTabInvestigationDAO(isaTabRootConfigurationFolder);
        this.studiesFolder = new File(studiesFolder);

    }

    public Study getStudy(String accession, boolean includeMetabolites) throws DAOException, IsaTabException {

        Study newStudy = new Study();
        newStudy.setStudyIdentifier(accession);
        return fillStudy(includeMetabolites, newStudy);

    }

    public static File getStudyFolder(final String metabolightsId, File location) {

        logger.debug("Trying to locate {} folder: {}", metabolightsId, location);

        File[] files = location.listFiles(new FilenameFilter() {
            public boolean accept(File file, String s) {
                return s.equals(metabolightsId);
            }
        });

        if (files != null && files.length == 1) {
            return files[0];
        } else {

            logger.debug("{} folder not found at {}", metabolightsId, location);
            return null;
        }


    }

    public static File getStudyFolder(String studyIdentifier) {
        // Try from the expected folder
        File studyFolder = getStudyFolder(studyIdentifier, studiesFolder);

        // Warn about this:
        if (studyFolder == null) {
            logger.warn("Folder for the study " + studyIdentifier + "not found here " + studiesFolder.getAbsolutePath());
        }
        return studyFolder;
    }

    public static File getRootFolder() {

        if (studiesFolder == null) {
            logger.warn("Careful!, it seems you are using the StudyDAO without having it initialized. Private folder or public folder is/are null");
        }

        return studiesFolder;

    }

    public static File getDestinationFolder(String studyIdentifier) {

        // If no status is passes we will use private as a safety measure
        File destination = getRootFolder();

        destination = new File(destination, studyIdentifier);

        if (!destination.exists()) {
            destination.mkdir();
        }

        return destination;

    }

    public static File getStudiesFolder() {
        return studiesFolder;
    }

    public static void setStudiesFolder(File studiesFolder) {
        StudyDAO.studiesFolder = studiesFolder;
    }


    public Study fillStudy(boolean includeMetabolites, Study studyToFill) throws DAOException {

        logger.info("Trying to parse study " + studyToFill.getStudyIdentifier());

        File studyFolder = getStudyFolder(studyToFill.getStudyIdentifier());

        // We got something ...
        if (studyFolder != null) {


            try {

                fillStudyFromFolder(includeMetabolites, studyToFill, studyFolder);

            } catch (Exception e) {

                logger.warn("Folder for {} found, but metadata can't be loaded. Load process will continue but without metadata. This should be fixed by submitting new metadata files.", studyToFill.getStudyIdentifier(), e);

            }

            // Return what we have (could be only DB data in case of metadata load failure.)
            return studyToFill;

        } else {
            throw new DAOException("Study folder for " + studyToFill.getStudyIdentifier() + " not found.");
        }

    }

    public Study fillStudyFromFolder(boolean includeMetabolites, Study studyToFill, File studyFolder) throws IsaTabException {

        // Set the location
        studyToFill.setStudyLocation(studyFolder.getAbsolutePath());

        // Load the IsaTab investigation
        org.isatools.isacreator.model.Investigation isaInvestigation = null;
        try {

            isaInvestigation = isaTabInvestigationDAO.getInvestigation(studyFolder.getAbsolutePath());

            // Convert it into a MetaboLights study
            studyToFill = IsaTab2MetaboLightsConverter.convert(isaInvestigation, studyFolder.getAbsolutePath(), includeMetabolites, studyToFill);


        } catch (IsaTabException e) {

            StudyValidationUtilities.AddValidationFromException(studyToFill, "Study metadata load","We could NOT load the isatab files: " + e.getMessage() + ", " +
                    e.getClass().getName());

        }

        // Add Backups
        studyToFill.setBackups(FileAuditUtil.getBackupsCollection(studyFolder));

        StudyValidationUtilities.validate(studyToFill);
        logger.warn(ClobJsonUtils.parseToJSONString(studyToFill.getValidations()));


        logger.info("Study loaded from folder: {}", studyFolder.getAbsolutePath());

        return studyToFill;
    }


}
