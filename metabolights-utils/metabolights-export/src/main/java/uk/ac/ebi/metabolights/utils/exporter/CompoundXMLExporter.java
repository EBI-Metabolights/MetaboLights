/*
 *
 *   EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 *   Cheminformatics and Metabolism group
 *
 *   European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 *   Last modified: { date }
 *   Modified by:   Venkata Chandrasekhar Nainala
 *
 *   Copyright 2015 EMBL - European Bioinformatics Institute
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package uk.ac.ebi.metabolights.utils.exporter;

/**
 * Created by venkata on 11/09/2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;

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
import java.util.Collection;
import java.util.Date;

public class CompoundXMLExporter {

    private final static Logger logger = LoggerFactory.getLogger(CompoundXMLExporter.class.getName());
    private final static String COMPOUNDS = "compounds";
    private final static String FIELD = "field";
    private final static String REF = "ref";
    private final static String DATE = "date";
    private final static String ML_BASE_URL = "http://www.ebi.ac.uk/metabolights";
    private static String xmlFileName = null;
    private static DocumentBuilderFactory dbf = null;
    private static DocumentBuilder builder = null;
    private static Document doc = null;
    private static String[] allCompounds = null;
    private static String WSCLIENT_URL = ML_BASE_URL + "/webservice/";
    private static MetabolightsWsClient wsClient;

    public CompoundXMLExporter(){}

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
            //Loop thorough the compounds returned from the WS
            addCompounds(doc);
            writeDocument(doc);
            return true;
        } catch (Exception e) {
            logger.error("Could not create XML document "+fileName);
            System.out.println("Could not create XML document "+fileName);

            return false;
        }
    }

    private static void initParams(String fileName, String wsClientURL){
        if (xmlFileName == null)
            xmlFileName = fileName;

        if (wsClientURL != null)        //To override the WS URL if passed at startup
            WSCLIENT_URL = wsClientURL;

        validateParams(new String[]{xmlFileName,wsClientURL});

        doc = getDoc();                 //Set up builder, parser and document
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
        createRootItemElement("entry_count", String.valueOf(allCompounds.length));
    }

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


    public static MetabolightsWsClient getWsClient() {
        if (wsClient == null)
            wsClient = new MetabolightsWsClient(WSCLIENT_URL);
        return wsClient;
    }


    private static String getDateString(Date date){
        if (date == null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);

    }

    private static Compound getCompound(String accession){
        RestResponse<Compound> response = getWsClient().getCompound(accession);
        return response.getContent();
    }

    public static void addCompounds(Document doc_){
        //First, add the surrounding <entries> tags
        Element entries = doc_.createElement(COMPOUNDS);
        int numberofcompounds = allCompounds.length;
        //Add all the public studies
        int i =0;
        for (String compoundAcc : allCompounds){

                System.out.println("Processing compound " + compoundAcc);
                logger.info("Processing Compound " + compoundAcc);

                //TODO, add ontologies
                //TODO, add pubmed

                MetaboLightsCompound compound = getCompound(compoundAcc).getMc();

                Element entry = doc_.createElement("compound");
                entry.setAttribute("id", compoundAcc);

                //Add the sub tree "additional_fields"
                Element additionalField = doc_.createElement("additional_fields");
                entry.appendChild(additionalField);

                additionalField.appendChild(createChildElement( FIELD, "inchi", compound.getInchi()));
                additionalField.appendChild(createChildElement( FIELD, "iupac", compound.getIupacNames()));
                additionalField.appendChild(createChildElement( FIELD, "formula", compound.getFormula()));
                additionalField.appendChild(createChildElement( FIELD, "has_species", String.valueOf(compound.getHasSpecies())));
                System.out.println("here");
                ArrayList<MetSpecies> metSpecies = compound.getMetSpecies();
                Element metspec = doc_.createElement("MetSpecies");
                ArrayList<String> StudyList = new ArrayList<>();
                for (MetSpecies species : metSpecies) {
                    Element spec = doc_.createElement("Species");
                    spec.appendChild(createChildElement( FIELD, "organism", species.getSpecies().getSpecies()));
                    spec.appendChild(createChildElement( FIELD, "organism_group", species.getSpecies().getSpeciesMember().getSpeciesGroup().getName()));
                    spec.appendChild(createChildElement( FIELD, "CrossReference", species.getCrossReference().getAccession()));

                    metspec.appendChild(spec);

                    if (species.getCrossReference().getDb().getName().equalsIgnoreCase("MTBLS")) {
                        System.out.println(species.getCrossReference().getDb().getName());
                        StudyList.add(species.getCrossReference().getAccession());
                    }
                }


                Element studiesAssoc = doc_.createElement("Studies");
                for(String study: StudyList){
                    studiesAssoc.appendChild(createChildElement(FIELD, "study", study));
                }
                additionalField.appendChild(metspec);
                additionalField.appendChild(studiesAssoc);
                //Add the complete study to the entry section
                entries.appendChild(entry);


            i++;
            System.out.println(String.valueOf(i) + " ------- " +  String.valueOf(numberofcompounds - i));
            break;
        }

        //Add the complete study list to the entries section
        doc_.getDocumentElement().appendChild(entries);
    }


    private static void addCompounds(){
        //First, add the surrounding <entries> tags
        Element entries = doc.createElement(COMPOUNDS);

        //Add all the public studies
        int i =0;
        for (String compoundAcc : allCompounds){
            try {
                System.out.println("Processing compound " + compoundAcc);
                logger.info("Processing study " + compoundAcc);

                //TODO, add ontologies
                //TODO, add pubmed

                MetaboLightsCompound compound = getCompound(compoundAcc).getMc();

                Element entry = doc.createElement("compound");
                entry.setAttribute("id", compoundAcc);

                //Add the sub tree "additional_fields"
                Element additionalField = doc.createElement("additional_fields");
                entry.appendChild(additionalField);

                additionalField.appendChild(createChildElement( FIELD, "inchi", compound.getInchi()));
                additionalField.appendChild(createChildElement( FIELD, "iupac", compound.getIupacNames()));
                additionalField.appendChild(createChildElement( FIELD, "formula", compound.getFormula()));
                additionalField.appendChild(createChildElement( FIELD, "has_species", String.valueOf(compound.getHasSpecies())));

                ArrayList<MetSpecies> metSpecies = compound.getMetSpecies();
                Element metspec = doc.createElement("MetSpecies");
                ArrayList<String> StudyList = new ArrayList<>();
                for (MetSpecies species : metSpecies) {
                    metspec.appendChild(createChildElement( FIELD, "organism", species.getSpecies().getSpecies()));
                    metspec.appendChild(createChildElement( FIELD, "organism_group", species.getSpecies().getSpeciesMember().getSpeciesGroup().getName()));
                    metspec.appendChild(createChildElement( FIELD, "CrossReference", species.getCrossReference().getAccession()));

                    if (species.getCrossReference().getDb().getName().equalsIgnoreCase("MTBLS")) {
                        System.out.println(species.getCrossReference().getDb().getName());
                        StudyList.add(species.getCrossReference().getAccession());
                    }
                }
                additionalField.appendChild(metspec);
                System.out.println(StudyList.toString());
                additionalField.appendChild(createChildElement( FIELD, "StudyID", StudyList.toString()));

                //Add the complete study to the entry section
                entries.appendChild(entry);
            }catch (Exception e){
                System.out.println(e.toString());
            }
            i++;

            if(i > 500){
                break;
            }
        }

        //Add the complete study list to the entries section
        doc.getDocumentElement().appendChild(entries);

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

    private static Element createChildElement(Document doc, String elementType, String attributeValue, String attributeText){

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
