/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Metabolism team
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2018-Apr-04
 * Modified by:   kenneth
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
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
import java.util.HashMap;
import java.util.Map;


public class MetabolightsEuropePMCExporter {

    private final static String ML_BASE_URL = "https://www.ebi.ac.uk/metabolights";
    private static String xmlFileName = null;
    private static DocumentBuilderFactory dbf = null;
    private static DocumentBuilder builder = null;
    private static Document doc = null;
    private static String[] allStudies = null;
    private static String WSCLIENT_URL = ML_BASE_URL + "/webservice/";
    private static MetabolightsWsClient wsClient;
    private static String metaboLightsId = "1782";
    private static final String pmid = "PMID", doi = "DOI", totals = "totalNumber";
    private static Boolean hasPublication = false, hasPMID = false;
    private static Map<String, Integer> studyPubs = new HashMap();

    public MetabolightsEuropePMCExporter(){}

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
        return str != null && !str.isEmpty();
    }

    private static void initParams(String fileName, String wsClientURL){
        if (xmlFileName == null)
            xmlFileName = fileName;

        if (wsClientURL != null)        //To override the WS URL if passed at startup
            WSCLIENT_URL = wsClientURL;

        validateParams(new String[]{xmlFileName, wsClientURL});

        doc = getDoc();                 //Set up builder, parser and document
        allStudies = getAllStudies();

        wsClient = getWsClient();
    }

    private static String[] getStudiesList(){
        RestResponse<String[]> response = getWsClient().getAllStudyAcc();
        //return new String[]{"MTBLS43","MTBLS93","MTBLS124","MTBLS59","MTBLS146"};
        return response.getContent();
    }

    private static boolean validateParams(String args[]){

        // If there isn't any parameter
        if (args == null || args.length == 0)
            return false;

        if (args.length < 2) return false;

        String p1 = args[0], p2 = args[1];

        System.out.println("Params: " + p1 + ":" + p2);

        if (p1 != null && (p1.equals("") || !p1.endsWith(".xml") || !p1.contains(File.separator))){
            System.out.println("ERROR param 1: You must provide a fully qualified filename with extension '.xml'");
            System.out.println("----");
            return false;
        }

        if (!p2.startsWith("http") || p2.equals("")){
            System.out.println("ERROR param 2: You must provide a fully qualified URL for the WebService, starting with 'http'. Not sure what you mean by "+args[1]);
            System.out.println("----");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        /** Profile file must also exist in the ftp upload directory:
         * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
         * <providers>
         *  <provider>
         *    <id>1782</id>
         *    <resourceName>MetaboLights</resourceName>
         *    <description>SMetaboLights is a database for Metabolomics experiments and derived information/description>
         *    <email>metabolights-help@ebi.ac.uk</email>
         *  </provider>
         * </providers>
         */


        if (!validateParams(args)){
            System.out.println("Usage:");
            System.out.println("    Parameter 1: The name of the Europe PMC export xml export file (Mandatory)");
            System.out.println("    Parameter 2: The URL of the MetaboLights Web Service");
            System.out.println();
        } else {

            xmlFileName = args[0];

            if (args[1] != null && args[1].startsWith("http"))
                WSCLIENT_URL = args[1];     //Use the WS URL provided by the user

            System.out.println("Using WS endpoint '" + WSCLIENT_URL + "'. Starting to export XML file '" +xmlFileName + "'");
            writeFile(xmlFileName, WSCLIENT_URL);

        }
    }

    public static boolean writeFile(String fileName, String wsClientURL) {
        try {

            initParams(fileName, wsClientURL);

            // create the root element node
            Element entries = doc.createElement("links");

            //Loop thorough the studies returned from the WS
            addStudies(entries);

            //Add the complete study list to the entries section
            doc.appendChild(entries);

            writeDocument(doc);
            return true;
        } catch (Exception e) {
            System.out.println("Could not create XML document "+fileName);
            return false;
        }
    }

    private static Study getStudy(String accession){
        RestResponse<Study> response = getWsClient().getStudy(accession);
        return response.getContent();
    }

    private static String tidyNonPrintChars(String s, String description){
        String clean = s.replaceAll("\\p{C}", "");

        if (clean != s) {
            System.out.println("Replaced special character in: " + description);
            return clean;
        }
        return s;
    }

    private static void checkStudy(Study study){

        Integer pmids = 0, dois = 0;

        for (Publication publication : study.getPublications()) {
            if (checkPMIDPublication(publication)) {
                pmids++;
                hasPublication = true;
            }

            if (hasValue(publication.getDoi())) {
                dois++;
                hasPublication = true;
            }
        }

        if (dois > 0)
            studyPubs.put(doi, dois);

        if (pmids > 0)
            studyPubs.put(pmid, pmids);

        if (hasPublication)
            System.out.println(study.getStudyIdentifier() + " : has publications. PMIDs: " + pmids + ", DOIs: " + dois );

    }

    private static Boolean checkPMIDPublication(Publication publication){
        if (hasValue(publication.getPubmedId()))
            return true;

        return false;

    }


    private static void addStudies(Element entries){
        //First, add the surrounding <entries> tags

        //Add all the public studies
        for (String studyAcc : allStudies){
            try {
                hasPublication = false;
                hasPMID = false;
                Study study = getStudy(studyAcc);

                checkStudy(study); //Does this study have publications? And which type, doi and/or pmid?

                if (hasPublication) {

                    for (Publication publication : study.getPublications()) {
                        Element entry = doc.createElement("link");
                        entry.setAttribute("providerId", metaboLightsId);   //TODO, change to final id

                        //Add the sub tree "additional_fields"
                        Element additionalField = doc.createElement("resource");
                        entry.appendChild(additionalField);
                        additionalField.appendChild(addGenericElement("title", tidyNonPrintChars(studyAcc + ": " + study.getTitle(), "title")));
                        additionalField.appendChild(addGenericElement("url", ML_BASE_URL + "/" + studyAcc));


                        if (checkPMIDPublication(publication)) {   // If we have PubMed id, do not add doi
                            System.out.println(studyAcc + " : Adding PMID ");
                            //Add the sub tree "record"
                            Element recordField = doc.createElement("record");
                            entry.appendChild(recordField);
                            recordField.appendChild(addGenericElement("source", "MED"));
                            recordField.appendChild(addGenericElement("id", tidyPubmed(publication.getPubmedId())));
                        } else if (!checkPMIDPublication(publication)) {
                            if (!hasValue(publication.getDoi())) //Check that we really have a value for the doi
                                break;

                            System.out.println(studyAcc + " : Adding DOI ");
                            entry.appendChild(addGenericElement("doi", tidyDoi(publication.getDoi())));
                        }

                        //Add the complete study to the entry section when we have a publication
                        entries.appendChild(entry);
                    }
                }
                else
                    System.out.println(studyAcc + " : --- No publication, skipping ");

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


    private static Element addGenericElement(String itemName, String itemValue) {

        Element genericElement = doc.createElement(itemName);

        // add an attribute to the node
        genericElement.setTextContent(itemValue);

        return genericElement;

    }

    private static String tidyDoi(String doi){
        String tidyStr = doi.toLowerCase();
        tidyStr = tidyNonPrintChars(tidyStr, "doi");

        tidyStr = tidyStr.replaceAll("http://","").replaceFirst("dx.", "");
        tidyStr = tidyStr.replaceAll("doi.org/","");
        tidyStr = tidyStr.replaceAll("doi:","");
        tidyStr = tidyStr.replaceAll("na","");
        tidyStr = tidyStr.replaceAll("n/a","");

        return tidyStr.trim();
    }

    private static String tidyPubmed(String pubmed){
        String tidyStr = pubmed.toLowerCase();

        tidyStr = tidyNonPrintChars(tidyStr, "pubmed");
        tidyStr = tidyStr.replaceAll("none","");
        tidyStr = tidyStr.replaceAll("na","");
        tidyStr = tidyStr.replaceAll("n/a","");

        return tidyStr.trim();
    }

    private static void writeDocument(Document xml) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");

        xml.setXmlStandalone(true);
        transformer.transform(new DOMSource(xml), new StreamResult(new File(xmlFileName)));
    }


}
