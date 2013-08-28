package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.isatools.isacreator.io.importisa.ISAtabFilesImporter;
import org.isatools.isacreator.model.Investigation;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 12:26
 */
public class IsaTabInvestigationDAO {

    private String isaTabConfigurationLocation;
    private ISAtabFilesImporter isatabFilesImporter;
    private Investigation investigation;


    public IsaTabInvestigationDAO(String isaTabConfigurationLocation){
        this.isaTabConfigurationLocation = isaTabConfigurationLocation;
    }

    /**
     * Import an ISATAB file set into corresponding ISA model objects
     *
     * @param parentDir - Directory containing the ISATAB files, eg. a study
     * @return boolean if successful or not!
     */
    private boolean validateISAtabFiles(String parentDir) {

        try {
            return getIsatabFilesImporter().importFile(parentDir);
        } catch (Exception e){
            System.out.print("Error: " + e.getMessage());

        }

        return false;
    }

    /**
     * Get the whole investigation from the study
     * @param parentDir
     * @return ISAcrator Study object
     */
    public Investigation getInvestigation(String parentDir) {
        if (investigation == null){
            if (validateISAtabFiles( parentDir))
                investigation = getInvestigation();
        }
        return investigation;
    }

    /**
     * Retrieves the investigation object from ISAcreator, this also contains studies etc etc
     *
     * @return investigation
     */
    private Investigation getInvestigation() {
        if (investigation == null)
            investigation = isatabFilesImporter.getInvestigation();
        return investigation;
    }

    private ISAtabFilesImporter getIsatabFilesImporter() {
        if (isatabFilesImporter == null)
            isatabFilesImporter = new ISAtabFilesImporter(isaTabConfigurationLocation);
        return isatabFilesImporter;
    }
}
