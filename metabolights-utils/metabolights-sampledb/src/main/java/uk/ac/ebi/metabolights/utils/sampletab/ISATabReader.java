package uk.ac.ebi.metabolights.utils.sampletab;


import com.csvreader.CsvReader;
import org.isatools.isacreator.io.importisa.ISAtabFilesImporter;
import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;

import java.util.ArrayList;
import java.util.HashMap;
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

    public String SOURCE_NAME = "Source Name";
    public String SAMPLE_NAME = "Sample Name";

    public String ORGANISM_HEADER = "Organism";
    public String ORGANISM = "Characteristics[" +ORGANISM_HEADER+ "]";
    public String ORGANISM_TERM_SOURCE_REF = "Organism Term Source REF";
    public String ORGANISM_TERM_ACCESSION_NUMBER = "Organism Term Accession Number";

    public String ORGANISM_PART_HEADER = "Organism part";
    public String ORGANISM_PART = "Characteristics[" +ORGANISM_PART_HEADER+ "]";
    public String ORGPART_TERM_SOURCE_REF = "Organism Part Term Source REF";
    public String ORGPART_TERM_ACCESSION_NUMBER = "Organism Part Term Accession Number";


    public ISAtabFilesImporter getIsatabFilesImporter(String configDir) {
        if (isatabFilesImporter == null)
            isatabFilesImporter = new ISAtabFilesImporter(configDir);
        return isatabFilesImporter;
    }

    public List<Map<String, String>> getAdditionalData(Study study){

        List<Map<String, String>> sampleListFromFile = new ArrayList<Map<String, String>>();

        String sampleFile = study.getStudySample().getTableReferenceObject().getTableName();

        try {

            CsvReader fileData = new CsvReader(sampleFile, '\t');
            fileData.readHeaders();
            while (fileData.readRecord()){
                Map<String, String> sampleRow = new HashMap<String, String>();

                sampleRow.put(SOURCE_NAME,fileData.get(SOURCE_NAME));
                sampleRow.put(SAMPLE_NAME,fileData.get(SAMPLE_NAME));

                //Get Organism
                sampleRow.put(ORGANISM,fileData.get(ORGANISM));
                int organismColumnNumber = fileData.getIndex(ORGANISM);
                sampleRow.put(ORGANISM_TERM_SOURCE_REF,fileData.get(organismColumnNumber+1));    //Term source ref is always next
                sampleRow.put(ORGANISM_TERM_ACCESSION_NUMBER,fileData.get(organismColumnNumber+2));

                //Get Organism Part
                sampleRow.put(ORGANISM_PART, fileData.get(ORGANISM_PART));
                int organismPartColumnNumber = fileData.getIndex(ORGANISM_PART);
                sampleRow.put(ORGPART_TERM_SOURCE_REF,fileData.get(organismPartColumnNumber+1));    //Term source ref is always next
                sampleRow.put(ORGPART_TERM_ACCESSION_NUMBER,fileData.get(organismPartColumnNumber+2));

                sampleListFromFile.add(sampleRow);

            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return sampleListFromFile;

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
     * Get the whole investigation from the study
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

    /**
     * Gets the study object from ISAcrator
     * @return Study
     */
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
