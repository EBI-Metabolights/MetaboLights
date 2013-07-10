package uk.ac.ebi.metabolights.utils.sampletab;

import org.apache.log4j.Logger;
import org.isatools.isacreator.model.*;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.SampleData;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Database;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Organization;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Person;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.TermSource;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.SampleNode;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.CharacteristicAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.OrganismAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.renderer.SampleTabWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 24/04/2013
 * Time: 10:07
 */
public class SampleTabExporter {

    private static Logger logger = Logger.getLogger(SampleTabExporter.class);
    private ISATabReader isaTabReader = new ISATabReader();
    private SampleTabTools tools = new SampleTabTools();
    private String metaboLightsURL = "http://www.ebi.ac/uk/metabolights/";

    private enum URLS {
        //TODO, must change all these url's to use BioPortal, OLS or Miriam
        NCBI("http://www.ncbi.nlm.nih.gov/taxonomy/"),
        NCIt("http://ncit.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=NCI%20Thesaurus&code="),
        DOID("http://purl.obolibrary.org/obo/DOID_"),
        MDR("http://bioportal.bioontology.org/ontologies/42280?p=terms&conceptid="),
        EFO("http://www.ebi.ac.uk/efo/");

        private final String url;

        private URLS(String toString) {
            this.url = toString;
        }

        public String toString(){
            return url;
        }

    }

    /**
     * Main, run from commandline
     * @param args
     */
    public static void main(String[] args){

        if (!commandLineValidation(args)){

            System.out.println("Usage:");
            System.out.println("Parameter 1: The folder name of the configuration files for the study");
            System.out.println("Parameter 2: The folder name of the MTBLS study files");
            System.out.println("Parameter 3: The file name of the SampleTab file you want to export, this is recreated at runtime");


        } else {
            new SampleTabExporter(args);
        }

    }

    public SampleTabExporter(){
    }

    public SampleTabExporter(String[] args){
        //export the SampleTab file
        exportSampleFile(args[0], args[1], args[2]);

    }


    /**
     * Returns the URL for the given ontology name
     * @param name
     * @return
     */
    public String getURLByName(String name){

        //TODO, try OLS or BioPortal first, then resolve below
        String uri = null;
        if (name.equals(URLS.NCIt.name()))
            uri = URLS.NCIt.url;
        else if (name.equals(URLS.DOID.name()))
            uri = URLS.DOID.url;
        else if (name.equals(URLS.NCBI.name()))
            uri = URLS.NCBI.url;
        else if (name.equals(URLS.MDR.name()))
            uri = URLS.MDR.url;
        else if (name.equals(URLS.EFO.name()))
            uri = URLS.EFO.url;
        //TODO, add BAO,

        return uri;
    }

    /**
     * Returns the investigation from the study
     * @param configDirectory
     * @param isatabDirectory
     * @return ISAcreator Investigation
     */
    private Investigation getIsaInvestigation(String configDirectory, String isatabDirectory){
        Investigation investigation = new Investigation();

        Boolean isaFound = isaTabReader.validateISAtabFiles(configDirectory, isatabDirectory);

        if (isaFound)
            investigation = isaTabReader.getInvestigation();

        return investigation;
    }

    /**
     * Export a sampletab file based on your isatab config files and Investigation/Study data
     * @param filename
     * @param configDirectory
     * @param isatabDirectory
     * @return boolean, did it work?
     */
    public boolean exportSampleFile(String configDirectory, String isatabDirectory, String filename) {

        //Populate the sampleData object from the ISA-tab files
        SampleData samples = createSampleData(configDirectory, isatabDirectory);

        if (samples.msi.databases != null){
            //We have some sample data, let's write this to file

            File sampletabFile = new File(filename);
            Writer writer = null;

            try {
                writer = new FileWriter(sampletabFile);
                SampleTabWriter sampleTabWriter = new SampleTabWriter(writer) ;
                sampleTabWriter.write(samples);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }


        }


        return true;
    }

