package uk.ac.ebi.metabolights.utils.sampletab;

import org.isatools.isacreator.io.importisa.ISAtabFilesImporter;
import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 24/04/2013
 * Time: 10:08
 */
public class ISATabReader {

    private ISAtabFilesImporter isatabFilesImporter;
    private Investigation investigation;

    public ISAtabFilesImporter getIsatabFilesImporter(String configDir) {
        if (isatabFilesImporter == null)
            isatabFilesImporter = new ISAtabFilesImporter(configDir);
        return isatabFilesImporter;
    }

    /**
     * Import an ISATAB file set into corresponding ISA model objects
     *
     * @param configDir - Directory containing the current MetaboLights configuration files
     * @param parentDir - Directory containing the ISATAB files, eg. a study
     * @return boolean if successful or not!
     */
    public boolean validateISAtabFiles(String configDir, String parentDir) {
        return getIsatabFilesImporter(configDir).importFile(parentDir);
    }

    /**
     * Retrieves the investigation object from ISAcreator, this also contains studies etc etc
     *
     * @return investigation
     */
    public Investigation getInvestigation() {
        if (investigation == null)
            investigation = isatabFilesImporter.getInvestigation();
        return investigation;
    }

    /**
     * Get the whole investion from the study
     * @param configDir
     * @param parentDir
     * @return ISAcrator Investigation object
     */
    public Investigation getInvestigation(String configDir, String parentDir) {
        if (investigation == null){
            if (validateISAtabFiles(configDir, parentDir))
                investigation = getInvestigation();
        }
        return investigation;
    }


    /**
     * Retrives all studies for an investigation
     * @return Map of Study accessions and Study objects
     */
    public Map<String, Study> getStudyMap(){
       return getInvestigation().getStudies();
    }


    /**
     * Retrieves the contacts we have recorded for this study
     * @param studyAcc (MTBLS id)
     * @return List of Contacts for this study
     */
    public List<Contact> getContcatsForStudy(String studyAcc){

        List<Contact> contactsList = null;


        for (Map.Entry<String, Study> entry : getInvestigation().getStudies().entrySet())
        {
            if (entry.getKey().equals(studyAcc)){
                contactsList = entry.getValue().getContacts();
                break;
            }
        }

       return contactsList;

    }

    /**
     * Get a study based on name
     * @param studyAcc
     * @return Study from ISAcreator
     */
    public Study getStudyOnName(String studyAcc){
        Study study = null;

        for (Map.Entry<String, Study> entry : getInvestigation().getStudies().entrySet())
        {
            if (entry.getKey().equals(studyAcc)){
                study = entry.getValue();
                break;
            }
        }

        return study;

    }

    public Study getStudyFromInvestigation(){
        Study study = null;

        for (Map.Entry<String, Study> entry : getInvestigation().getStudies().entrySet())
        {
            study = entry.getValue();
            if (study != null)
                break;      //There can only be one!
        }

        return study;
    }

}
