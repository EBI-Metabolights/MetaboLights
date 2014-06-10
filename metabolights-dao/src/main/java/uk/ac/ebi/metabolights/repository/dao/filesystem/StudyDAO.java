/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 6/6/14 2:25 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.utils.IsaTab2MetaboLightsConverter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 14:41
 */
public class StudyDAO {

    private IsaTabInvestigationDAO isaTabInvestigationDAO;
    private File publicFolder;
    private File privateFolder;
    private final static Logger logger = Logger.getLogger(StudyDAO.class.getName());

    public StudyDAO(String isaTabRootConfigurationFolder, String publicFolder, String privateFolder){
        this.isaTabInvestigationDAO = new IsaTabInvestigationDAO(isaTabRootConfigurationFolder);
        this.publicFolder = new File(publicFolder);
        this.privateFolder = new File(privateFolder);

    }

    public Study getStudy(String metabolightsId, boolean includeMetabolites){

        // Try public studies location
        File studyFolder = getInvestigationFolder(metabolightsId, publicFolder);

        logger.info("Trying to parse study "+metabolightsId);

        boolean isPublic = true;

        // If we got nothing...
        if (studyFolder == null) {

            // Try private studies location
            studyFolder = getInvestigationFolder(metabolightsId, privateFolder);

            isPublic = false;
        }

        // We got something ...
        if (studyFolder != null){

            // Load the IsaTab investigation
            org.isatools.isacreator.model.Investigation isaInvestigation = isaTabInvestigationDAO.getInvestigation(studyFolder.getAbsolutePath());

            // Convert it into a MetaboLights study
            Study study = IsaTab2MetaboLightsConverter.convert(isaInvestigation, studyFolder.getAbsolutePath(), includeMetabolites);

            // Set status...
            study.setPublicStudy(isPublic);

            study.setStudyLocation(studyFolder.getAbsolutePath());

            logger.info("Loaded study "+study.getStudyIdentifier());

            return study;

        } else {
            return null;
        }


    }

    private File getInvestigationFolder(final String metabolightsId, File location){

        logger.info("Study location is "+location+" for study "+metabolightsId);

        File[] files = location.listFiles(new FilenameFilter() {
            public boolean accept(File file, String s) {
                return s.equals(metabolightsId);
            }
        });

        if (files != null && files.length == 1 ){
            return  files[0];
        } else {
            return null;
        }


    }
	public boolean isStudyPublic(String metaboLightsId){
		return (getInvestigationFolder(metaboLightsId,publicFolder) != null);
	}

}