    /**
     * This adds all the data from the ISA-tab files into the Sample-Tab SampleData object
     * @param configDirectory
     * @param isatabDirectory
     * @return SampleData
     */
    private SampleData createSampleData(String configDirectory, String isatabDirectory){
        SampleData sampleData = new SampleData();
        //Need to get the investigation first
        Investigation investigation = getIsaInvestigation(configDirectory, isatabDirectory);

        //First get the study, can only be on per MetaboLights rules
        Study study = isaTabReader.getStudyFromInvestigation();

        if (addSampleData(sampleData, study)){
           //log.info("Sucessfully loaded data for study "+study.getStudyId());
        } else {
           // log.error("Could not load data for study " + study.getStudyId());
        }

        return sampleData;
    }


    /**
     *
     * @param sampleData
     * @param study
     * @return
     */

    private boolean addSampleData(SampleData sampleData, Study study) {

        logger.info("Start adding MSI data to the file");

        logger.info("Add Submission data");
        if (!setSubmissionData(sampleData, study))
            return false;

        //Add Person and Organisation data
        if (!setPersonAndOrganisationData(sampleData, isaTabReader.getContcatsForStudy(study.getStudyId())))
            return false;

        logger.info("Add Publication data");
        if (!setPublicationData(sampleData, study))
            logger.info("WARNING: Could not find any Publication info");

        //Add Database data
        if (!setDatabaseData(sampleData, study))
            return false;

        //Add Term Source, the ontologies used in the study design
        if (!setTermSourceData(sampleData, study))
            return false;

        //
        // Start adding SCD data to the file
        //

        if (!setSampleObjectData(sampleData, study))
            return false;

        return true;
    }


    /**
     * This adds the SUBMISSION part of the sampletab definition
     * @param sampleData
     * @param study
     * @return true if SampleData.MSI has data
     */
    private boolean setSubmissionData(SampleData sampleData, Study study){

        if (study == null)
            return false;

        if (study.getStudyTitle() != null)
            sampleData.msi.submissionTitle = study.getStudyTitle();

        if (study.getStudyDesc() != null)
            sampleData.msi.submissionDescription = study.getStudyDesc();

        if (study.getStudyId() != null)
            sampleData.msi.submissionIdentifier = study.getStudyId();

        //I have skipped the optional field "Submission Version" as this comes from the "sample tab jar" we use

        if (study.getDateOfSubmission() != null)
            sampleData.msi.submissionUpdateDate = tools.parseDate(study.getDateOfSubmission());

        if (study.getPublicReleaseDate() != null)
            sampleData.msi.submissionReleaseDate = tools.parseDate(study.getPublicReleaseDate());

        if (sampleData.msi == null)
            return false;

        return true;

    }

    /**
     * This adds the PERSON and ORGANIZATION part of the sampleTab definition
     * @param sampleData
     * @param contacts
     * @return true if SampleData.MSI.person and SampleData.MSI.organization has data
     */
    private boolean setPersonAndOrganisationData(SampleData sampleData, List<Contact> contacts){
        if (sampleData == null || contacts.isEmpty())
            return false; //Must have a valid sampleData object and some contacts to add to it.

        Iterator iterator = contacts.iterator();

        while (iterator.hasNext()){
            Contact contact = (Contact) iterator.next();

            Person person = new Person(contact.getLastName(),contact.getMidInitial(), contact.getFirstName(),contact.getEmail(),contact.getRole());
            if (person != null)
                sampleData.msi.persons.add(person);

            Organization organization = new Organization(contact.getAffiliation(), contact.getAddress(), null, contact.getEmail(),contact.getRole());  //URI missing

            if (organization != null)
                sampleData.msi.organizations.add(organization);
        }

        if (sampleData.msi.persons == null || sampleData.msi.organizations == null)
            return false;

        return true;

    }

