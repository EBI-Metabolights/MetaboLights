/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 02/10/13 14:17
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.sampletab;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.isatools.isacreator.model.*;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.SampleData;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Database;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Organization;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Person;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.TermSource;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.SampleNode;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.CharacteristicAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.CommentAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.OrganismAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.renderer.SampleTabWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
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

    private final static Logger logger = Logger.getLogger(SampleTabExporter.class.getName());
    private ISATabReader isaTabReader = new ISATabReader();
    private SampleTabTools tools = new SampleTabTools();
    private String metaboLightsURL = "http://www.ebi.ac/uk/metabolights/";
    private String[] subClassification = {"strain", "part", "family"};

    private enum URLS {
        //TODO, must change all these url's to use BioPortal, OLS or Miriam
        NCBI("http://www.ncbi.nlm.nih.gov/taxonomy"), NCBI_direct_url("http://www.ncbi.nlm.nih.gov/taxonomy/"),
        NCIt("http://ncit.nci.nih.gov/ncitbrowser/pages/home.jsf?version=13.06d"), NCIt_direct_url("http://ncit.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=NCI%20Thesaurus&code="),
        DOID("http://www.berkeleybop.org/ontologies/doid/doid.obo"), DOID_direct_url("http://purl.obolibrary.org/obo/DOID_"),
        MDR("http://purl.bioontology.org/ontology/MEDDRA"), MDR_direct_url("http://bioportal.bioontology.org/ontologies/42280?p=terms&conceptid="),
        NEWT("http://www.ebi.ac.uk/newt"),
        EFO("http://www.ebi.ac.uk/efo"), EFO_direct_url("http://www.ebi.ac.uk/efo/"),
        BAO("http://bioassayontology.org/bao"),
        SBO("http://www.ebi.ac.uk/sbo/main");

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

        logger.info("Starting SampleTab exporter, with the following arguments: "+ args[0] +" : "+ args[1] +" : "+ args[2]);
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
        else if (name.equals(URLS.NCBI.name()) || name.equals("NCBITaxon"))   //Annoying that two different names are being used
            uri = URLS.NCBI.url;
        else if (name.equals(URLS.MDR.name()))
            uri = URLS.MDR.url;
        else if (name.equals(URLS.EFO.name()))
            uri = URLS.EFO.url;
        else if (name.equals(URLS.BAO.name()))
            uri = URLS.BAO.url;
        else if (name.equals(URLS.SBO.name()))
            uri = URLS.SBO.url;

        return uri;
    }

    /**
     * Returns the investigation from the study
     * @param configDirectory
     * @param isatabDirectory
     * @return ISAcreator Investigation
     */
    public Investigation getIsaInvestigation(String configDirectory, String isatabDirectory){
        Investigation investigation = new Investigation();

        investigation = isaTabReader.getInvestigation(configDirectory,isatabDirectory);

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

        logger.info("Populate the sampleData object from the ISA-tab files");
        SampleData samples = createSampleData(configDirectory, isatabDirectory);

        if (samples.msi.databases != null){
            logger.info("We have some sample data, let's write this to file");

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
           logger.info("Sucessfully loaded data for study "+study.getStudyId());
        } else {
           logger.error("ERROR: Could not load data for study " + study.getStudyId());
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

        logger.info("Add Person and Organisation data");
        if (!setPersonAndOrganisationData(sampleData, isaTabReader.getContcatsForStudy(study.getStudyId())))
            logger.warn("WARNING: Could not find any Person and Organisation data");

        logger.info("Add Publication data");
        if (!setPublicationData(sampleData, study))
            logger.warn("WARNING: Could not find any Publication info");

        logger.info("Add Database data");
        if (!setDatabaseData(sampleData, study))
            return false;

        logger.info("Add Term Source, the ontologies used in the study design");
        if (!setTermSourceData(sampleData, study))
            return false;

        logger.info("Start adding SCD data to the file");

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

        if (study.getDateOfSubmission() != null && study.getDateOfSubmission().length() > 3)
            sampleData.msi.submissionUpdateDate = tools.parseDate(study.getDateOfSubmission());

        if (study.getPublicReleaseDate() != null && study.getPublicReleaseDate().length() > 3)
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

                    //Took this out as we are not referencing directly
                    //uri = uri+ cleanOntologyTerm(studyDesign.getStudyDesignTypeTermAcc()); //Add the (cleaned) accession number to the URL

                    //logger.info("Enforcing bioportal ontology for term 'Metabolomics'");
                    //if (studyDesign.getStudyDesignTypeTermAcc().equals("Metabolomics"))
                    //    uri = "http://bioportal.bioontology.org/ontologies/50373?p=terms&conceptid=C49019"; //For "Metabolomics" we enforce this ontology

                    //TODO, last null is "version"
                    TermSource termSource = new TermSource(studyDesign.getStudyDesignTypeTermSourceRef(), uri, null);
                    sampleData.msi.getOrAddTermSource(termSource);
                }

            }
        }


        //TODO, some check if the term source was added correctly

        return true;


    }

    /**
     * Returns a "cleaner" organism part name if we are dealing with strains or lines
     * @param organismPart
     * @param subs
     * @return A "cleaner" organism part name
     */
    private String cleanStrainName(String organismPart, String subs){

        if (organismPart.startsWith(subs))
            organismPart = organismPart.replace(subs+" ",""); //Clean "Strain " or "Line "
        else if (organismPart.endsWith(subs))
            organismPart = organismPart.replace(" "+subs,""); //Clean " strain" or " line"
        else
            organismPart = organismPart.replace(" "+subs+" "," ");   //Well, must be in the middle somewhere, so " strain " becomes " "

        return organismPart;

    }

    /**
     * Get a list of attribute nodes populated from the sample sheet
     * @param study
     * @return
     */
    private List<String> getStudyFactors(Study study){

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

    /**
     * Get additional data from the file, the sample file in ISAcreator is not enough for the sampleDb output
     * @param sampleData
     * @param study
     * @return TRUE if we managed to get and set the sample data from the s_*.txt file(s)
     */
    private boolean setSampleObjectData(SampleData sampleData, Study study){
        if (sampleData == null || study == null)
            return false; //Must have a valid sampleData object and assay information from the study to add to it.


        //Get additional data from the file, the sample file in ISAcreator is not good enough for the sampleDb
        List<Map<String, String>> additionalFileData = isaTabReader.getAdditionalData(study, getStudyFactors(study));
        for (Map<String, String> columns : additionalFileData){

            SampleNode samplenode = new SampleNode();          //Per sample row in ISA-tab

            if (columns.containsKey(isaTabReader.SAMPLE_NAME)){
                String sampleName = columns.get(isaTabReader.SAMPLE_NAME);
                samplenode.setNodeName(sampleName);  //The proper name of the sample
            }

            String organism = null;

            if (columns.containsKey(isaTabReader.ORGANISM)){
                organism = columns.get(isaTabReader.ORGANISM);

                if (organism == null || organism == "")
                    continue;           //Skip this row

                String termSource = columns.get(isaTabReader.ORGANISM_TERM_SOURCE_REF);
                //Check if the term is a number
                String termNumber = columns.get(isaTabReader.ORGANISM_TERM_ACCESSION_NUMBER);
                if (termNumber.contains("_")){
                    String[] termNumbers = termNumber.split("_");
                    logger.info("Removing underscore from ontology term, "+ termNumber + " became "+ termNumbers[1]);
                    termNumber = termNumbers[1]; //get rid if the term before the underscore, "obo:NCBITaxon_10090" becomes "10090"
                }

                if (!SampleTabTools.isInteger(termNumber)){
                    logger.error("Term number from the sample file is not a number or is empty!");
                    return false;
                }

                Integer termId = new Integer(termNumber);
                //samplenode.addAttribute(new CharacteristicAttribute(isaTabReader.ORGANISM_HEADER,organism));
                OrganismAttribute organismAttribute = new OrganismAttribute(organism, termSource, termId);
                samplenode.addAttribute(organismAttribute);
            }

            if (columns.containsKey(isaTabReader.ORGANISM_PART)){ //ORGANISM_TERM_ACCESSION_NUMBER
                String organismPart = columns.get(isaTabReader.ORGANISM_PART);

                if (organismPart != "" ){     //The Organism part column is normally present but with an empty string value if no data recorded, ignore this

                    boolean addStrain = false;

                    for (String subs : subClassification){
                        if (StringUtils.containsIgnoreCase(organismPart, subs)){
                            organismPart = cleanStrainName(organismPart, subs); //Remove the 'offending' words
                            addStrain = true;
                            break; //Don't loop through the rest
                        }
                    }

                    if (!organismPart.equals(organism)){            //QuickFix. Make sure that the Organism Part is different from Organism

                        String orgPartHeader = isaTabReader.ORGANISM_PART_HEADER;

                        if (addStrain)
                            orgPartHeader = "strain or line";

                        samplenode.addAttribute(new CharacteristicAttribute(orgPartHeader, organismPart));
                    }
                }
            }

            //TODO, add the study design factors as well
            //Check what has been used for study desing in the investigation, then add dynamically as "Comment[other]" on the sample

            List<String> allAttributes = getStudyFactors(study);
            for (String attribute : allAttributes){

                if (columns.containsKey(attribute)){
                    CommentAttribute commentAttribute = new CommentAttribute("other",columns.get(attribute));
                    //commentAttribute.setAttributeValue(columns.get(attribute));

                    String termSourceHeader = "TSI "+attribute;
                    if (columns.containsKey(termSourceHeader)){
                        String termSourceId = columns.get(termSourceHeader);
                        commentAttribute.setTermSourceID(termSourceId);

                        if (SampleTabTools.isInteger(termSourceId))
                            commentAttribute.setTermSourceIDInteger(new Integer(termSourceId));
                    }

                    String termSourceRefHeader = "TSR "+attribute;
                    if (columns.containsKey(termSourceRefHeader))
                        commentAttribute.setTermSourceREF(columns.get(termSourceRefHeader));

                    if (commentAttribute.getAttributeValue() != "")
                        samplenode.addAttribute(commentAttribute);
                }

            }
/*
            List<CommentAttribute> attributes = addStudyFactors(study);
            if (attributes != null){
                for (CommentAttribute attribute : attributes) {
                    samplenode.addAttribute(attribute);
                }
            }
*/


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
