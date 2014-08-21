/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/16/13 11:03 AM
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

package uk.ac.ebi.metabolights.utils.sampletab;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Publication;
import org.isatools.isacreator.model.Study;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.SampleData;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Database;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Organization;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Person;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.TermSource;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.SampleNode;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.CharacteristicAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.CommentAttribute;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.scd.node.attribute.NamedAttribute;
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

    private final static Logger logger = Logger.getLogger(SampleTabExporter.class.getName());
    private ISATabReader isaTabReader = new ISATabReader();
    private SampleTabTools tools = new SampleTabTools();
    private SampleTabUriResolver uriResolver = new SampleTabUriResolver();

    private String metaboLightsURL = "http://www.ebi.ac/uk/metabolights/";
    private String[] subClassification = {"strain", "part", "family"};



    /**
     * Main, run from commandline
     * @param args
     */
    public static void main(String[] args){

        if (!SampleTabTools.commandLineValidation(args)){

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
        Investigation investigation = isaTabReader.getIsaInvestigation(configDirectory, isatabDirectory);

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
     * This adds the Term and Source (ontologies used) part of the sampleTab definition
     * @param sampleData
     * @param study
     * @return true if we have managed to populate the TermSource list
     */
    private boolean setTermSourceData(SampleData sampleData, Study study){
        if (sampleData == null || study.getStudyDesigns().isEmpty())
            return false; //Must have a valid sampleData object and some study design data to add to it.

        List<String> ontologies = isaTabReader.getOntologyNameFromSample(study);

        for (String ontoName : ontologies){
            String uri = uriResolver.getURLByName(ontoName);

            if (uri != null){
                TermSource termSource = new TermSource(ontoName, uri, null);
                sampleData.msi.getOrAddTermSource(termSource);
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
     * Get additional data from the file, the sample file in ISAcreator is not enough for the sampleDb output
     * @param sampleData
     * @param study
     * @return TRUE if we managed to get and set the sample data from the s_*.txt file(s)
     */
    private boolean setSampleObjectData(SampleData sampleData, Study study){
        if (sampleData == null || study == null)
            return false; //Must have a valid sampleData object and assay information from the study to add to it.


        //Get additional data from the file, the sample file in ISAcreator is not good enough for the sampleDb
        List<Map<String, String>> additionalFileData = isaTabReader.getAdditionalData(study, isaTabReader.getStudyFactors(study));
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
                String termNumber = tools.cleanTermAttributes(columns.get(isaTabReader.ORGANISM_TERM_ACCESSION_NUMBER));

                if (!tools.isInteger(termNumber)){      //Check if the term is a number
                    logger.error("Term number from the sample file is not a number or is empty!");
                    return false;
                }

                Integer termId = new Integer(termNumber);
                //samplenode.addAttribute(new CharacteristicAttribute(isaTabReader.ORGANISM_HEADER,organism));
                OrganismAttribute organismAttribute = new OrganismAttribute(organism, termSource, termId);
                samplenode.addAttribute(organismAttribute);
            }

            if (columns.containsKey(isaTabReader.ORGANISM_PART)){ //TODO, should also reflect the ontology
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


                    //Add two new named attributes to capture the ontologies for organism part
                    String termSource = columns.get(isaTabReader.ORGPART_TERM_SOURCE_REF);
                    NamedAttribute orgPartTermSource = new NamedAttribute(isaTabReader.TERM_SOURCE_REF,termSource);
                    samplenode.addAttribute(orgPartTermSource);

                    String termNumber = tools.cleanTermAttributes(columns.get(isaTabReader.ORGPART_TERM_ACCESSION_NUMBER));
                    NamedAttribute orgPartTermNumber = new NamedAttribute(isaTabReader.TERM_SOURCE_ID,termNumber);
                    samplenode.addAttribute(orgPartTermNumber);

                }
            }

            //TODO, add the study design factors as well
            //Check what has been used for study desing in the investigation, then add dynamically as "Comment[other]" on the sample

            List<String> allAttributes = isaTabReader.getStudyFactors(study);
            for (String attribute : allAttributes){

                if (columns.containsKey(attribute)){
                    CommentAttribute commentAttribute = new CommentAttribute("other",columns.get(attribute));
                    //commentAttribute.setAttributeValue(columns.get(attribute));

                    String termSourceHeader = "TSI "+attribute;
                    if (columns.containsKey(termSourceHeader)){
                        String termSourceId = columns.get(termSourceHeader);
                        commentAttribute.setTermSourceID(termSourceId);

                        if (tools.isInteger(termSourceId))
                            commentAttribute.setTermSourceIDInteger(new Integer(termSourceId));
                    }

                    String termSourceRefHeader = "TSR "+attribute;
                    if (columns.containsKey(termSourceRefHeader))
                        commentAttribute.setTermSourceREF(columns.get(termSourceRefHeader));

                    if (commentAttribute.getAttributeValue() != "")
                        samplenode.addAttribute(commentAttribute);
                }

            }


            try {
                sampleData.scd.addNode(samplenode);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }

        }

        return true;
    }



}
