/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-22
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.utils.exporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MetabolightsXMLExporter {

    private final static Logger logger = LoggerFactory.getLogger(MetabolightsXMLExporter.class.getName());
    private final static String STUDIES = "entries";
    private final static String FIELD = "field";
    private final static String REF = "ref";
    private final static String DATE = "date";
    private final static String ML_BASE_URL = "http://www.ebi.ac.uk/metabolights";
    private final static String ML_BASE_FTP = "ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/";
    private static String xmlFileName = null;
    private static DocumentBuilderFactory dbf = null;
    private static DocumentBuilder builder = null;
    private static Document doc = null;
    private static String[] allStudies = null;
    private static String[] allCompounds = null;
    private static String WSCLIENT_URL = ML_BASE_URL + "/webservice/";
    private static MetabolightsWsClient wsClient;
    private static List<String> metaboliteList;

    public MetabolightsXMLExporter(){}

    public static DocumentBuilderFactory getDbf() {
        if (dbf == null)
            dbf = DocumentBuilderFactory.newInstance();
        return dbf;
    }

    public static DocumentBuilder getBuilder() {
        try {
            if (builder == null)
                builder = getDbf().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return builder;
    }

    public static Document getDoc() {
        if (doc == null)
            doc = getBuilder().newDocument();
        return doc;
    }

    public static String[] getAllStudies() {
        if (allStudies == null)
            allStudies = getStudiesList();
        return allStudies;
    }

    public static MetabolightsWsClient getWsClient() {
        if (wsClient == null)
            wsClient = new MetabolightsWsClient(WSCLIENT_URL);
        return wsClient;
    }

    public static List<String> getMetaboliteList() {
        if (metaboliteList == null)
            metaboliteList  = new ArrayList<>();

        return metaboliteList;
    }

    public static void setMetaboliteList(List<String> metaboliteList) {
        MetabolightsXMLExporter.metaboliteList = metaboliteList;
    }

    public static Boolean hasValue(String str){
        return str != null && !str.isEmpty();
    }

    private static void initParams(String fileName, String wsClientURL){
        if (xmlFileName == null)
            xmlFileName = fileName;

        if (wsClientURL != null)        //To override the WS URL if passed at startup
            WSCLIENT_URL = wsClientURL;

        validateParams(new String[]{xmlFileName,wsClientURL});

        doc = getDoc();                 //Set up builder, parser and document
        allStudies = getAllStudies();
        allCompounds = getAllCompounds();
        wsClient = getWsClient();
    }

    public static String[] getAllCompounds() {
        if (allCompounds == null)
            allCompounds = getCompoundsList();
        return allCompounds;
    }

    private static String[] getCompoundsList(){
        RestResponse<String[]> response = getWsClient().getAllCompoundsAcc();
        //return new String[]{"MTBLC100"};
        return response.getContent();
    }

    public static void main(String[] args) throws Exception {

        if (!validateParams(args)){
            System.out.println("Usage:");
            System.out.println("    Parameter 1: The name of the xml export file ");
            System.out.println("    Parameter 2: The URL of the Web Service (optional)");
            System.out.println();
        } else {

            xmlFileName = args[0];

            if (args[1] != null && args[1].startsWith("http"))
                WSCLIENT_URL = args[1];     //Use the WS URL provided by the user

            logger.info("Using WS endpoint '" + WSCLIENT_URL + "'. Starting to export XML file '" +xmlFileName + "'");
            writeFile(xmlFileName, WSCLIENT_URL);

        }
    }


    public static boolean writeFile(String fileName, String wsClientURL) throws Exception {
        try {

            initParams(fileName, wsClientURL);
            // create the root element node
            setRootXmlElements();

            //Loop thorough the studies returned from the WS
            Element entries = doc.createElement(STUDIES);
            addStudies(entries);
            addCompounds(entries);

            writeDocument(doc);
            return true;
        } catch (Exception e) {
            logger.error("Could not create XML document "+fileName);
            System.out.println("Could not create XML document "+fileName);
            return false;
        }
    }

    public static void addCompounds(Element entries){
        //First, add the surrounding <entries> tags

        int numberofcompounds = allCompounds.length;
        //Add all the public studies
        int i =0;
        ArrayList<String> failedCompounds = new ArrayList<String>();
        for (String compoundAcc : allCompounds){

            try {
                System.out.println("Processing compound " + compoundAcc);
                logger.info("Processing Compound " + compoundAcc);

                //TODO, add ontologies
                //TODO, add pubmed

                MetaboLightsCompound compound = getCompound(compoundAcc).getMc();

                Element entry = doc.createElement("entry");
                entry.setAttribute("id", compoundAcc);

                entry.appendChild(addGenericElement("name", compound.getName()));
                entry.appendChild(addGenericElement("description", compound.getDescription()));

                //Add the sub tree "additional_fields"
                Element additionalField = doc.createElement("additional_fields");


                additionalField.appendChild(createChildElement(FIELD, "inchi", compound.getInchi()));
                additionalField.appendChild(createChildElement(FIELD, "iupac", compound.getIupacNames()));
                additionalField.appendChild(createChildElement(FIELD, "formula", compound.getFormula()));



                Element crossreferences = doc.createElement("cross_references");
                try {
                    ArrayList<MetSpecies> metSpecies = compound.getMetSpecies();


                    ArrayList<String> StudyList = new ArrayList<>();


                    for (MetSpecies species : metSpecies) {
                        additionalField.appendChild(createChildElement(FIELD, "organism", species.getSpecies().getSpecies()));

                        if (species.getSpecies().getSpecies().equalsIgnoreCase("reference compound")) {

                        }
                        else {
                            if (species.getSpecies().getSpeciesMember() != null) {
                                additionalField.appendChild(createChildElement(FIELD, "organism_group", species.getSpecies().getSpeciesMember().getSpeciesGroup().getName()));
                            }
                        }


                        Element crossref =  createChildElement("ref",  species.getCrossReference().getAccession(), species.getCrossReference().getDb().getName());
                        crossreferences.appendChild(crossref);

                            if (species.getCrossReference().getDb().getName().equalsIgnoreCase("MTBLS")) {
                                if (StudyList.contains(species.getCrossReference().getAccession())) {
                                    System.out.println("exists");
                                } else {
                                    StudyList.add(species.getCrossReference().getAccession());
                                }

                            }
                    }

                    System.out.println(StudyList.size());
                    for (String study : StudyList) {
                        additionalField.appendChild(createChildElement(FIELD, "study", study));
                    }


                }catch(Exception e){

                    System.out.println(e.getMessage());
                    failedCompounds.add(compoundAcc);

                }
                entry.appendChild(crossreferences);
                entry.appendChild(additionalField);
                //Add the complete study to the entry section
                entries.appendChild(entry);


                i++;


                System.out.println(String.valueOf(i) + " ------- " + String.valueOf(numberofcompounds - i) + "=====" + String.valueOf(failedCompounds.size()));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                failedCompounds.add(compoundAcc);
            }
            if ( i >250) {
                break;
            }
        }

        //Add the complete study list to the entries section
        doc.getDocumentElement().appendChild(entries);

        System.out.println("======================================");
        for (String failedCompound : failedCompounds){
            System.out.println(failedCompound);
        }
    }

    private static Compound getCompound(String accession){
        RestResponse<Compound> response = getWsClient().getCompound(accession);
        return response.getContent();
    }


    private static String getDateString(Date date){
        if (date == null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);

    }

    private static void setRootXmlElements(){
        // create the root element node
        Element database = doc.createElement("database");
        //element.setAttribute("id", "MetaboLights\" visible=\"true\" order=\"0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance");
        doc.appendChild(database);

        //Section for the standard headings
        createRootItemElement("name", "MetaboLights");
        createRootItemElement("description", "MetaboLights is a database for Metabolomics experiments and derived information");
        createRootItemElement("release", "2");
        createRootItemElement("release_date", getDateString(new Date()));
        createRootItemElement("entries_count", String.valueOf(allStudies.length + allCompounds.length));

    }

    private static String[] getStudiesList(){
        RestResponse<String[]> response = getWsClient().getAllStudyAcc();
        //return new String[]{"MTBLS1"};
        //return new String[]{"MTBLS1", "MTBLS2", "MTBLS3"};
        return response.getContent();
    }

    private static Study getStudy(String accession){
        RestResponse<Study> response = getWsClient().getStudy(accession);
        return response.getContent();
    }

    private static void addProtocols(Element entry, Study study){

        for (Protocol protocol : study.getProtocols()) {
            String protocolName = protocol.getName();
            String protocolDesc = protocol.getDescription();

            switch (protocolName){
                case "Sample collection": entry.appendChild(createChildElement(FIELD, "sample_protocol", protocolDesc)); break;
                case "Data transformation": entry.appendChild(createChildElement(FIELD, "data_protocol", protocolDesc)); break;
                case "Metabolite identification": entry.appendChild(createChildElement(FIELD, "metabolite_id_protocol", protocolDesc)); break;
                case "Extraction": entry.appendChild(createChildElement(FIELD, "extraction_protocol", protocolDesc)); break;
                case "Chromatography": entry.appendChild(createChildElement(FIELD, "chromatography_protocol", protocolDesc)); break;
                case "Mass spectrometry": entry.appendChild(createChildElement(FIELD, "mass_spec_protocol", protocolDesc)); break;
                case "NMR spectroscopy":  entry.appendChild(createChildElement(FIELD, "nmr_spec_protocol", protocolDesc)); break;
                case "NMR assay": entry.appendChild(createChildElement(FIELD, "nmr_assay_protocol", protocolDesc)); break;
                case "Derivatization": entry.appendChild(createChildElement(FIELD, "derivatization_protocol", protocolDesc)); break;
                case "Statistical analysis": entry.appendChild(createChildElement(FIELD, "statistical_protocol", protocolDesc)); break;
            }

        }

    }

    private static void addDates(Element dateFields, Study study){

        dateFields.appendChild(createChildElement(DATE, "submission", getDateString(study.getStudySubmissionDate())));
        dateFields.appendChild(createChildElement(DATE, "publication", getDateString(study.getStudyPublicReleaseDate())));

    }

    private static void addXrefs(Element crossRefs, Study study){
        List<String> xrefList = new ArrayList<>();


        for (int i = 0; i < study.getAssays().size(); i++) {
            i++;
            RestResponse<MetaboliteAssignment> response = getWsClient().getMetabolites(study.getStudyIdentifier(), i);

            if (response.getContent() != null) {
                MetaboliteAssignment maf = response.getContent();
                if (maf.getMetaboliteAssignmentLines() != null) {
                    for (MetaboliteAssignmentLine metaboliteAssignmentLine : maf.getMetaboliteAssignmentLines()) {
                        String dbId = metaboliteAssignmentLine.getDatabaseIdentifier();
                        String metName = metaboliteAssignmentLine.getMetaboliteIdentification();
                        dbId = dbId.trim(); //Get rid of spaces

                        //To avoid looping throught this data twice, populate the metabolite list here
                        if (hasValue(metName) && !getMetaboliteList().contains(metName))
                            metaboliteList.add(metName);

                        if (xrefList.contains(dbId) || !hasValue(dbId))
                            continue; //Jump out as we only want unique xrefs in the list

                        //Add this xref as it's the first time we see it in all assys for this study
                        xrefList.add(dbId);

                        if (dbId.startsWith("CHEBI:")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "ChEBI"));
                            crossRefs.appendChild(createChildElement(REF, dbId.replace("CHEBI:", "MTBLC"), "MetaboLights"));  //Cheeky assumption for now
                        } else if (dbId.startsWith("CID")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "PubChem"));
                        } else if (dbId.startsWith("HMDB")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "HMDB"));
                        } else if (dbId.startsWith("MTBLC")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "MetaboLights"));
                        } else if (dbId.startsWith("LM")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "LIPID MAPS"));
                        } else if (dbId.startsWith("C")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "KEGG"));
                        } else if (dbId.startsWith("GMD")) {
                            crossRefs.appendChild(createChildElement(REF, dbId, "GOLM"));
                        }


                    }
                }
            }
        }


    }


    private static void addStudies(Element entries){
        //First, add the surrounding <entries> tags


        //Add all the public studies
        for (String studyAcc : allStudies){
            try{

                System.out.println("Processing study " + studyAcc);
                logger.info("Processing study " + studyAcc);

                //TODO, add ontologies
                //TODO, add pubmed

                // Empty the metabolites list
                setMetaboliteList(new ArrayList<String>());

                Study study = getStudy(studyAcc);

                Element entry = doc.createElement("entry");
                entry.setAttribute("id", studyAcc);
                entry.appendChild(addGenericElement("name", study.getTitle()));
                entry.appendChild(addGenericElement("description", study.getDescription()));

                //Add the sub tree "cross_references"
                Element crossRefs = doc.createElement("cross_references");
                entry.appendChild(crossRefs);
                addXrefs(crossRefs, study);   //Add cross refs AND get the list of metabolites to add to additional_fields

                //Add the sub branch "dates"
                Element dateFields = doc.createElement("dates");
                entry.appendChild(dateFields);
                addDates(dateFields, study);

                //Add the sub tree "additional_fields"
                Element additionalField = doc.createElement("additional_fields");
                entry.appendChild(additionalField);

                additionalField.appendChild(createChildElement(FIELD, "repository", "MetaboLights"));
                additionalField.appendChild(createChildElement(FIELD, "omics_type", "Metabolomics"));

                //Add all protocols to the "additional_fields" tree
                addProtocols(additionalField, study);

                //Do not repeat values
                List<String> techList = new ArrayList<>();
                List<String> platformList = new ArrayList<>();
                for (Assay assay : study.getAssays()){

                    if (assay.getTechnology()!= null && !assay.getTechnology().isEmpty() && !techList.contains(assay.getTechnology())) {
                        techList.add(assay.getTechnology());
                        additionalField.appendChild(createChildElement(FIELD, "technology_type", assay.getTechnology()));
                    }

                    if (assay.getPlatform() != null && !assay.getPlatform().isEmpty() && !platformList.contains(assay.getPlatform())) {
                        platformList.add(assay.getPlatform());
                        additionalField.appendChild(createChildElement(FIELD, "instrument_platform", assay.getPlatform()));
                    }

                }

                additionalField.appendChild(createChildElement(FIELD, "disease", ""));  //We currently do not capture this in a concise way
                additionalField.appendChild(createChildElement(FIELD, "ptm_modification", ""));  //Proteins only?

                for (User user: study.getUsers()){
                    additionalField.appendChild(createChildElement(FIELD, "submitter", user.getFirstName() + " " + user.getLastName() ));
                    additionalField.appendChild(createChildElement(FIELD, "submitter_email", user.getEmail() ));
                    if (hasValue(user.getAffiliation()))
                        additionalField.appendChild(createChildElement(FIELD, "submitter_affiliation", user.getAffiliation() ));
                }

                additionalField.appendChild(createChildElement(FIELD, "curator_keywords", "")); //We do not capture this

                for (StudyDesignDescriptors studyDesignDescriptors : study.getDescriptors()) {
                    String studyDesc = studyDesignDescriptors.getDescription();

                    if (studyDesc.contains(":")) {
                        int i = 1;
                        for (String retval : studyDesignDescriptors.getDescription().split(":")) {
                            if (i == 2) //Get rid of ontology refs before ":"
                                additionalField.appendChild(createChildElement(FIELD, "study_design", retval));
                            i++;
                        }
                    } else {
                        additionalField.appendChild(createChildElement(FIELD, "study_design", studyDesc));
                    }
                }

                for (StudyFactor studyFactor : study.getFactors()) {
                    additionalField.appendChild(createChildElement(FIELD, "study_factor",studyFactor.getName()));
                }

                additionalField.appendChild(createChildElement(FIELD, "study_status", "Public"));
                additionalField.appendChild(createChildElement(FIELD, "full_dataset_link", ML_BASE_URL + "/" + studyAcc));     //TODO, all files should be listed
                additionalField.appendChild(createChildElement(FIELD, "dataset_file", ML_BASE_FTP + studyAcc));

                if (study.getOrganism() != null)
                    for (Organism organism : study.getOrganism()) {
                        if (hasValue(organism.getOrganismName()))
                            additionalField.appendChild(createChildElement(FIELD, "Organism", organism.getOrganismName().trim()));

                        if (hasValue(organism.getOrganismPart()))
                            additionalField.appendChild(createChildElement(FIELD, "Organism Part", organism.getOrganismPart().trim()));
                    }

                for (Publication publication : study.getPublications()) {
                    String completePublications = composePublications(publication);

                    if (hasValue(completePublications))
                        additionalField.appendChild(createChildElement(FIELD, "publication", completePublications));
                }

                //Add the metabolites from the the xrefs loop to the additional_fields sub-tree
                for (String met : metaboliteList) {
                    additionalField.appendChild(createChildElement(FIELD, "metabolite_name", met));
                }

                //Add the complete study to the entry section
                entries.appendChild(entry);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    private static String composePublications(Publication publication){
        String completePublication = null;
        String sep = ".";

        if (hasValue(publication.getTitle()))
            completePublication = publication.getTitle().trim();

        if (hasValue(completePublication) && !completePublication.endsWith(sep))
            completePublication = completePublication + sep;

        if (hasValue(publication.getDoi()))
            completePublication = completePublication + " " + publication.getDoi().replaceAll("http://","").replaceFirst("dx.", "");

        if (hasValue(completePublication) && !completePublication.endsWith(sep))
            completePublication = completePublication + sep;

        if (hasValue(publication.getPubmedId()))
            completePublication = completePublication + " " + "PMID:"+publication.getPubmedId();

        if (hasValue(completePublication))
            completePublication = completePublication.trim();

        return completePublication;
    }


    private static Element addGenericElement(String itemName, String itemValue) {

        Element genericElement = doc.createElement(itemName);

        // add an attribute to the node
        genericElement.setTextContent(itemValue);

        return genericElement;

    }


    private static void createRootItemElement(String itemName, String itemValue){
        Element itemElement = doc.createElement(itemName);

        // add an attribute to the node
        itemElement.setTextContent(itemValue);

        if (doc.getDocumentElement() != null) {    //There is a parent element
            doc.getDocumentElement().appendChild(itemElement);
        } else {
            doc.appendChild(itemElement);
        }
    }

    private static Element createChildElement(String elementType, String attributeValue, String attributeText){

        // add element after the last child of the root element
        Element element = doc.createElement(elementType);

        // add an attribute to the node
        if (elementType.equals(FIELD)) {
            element.setAttribute("name", attributeValue);
            //element.setAttribute("type", "text");
            element.setTextContent(attributeText);
            //} else if (elementType.equals(DBKEY)){
            //    element.setAttribute(DBKEY, attributeValue);
        } else if (elementType.equals(REF)){
            element.setAttribute("dbkey", attributeValue);
            element.setAttribute("dbname", attributeText);
        } else if (elementType.equals(DATE)) {
            element.setAttribute("type", attributeValue);
            element.setAttribute("value", attributeText);
        }

        return element;

    }

    private static void writeDocument(Document xml) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(new DOMSource(xml), new StreamResult(new File(xmlFileName)));
    }

    private static boolean validateParams(String args[]){

        // If there isn't any parameter
        if (args == null || args.length == 0)
            return false;

        if (args[0] == null || args[0].equals("") || !args[0].endsWith(".xml") || !args[0].contains(File.separator)){
            System.out.println("ERROR: You must provide a fully qualified filename with extension '.xml'");
            System.out.println("----");
            return false;
        }

        if (args.length > 1)
            if (!args[1].startsWith("http") || args[0].equals("")){
                System.out.println("ERROR: You must provide a fully qualified URL for the WebService, starting with 'http'");
                System.out.println("----");
                return false;
            }

        return true;

    }

}
