/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 3/20/14 11:58 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.sampletab;


import com.csvreader.CsvReader;
import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Factor;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;
import uk.ac.ebi.metabolights.repository.dao.filesystem.IsaTabInvestigationDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ISATabReader {

    private Investigation investigation;
    private String studyDirectory = null;

    public String SOURCE_NAME = "Source Name";
    public String SAMPLE_NAME = "Sample Name";

    public String TERM_SOURCE_REF = "Term Source REF";
    public String TERM_SOURCE_ID = "Term Source ID";
    public String TERM_ACC_NUMBER = "Term Accession Number";

    public String ORGANISM_HEADER = "Organism";
    public String ORGANISM = "Characteristics[" +ORGANISM_HEADER+ "]";
    public String ORGANISM_TYPO = "Characteristics[organism]";
    public String ORGANISM_TERM_SOURCE_REF = ORGANISM_HEADER+" "+TERM_SOURCE_REF;
    public String ORGANISM_TERM_ACCESSION_NUMBER = ORGANISM_HEADER+" "+TERM_ACC_NUMBER;

    public String ORGANISM_PART_HEADER = ORGANISM_HEADER+" part";
    public String ORGANISM_PART = "Characteristics[" +ORGANISM_PART_HEADER+ "]";
    public String ORGPART_TERM_SOURCE_REF = ORGANISM_HEADER+ " Part " + TERM_SOURCE_REF;
    public String ORGPART_TERM_ACCESSION_NUMBER = ORGANISM_HEADER+ " Part "+TERM_ACC_NUMBER;


    /**
     * Returns the investigation from the study
     * @param configDirectory
     * @param isatabDirectory
     * @return ISAcreator Investigation
     */
    public Investigation getIsaInvestigation(String configDirectory, String isatabDirectory){
        Investigation investigation = new Investigation();

        investigation = getInvestigation(configDirectory,isatabDirectory);

        return investigation;
    }


    public List<String> getOntologyNameFromSample(Study study){
        String sampleFile = getSampleFileName(study);
        List<String> termSources = new ArrayList<String>();

        try {

            CsvReader fileData = new CsvReader(sampleFile, '\t');
            fileData.readHeaders();

            while (fileData.readRecord()){
                String[] headers = fileData.getHeaders();
                String termSourceRef = "";

                for (int i = 0; i <= headers.length-1; i++){      //Have to substract 1 from header length as the array starts with 0
                    if (headers[i].endsWith(TERM_SOURCE_REF)){
                        termSourceRef = fileData.get(i);

                        if (!termSources.contains(termSourceRef) && !termSourceRef.isEmpty())
                            termSources.add(termSourceRef);
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return termSources;

    }


    private String getSampleFileName(Study study){
        //String path = System.getProperty("user.dir");
        String fileName = study.getStudySampleFileIdentifier();

        return studyDirectory + "/" + fileName;
    }

    /**
     * Reads data from the study file
     * @param study
     * @return rows from the sample file
     */
    //TODO, replace this with the new Study module in the webapp
    // Can get this from Investigation -> Studies -> <array> -> value -> studySampleRecord -> referenceData
    public List<Map<String, String>> getAdditionalData(Study study, List<String> headerNames){

        List<Map<String, String>> sampleListFromFile = new ArrayList<Map<String, String>>();

        String sampleFile = getSampleFileName(study);

        try {

            CsvReader fileData = new CsvReader(sampleFile, '\t');
            fileData.readHeaders();
            while (fileData.readRecord()){
                Map<String, String> sampleRow = new HashMap<String, String>();

                sampleRow.put(SOURCE_NAME,fileData.get(SOURCE_NAME));
                sampleRow.put(SAMPLE_NAME,fileData.get(SAMPLE_NAME));

                //Get Organism
                int organismColumnNumber = fileData.getIndex(ORGANISM);

                if (organismColumnNumber == -1){  //Argh, stop with all the typos in the submission
                    sampleRow.put(ORGANISM,fileData.get(ORGANISM_TYPO));
                    organismColumnNumber = fileData.getIndex(ORGANISM_TYPO);
                } else {
                    sampleRow.put(ORGANISM,fileData.get(ORGANISM));
                    organismColumnNumber = fileData.getIndex(ORGANISM);
                }

                sampleRow.put(ORGANISM_TERM_SOURCE_REF,fileData.get(organismColumnNumber+1));    //Term source ref is always next
                sampleRow.put(ORGANISM_TERM_ACCESSION_NUMBER,fileData.get(organismColumnNumber+2));

                //Get Organism Part
                sampleRow.put(ORGANISM_PART, fileData.get(ORGANISM_PART));
                int organismPartColumnNumber = fileData.getIndex(ORGANISM_PART);
                sampleRow.put(ORGPART_TERM_SOURCE_REF,fileData.get(organismPartColumnNumber+1));    //Term source ref is always next
                sampleRow.put(ORGPART_TERM_ACCESSION_NUMBER,fileData.get(organismPartColumnNumber+2));

                //Get additional columns
                for (String headerName : headerNames){
                    sampleRow.put(headerName, fileData.get(headerName));
                    int attributePartColumnNumber = fileData.getIndex(headerName);

                    if (attributePartColumnNumber >0 ){  //If the column is not found, the value is -1
                        sampleRow.put("TSR "+headerName, fileData.get(attributePartColumnNumber+1));    //Term source ref is always next
                        sampleRow.put("TSI "+headerName, fileData.get(attributePartColumnNumber+2));
                    }
                }

                sampleListFromFile.add(sampleRow);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sampleListFromFile;

    }

//    /**
//     * Import an ISATAB file set into corresponding ISA model objects
//     *
//     * @param configDir - Directory containing the current MetaboLights configuration files
//     * @param parentDir - Directory containing the ISATAB files, eg. a study
//     * @return boolean if successful or not!
//     */
//    public boolean validateISAtabFiles(String configDir, String parentDir) {
//        return getIsatabFilesImporter(configDir).importFile(parentDir);
//    }

    /**
     * Retrieves the investigation object from ISAcreator, this also contains studies etc etc
     *
     * @return investigation
     */
    public Investigation getInvestigation() {

        return investigation;
    }

    /**
     * Get the whole investigation from the study
     * @param configDir
     * @param parentDir
     * @return ISAcreator Investigation object
     */
    public Investigation getInvestigation(String configDir, String parentDir) {

        this.studyDirectory = parentDir;

        IsaTabInvestigationDAO isaTabDAO = new IsaTabInvestigationDAO(configDir);

        investigation = isaTabDAO.getInvestigation(parentDir);

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
     * Gets the study object from ISAcreator
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

    /**
     * Get a list of attribute nodes populated from the sample sheet
     * @param study
     * @return
     */
    public List<String> getStudyFactors(Study study){

        List<String> attributeList = new ArrayList<String>();

        //TODO, complete
        List<Factor> studyFactors = study.getFactors();
        for (Factor factor : studyFactors) {
            String factorName = factor.getFactorName();
/*            String factorType = factor.getFactorType();

            if (factorType.contains(":")){
                String[] factorTypes = factorType.split(":");
                factorType = factorTypes[1];
            }

            String factorAcc = factor.getFactorTypeTermAccession();
            String factorSource = factor.getFactorTypeTermSource();


            CommentAttribute commentAttribute = new CommentAttribute(factorName,factorType);
            commentAttribute.setTermSourceREF(factorSource);
            commentAttribute.setTermSourceID(factorSource);

            if (SampleTabTools.isInteger(factorAcc))
                commentAttribute.setTermSourceIDInteger(new Integer(factorAcc));*/

            attributeList.add("Factor Value["+factorName+"]");

        }



        return attributeList;

    }

}
