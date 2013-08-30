package uk.ac.ebi.metabolights.repository.dao.filesystem;

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

    public StudyDAO(String isaTabConfigurationFolder, String publicFolder, String privateFolder){
        this.isaTabInvestigationDAO = new IsaTabInvestigationDAO(isaTabConfigurationFolder);
        this.publicFolder = new File(publicFolder);
        this.privateFolder = new File(privateFolder);

    }

    public Study getStudy(String metabolightsId){


        // Try public studies location
        File studyFolder = getInvestigationFolder(metabolightsId, publicFolder);

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
            Study study = IsaTab2MetaboLightsConverter.convert(isaInvestigation);

            // Set status...
            study.setPublic(isPublic);

            return study;

        } else {
            return null;
        }


    }

    private File getInvestigationFolder(final String metabolightsId, File location){

        File[] files = location.listFiles(new FilenameFilter() {
            @Override
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
}