    /**
     * This adds the PUBLICATION part of the sampleTab definition
     * @param sampleData
     * @param study
     * @return true if we have managed to populate the publication list
     */
    private boolean setPublicationData(SampleData sampleData, Study study){
        if (sampleData == null || study.getPublications().isEmpty())
            return false; //Must have a valid sampleData object and some contacts to add to it.
        Iterator iterator = study.getPublications().iterator();
        while (iterator.hasNext()){
            Publication isaPublication = (Publication) iterator.next();

            uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Publication publication =
                    new uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Publication(isaPublication.getPubmedId(), isaPublication.getPublicationDOI());
            if (publication != null)
                sampleData.msi.publications.add(publication);

        }

        if (sampleData.msi.publications == null)
            return false;

        return true;

    }

    /**
     * This adds the DATABASE part of the sampleTab definition
     * @param sampleData
     * @param study
     * @return true if we have managed to populate the database list
     */
    private boolean setDatabaseData(SampleData sampleData, Study study){
        if (sampleData == null || study.getStudyId().isEmpty())
            return false; //Must have a valid sampleData object and some study data to add to it.

        Database database = new Database("MetaboLights",metaboLightsURL+study.getStudyId(),study.getStudyId());
        sampleData.msi.databases.add(database);

        if (sampleData.msi.databases == null)
            return false;

        return true;
    }

    /**
     * The full ontology term in, a cleaner version out....
     * @param ontoTerm
     * @return
     */
    private String cleanOntologyTerm(String ontoTerm){

        String[] cleanDesigns = null;
        String cleanDesign = ontoTerm;


        if (ontoTerm.contains(":")){
            cleanDesigns = ontoTerm.split(":");

            if (cleanDesigns != null)
                cleanDesign = cleanDesigns[1];    //Skip the reference before the colon, "efo:EFO_0004529" becomes "EFO_0004529"
        }

        return cleanDesign;

    }

    /**
     * This adds the Term and Source (ontologies used) part of the sampleTab definition
     * @param sampleData
     * @param study
     * @return true if we have managed to populate the TermSource list
     */
    private boolean setTermSourceData(SampleData sampleData, Study study){
        if (sampleData == null || study.getStudyDesigns().isEmpty())
            return false; //Must have a valid sampleData object and some study design data to add to it.

        Iterator iterator  = study.getStudyDesigns().iterator();
        while (iterator.hasNext()){
            StudyDesign studyDesign = (StudyDesign) iterator.next();

            if (studyDesign != null){

                String uri = getURLByName(studyDesign.getStudyDesignTypeTermSourceRef());

                if (uri != null){

                    uri = uri+ cleanOntologyTerm(studyDesign.getStudyDesignTypeTermAcc()); //Add the (cleaned) accession number to the URL

                    if (studyDesign.getStudyDesignTypeTermAcc().equals("Metabolomics"))
                        uri = "http://bioportal.bioontology.org/ontologies/50373?p=terms&conceptid=C49019"; //For "Metabolomics" we enforce this ontology

                    //TODO, last null is "version"
                    TermSource termSource = new TermSource(studyDesign.getStudyDesignType(), uri, null);
                    sampleData.msi.getOrAddTermSource(termSource);
                }

            }
        }


        //TODO, some check if the term source was added correctly

        return true;


    }

    private URLS getURI(String term){
        return URLS.valueOf(term);
    }

