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
import java.util.ArrayList;
import java.util.List;

public class XMLExporter {

    private final static Logger logger = LoggerFactory.getLogger(XMLExporter.class.getName());
    private final static String DATABASE = "database";
    private final static String ENTRIES = "entries";
    private final static String FIELD = "field";
    private final static String DBKEY = "dbkey";
    private final static String XREF = "ref";
    private final static String ML_BASE_URL = "http://www.ebi.ac.uk/metabolights";
    private final static String ML_BASE_FTP = "ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/";
    private final static String STUDY_STATUS = "Public";
    private final static String CROSS_REFS = "cross_references";
    private final static String OMICS_TYPE = "Metabolomics";
    private static String xmlFileName = null;
    private static DocumentBuilderFactory dbf = null;
    private static DocumentBuilder builder = null;
    private static Document doc = null;
    private static String[] allStudies = null;
    private static String WSCLIENT_URL = ML_BASE_URL + "/webservice/";
    private static MetabolightsWsClient wsClient;

    public XMLExporter(){}

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

    public static Boolean hasValue(String str){
        if(str != null && !str.isEmpty())
            return true;
        return false;
    }

    private static void initParams(String fileName, String wsClientURL){
        if (xmlFileName == null)
            xmlFileName = fileName;

        if (wsClientURL != null)        //To override the WS URL if passed at startup
            WSCLIENT_URL = wsClientURL;

        validateParams(new String[]{xmlFileName,wsClientURL});

        doc = getDoc(); //Set up builder, parser and document
        allStudies = getAllStudies();
        wsClient = getWsClient();
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
            addStudies();

            writeDocument(doc);
            return true;
        } catch (Exception e) {
            logger.error("Could not create XML document "+fileName);
            System.out.println("Could not create XML document "+fileName);
            return false;
        }
    }

    private static void setRootXmlElements(){
        // create the root element node
        Element element = doc.createElement(DATABASE);
        element.setAttribute("id", "MetaboLights\" visible=\"true\" order=\"0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance");
        doc.appendChild(element);

        //Section for the standard headings
        createItemElement("repository-name", "MetaboLights");
        createItemElement("mainURL", ML_BASE_URL);
        createItemElement("summary", "MetaboLights is a database for Metabolomics experiments and derived information");
        createItemElement("entry_count", String.valueOf(allStudies.length));
    }

    private static String[] getStudiesList(){
        RestResponse<String[]> response = getWsClient().getAllStudyAcc();
        //return new String[]{"MTBLS114"}; //{"MTBLS1", "MTBLS2", "MTBLS3"};
        return response.getContent();
    }

    private static Study getStudy(String accession){
        RestResponse<Study> response = getWsClient().getStudy(accession);
        return response.getContent();
    }

    private static Element addProtocols(Element entry, Study study){

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

        return entry;
    }

    private static Element addXrefs(Integer numberOfAssays, Study study){
        Element crossRefs = doc.createElement(CROSS_REFS);              //TODO, add ontologies and pubmed id's as well
        List<String> xrefList = new ArrayList<>();
        for (int i = 0; i < numberOfAssays; i++) {
            i++;
            RestResponse<MetaboliteAssignment> response = getWsClient().getMetabolites(study.getStudyIdentifier(), i);

            if (response.getContent() != null){
                MetaboliteAssignment maf = response.getContent();
                if (maf.getMetaboliteAssignmentLines() != null) {
                    for (MetaboliteAssignmentLine metaboliteAssignmentLine : maf.getMetaboliteAssignmentLines()) {
                        String dbId = metaboliteAssignmentLine.getDatabaseIdentifier();
                        dbId = dbId.trim(); //Get rid of spaces

                        if (xrefList.contains(dbId))
                            break; //Jump out as we only want unique xrefs in the list

                        //Add this xref as it's the first time we see it in all assys for this study
                        xrefList.add(dbId);

                        if (dbId.startsWith("CHEBI:")) {
                            crossRefs.appendChild(createChildElement(XREF, dbId, "ChEBI"));
                            crossRefs.appendChild(createChildElement(XREF, dbId.replace("CHEBI:","MTBLC"), "MetaboLights"));  //Cheeky assumption for now
                        } else if (dbId.startsWith("CID"))
                            crossRefs.appendChild(createChildElement(XREF, dbId, "PubChem"));
                        else if (dbId.startsWith("HMDB"))
                            crossRefs.appendChild(createChildElement(XREF, dbId, "HMDB"));
                        else if (dbId.startsWith("MTBLC"))
                            crossRefs.appendChild(createChildElement(XREF, dbId, "MetaboLights"));
                        else if (dbId.startsWith("LM"))
                            crossRefs.appendChild(createChildElement(XREF, dbId, "LIPID MAPS"));
                        else if (dbId.startsWith("C"))
                            crossRefs.appendChild(createChildElement(XREF, dbId, "KEGG"));
                        else if (dbId.startsWith("GMD"))
                            crossRefs.appendChild(createChildElement(XREF, dbId, "GOLM"));
                        //else if (dbId != null && !dbId.isEmpty() && dbId.length() > 2)
                        //    crossRefs.appendChild(createChildElement(XREF, dbId, "OTHER"));

                    }
                }
            }
        }

        if (study.getOrganism() != null)
            for (Organism organism : study.getOrganism()) {
                if (hasValue(organism.getOrganismName()))
                    crossRefs.appendChild(createChildElement(XREF, organism.getOrganismName().trim(), "Organism"));

                if (hasValue(organism.getOrganismPart()))
                    crossRefs.appendChild(createChildElement(XREF, organism.getOrganismPart().trim(), "Organism Part"));
            }

       return crossRefs;
    }


    private static void addStudies(){
        //First, add the surrounding <entries> tags
        Element entries = doc.createElement(ENTRIES);

        //Add all the public studies
        for (String studyAcc : allStudies){
            System.out.println("Processing study " + studyAcc);
            logger.info("Processing study "+studyAcc);

            Integer numberOfAssays = 0;
            Study study = getStudy(studyAcc);

            Element entry = doc.createElement("entry");
            entry.setAttribute("id", studyAcc);
            entry.appendChild(createChildElement(FIELD, "name", study.getTitle()));
            entry.appendChild(createChildElement(FIELD, "description", study.getDescription()));
            entry.appendChild(createChildElement(FIELD, "omics_type", OMICS_TYPE));

            //Add all protocols
            entry = addProtocols(entry,study);

            //Do not repeat values
            List<String> techList = new ArrayList<>();
            List<String> platformList = new ArrayList<>();
            for (Assay assay : study.getAssays()){

                if (assay.getTechnology()!= null && !assay.getTechnology().isEmpty() && !techList.contains(assay.getTechnology())) {
                    techList.add(assay.getTechnology());
                    entry.appendChild(createChildElement(FIELD, "technology_type", assay.getTechnology()));
                }

                if (assay.getPlatform() != null && !assay.getPlatform().isEmpty() && !platformList.contains(assay.getPlatform())) {
                    platformList.add(assay.getPlatform());
                    entry.appendChild(createChildElement(FIELD, "instrument_platform", assay.getPlatform()));
                }

                numberOfAssays++;
            }

            entry.appendChild(createChildElement(FIELD, "disease", ""));  //We currently do not capture this in a concise way
            entry.appendChild(createChildElement(FIELD, "ptm_modification", ""));  //Proteins only?

            for (User user: study.getUsers()){
                entry.appendChild(createChildElement(FIELD, "submitter", user.getFirstName() + " " + user.getLastName() ));
                entry.appendChild(createChildElement(FIELD, "submitter_email", user.getEmail() ));
                if (hasValue(user.getAffiliation()))
                    entry.appendChild(createChildElement(FIELD, "submitter_affiliation", user.getAffiliation() ));
            }

            entry.appendChild(createChildElement(FIELD, "curator_keywords", "")); //We do not capture this

            for (StudyDesignDescriptors studyDesignDescriptors : study.getDescriptors()) {
                String studyDesc = studyDesignDescriptors.getDescription();

                if (studyDesc.contains(":")) {
                    int i = 1;
                    for (String retval : studyDesignDescriptors.getDescription().split(":")) {
                        if (i == 2) //Get rid of ontology refs before ":"
                            entry.appendChild(createChildElement(FIELD, "study_design", retval));
                        i++;
                    }
                } else {
                    entry.appendChild(createChildElement(FIELD, "study_design", studyDesc));
                }
            }

            for (StudyFactor studyFactor : study.getFactors()) {
                entry.appendChild(createChildElement(FIELD, "study_factor",studyFactor.getName()));
            }

            entry.appendChild(createChildElement(FIELD, "study_status", STUDY_STATUS));
            entry.appendChild(createChildElement(FIELD, "full_dataset_link", ML_BASE_URL + "/" + studyAcc));     //TODO, all files should be listed
            entry.appendChild(createChildElement(FIELD, "dataset_file", ML_BASE_FTP + studyAcc));

            for (Publication publication : study.getPublications()) {
                String completePublications = composePublications(publication);

                if (hasValue(completePublications))
                    entry.appendChild(createChildElement(FIELD, "publication", completePublications));
            }

            //Add the cross references
            entry.appendChild(addXrefs(numberOfAssays, study));

            //Add the complete study to the entry section
            entries.appendChild(entry);
        }

        //Add the complete study list to the entries section
        doc.getDocumentElement().appendChild(entries);

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

    private static void createItemElement(String itemName, String itemValue){
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
            element.setAttribute("id", attributeValue);
            element.setAttribute("type", "text");
            element.setTextContent(attributeText);
        } else {
            element.setAttribute(DBKEY, attributeValue);
            element.setAttribute("dbname", attributeText);
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