    private boolean setSampleObjectData(SampleData sampleData, Study study){
        if (sampleData == null || study == null)
            return false; //Must have a valid sampleData object and assay information from the study to add to it.


        //Get additional data from the file, the sample file in ISAcreator is not good enough for the sampleDb
        List<Map<String, String>> additionalFileData = isaTabReader.getAdditionalData(study);
        for (Map<String, String> columns : additionalFileData){

            SampleNode samplenode = new SampleNode();          //Per sample row in ISA-tab

            if (columns.containsKey(isaTabReader.SAMPLE_NAME)){
                String sampleName = columns.get(isaTabReader.SAMPLE_NAME);
                samplenode.setNodeName(sampleName);  //The proper name of the sample
            }

            if (columns.containsKey(isaTabReader.ORGANISM)){
                String organism = columns.get(isaTabReader.ORGANISM);
                String termSource = columns.get(isaTabReader.ORGANISM_TERM_SOURCE_REF);
                //Check if the term is a number
                String termNumber = columns.get(isaTabReader.ORGANISM_TERM_ACCESSION_NUMBER);
                if (termNumber.contains("_")){
                    String[] termNumbers = termNumber.split("_");
                    termNumber = termNumbers[1]; //get rid if the term before the underscore, "obo:NCBITaxon_10090" becomes "10090"

                    if (!SampleTabTools.isInteger(termNumber))
                        return false;

                }

                Integer termId = new Integer(termNumber);
                //samplenode.addAttribute(new CharacteristicAttribute(isaTabReader.ORGANISM_HEADER,organism));
                OrganismAttribute organismAttribute = new OrganismAttribute(organism, termSource, termId);
                samplenode.addAttribute(organismAttribute);
            }

            if (columns.containsKey(isaTabReader.ORGANISM_TERM_ACCESSION_NUMBER)){
                String organismPart = columns.get(isaTabReader.ORGANISM_PART);
                samplenode.addAttribute(new CharacteristicAttribute(isaTabReader.ORGANISM_PART_HEADER, organismPart));
            }

            try {
                sampleData.scd.addNode(samplenode);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }

        }


        /*
        //get all the sample records from ISAcreator
        Object[][] sampleObjects = study.getStudySampleDataMatrix();
        int i = 0;
        for (Object[] sampleObject : sampleObjects) {
            String sourceName = null, organism = null, organismPart = null, protocolRef = null, sampleName = null, factor = null;
            i++;

            SampleNode samplenode = new SampleNode();          //Per sample row in ISA-tab

            if (sampleObject[0] != null) { sourceName = (String) sampleObject[0]; }
            if (sampleObject[1] != null) { organism = (String) sampleObject[1]; }
            if (sampleObject[2] != null) { organismPart = (String) sampleObject[2]; }
            if (sampleObject[3] != null) { protocolRef = (String) sampleObject[3]; }
            if (sampleObject[4] != null) { sampleName = (String) sampleObject[4]; }
            if (sampleObject[5] != null) { factor = (String) sampleObject[5];  }

            if (sampleName == null && sourceName != null)
                sampleName = sourceName; //If do not have a proper sample name, use the sourcename

            if (i > 1) {//Skip header row
                samplenode.setNodeName(sampleName);  //The proper name of the sample

                //organism
                if (organism != null){
                    //OrganismAttribute organismAttribute = new OrganismAttribute(organism);
                    samplenode.addAttribute(new CharacteristicAttribute("Organism",organism));
                }

                //organism part as an attribute
                if (organismPart != null){
                    samplenode.addAttribute(new CharacteristicAttribute("Organism part",organismPart));
                }

                try {
                    sampleData.scd.addNode(samplenode);
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    return false;
                }

            }

        } */

        return true;
    }



    /**
     * Checks if the correct parameters are given
     * @param args
     * @return true if you got it right
     */
    private static boolean commandLineValidation(String args[]){

        // If there isn't any parameter
        if (args == null || args.length== 0){
            return false;
        }

        // Check first parameter is the config folder
        File first = new File(args[0]);
        if (!first.exists()){
            System.out.println("ERROR:  1st parameter must be the configuration folder: " + args[0]);
            System.out.println("----");
            return false;
        }

        // Check first parameter is the config file file
        File secound = new File(args[1]);
        if (!secound.exists()){
            System.out.println("ERROR: 2nd parameter must be the ISAtab (MTBLS Study) folder" + args[1]);
            System.out.println("----");
            return false;
        }

        if (args[2] == null){
            System.out.println("ERROR: You must also give us the filename of the SampleTab file you want to export");
            System.out.println("----");
            return false;
        }

        return true;

    }


}
