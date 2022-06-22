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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

    private final static String STUDIES = "entries";
    private final static String FIELD = "field";
    private final static String REF = "ref";
    private final static String DATE = "date";
    private final static String ML_BASE_URL = "https://www.ebi.ac.uk/metabolights";
    private final static String ML_BASE_FTP = "ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/";
    private final static String ML_BASE_FTP_DIR = "/ebi/ftp/pub/databases/metabolights/studies/public/";
    private final static String ML_BASE_FTP_DIR_CODON = "/nfs/ftp/public/databases/metabolights/studies/public";
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

    private static void initParams(String fileName, String wsClientURL, Boolean includeCompounds){
        if (xmlFileName == null)
            xmlFileName = fileName;

        if (wsClientURL != null)        //To override the WS URL if passed at startup
            WSCLIENT_URL = wsClientURL;

        validateParams(new String[]{xmlFileName, wsClientURL});

        doc = getDoc();                 //Set up builder, parser and document
        allStudies = getAllStudies();

        if (includeCompounds)
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
        //return new String[]{"MTBLC64889"};
        //return new String[]{"MTBLC66662","MTBLC64889"};
        return response.getContent();
    }

    private static String[] getStudiesList(){
        RestResponse<String[]> response = getWsClient().getAllStudyAcc();
        // return new String[]{"MTBLS291"};
        //return new String[]{"MTBLS124"};
        return response.getContent();
    }

    private static boolean validateParams(String args[]){

        // If there isn't any parameter
        if (args == null || args.length == 0)
            return false;

        int paramLength = args.length;
        if (paramLength < 4)
            return false;

        String s0 = args[0];
        String s1 = args[1].toLowerCase();
        String s2 = args[2].toLowerCase();
        String s3 = args[3];

        System.out.println("Params: " + s0 + ":" + s1 + ":" + s2 + ":" +s3);

        if (s0 != null && (s0.equals("") || !s0.endsWith(".xml") || !s0.contains(File.separator))){
            System.out.println("ERROR param 1: You must provide a fully qualified filename with extension '.xml'");
            System.out.println("----");
            return false;
        }

        if (!s1.matches("[yn]*")){
            System.out.println("ERROR param 2: Yes or No are indicated by 'y', 'n', 'Y' or 'N'. Not sure what you mean by "+args[1]);
            System.out.println("----");
            return false;
        }

        if (!s2.matches("[yn]*")){
            System.out.println("ERROR param 3: Yes or No are indicated by 'y', 'n', 'Y' or 'N'. Not sure what you mean by "+args[2]);
            System.out.println("----");
            return false;
        }

        if (!s3.startsWith("http") || s3.equals("")){
            System.out.println("ERROR param 4: You must provide a fully qualified URL for the WebService, starting with 'http'. Not sure what you mean by "+args[3]);
            System.out.println("----");
            return false;
        }

        return true;

    }


    public static void main(String[] args) throws Exception {
        // For testing Locally ..
        //String args1[] = {"/Users/famaladoss/Work/temp/studies.xml", "n", "n", "http://wp-p3s-15:8070/metabolights/webservice/"};
        // String args2[] = {"/Users/famaladoss/Work/temp/compounds.xml", "y", "n", "http://wp-p3s-15:8070/metabolights/webservice/"};
        // args = args1;
        if (!validateParams(args)){
            System.out.println("Usage:");
            System.out.println("    Parameter 1: The name of the xml export file (Mandatory)");
            System.out.println("    Parameter 2: Include metabolites in the export file (y/n)");
            System.out.println("    Parameter 3: Include detailed author information in the export file (y/n)");
            System.out.println("    Parameter 4: The URL of the MetaboLights Web Service");
            System.out.println();
        } else {

            xmlFileName = args[0];
            Boolean includeCompounds = false, detailedAuthors = false;

            if (args[1] != null && args[1].equalsIgnoreCase("y"))
                includeCompounds = true;

            if (args[2] != null && args[2].equalsIgnoreCase("y"))
                detailedAuthors = true;

                if (args[3] != null && args[3].startsWith("http"))
                    WSCLIENT_URL = args[3];     //Use the WS URL provided by the user

            System.out.println("Using WS endpoint '" + WSCLIENT_URL + "'. Starting to export XML file '" +xmlFileName + "'");
            writeFile(xmlFileName, includeCompounds, detailedAuthors, WSCLIENT_URL);

        }
    }


    public static boolean writeFile(String fileName, Boolean includeCompounds, Boolean detailedTags, String wsClientURL) throws Exception {
        try {

            initParams(fileName, wsClientURL, includeCompounds);

            //Loop thorough the studies returned from the WS
            Element entries = doc.createElement(STUDIES);
            addStudies(entries, detailedTags);

            if (includeCompounds)
                addCompounds(entries);

            // create the root element node
            setRootXmlElements(includeCompounds, entries);

            //Add the complete study list to the entries section
            doc.getDocumentElement().appendChild(entries);
            writeDocument(doc);
            return true;
        } catch (Exception e) {
            System.out.println("Could not create XML document " + fileName);
            return false;
        }
    }

    public static void addCompounds(Element entries){
        //First, add the surrounding <entries> tags

        int numberofcompounds = allCompounds.length;
        System.out.println("Total number of compounds : " + numberofcompounds);
        //Add all Compounds
        int i = 0;
        int j = 0;
        ArrayList<String> failedCompounds = new ArrayList<>();
       for (String compoundAcc : allCompounds){
           i++;
           System.out.println("Processing compound " + compoundAcc);
           Element entry = processCompoundToEntry(compoundAcc);
           if(entry != null){
                //Add the complete Compound to the entry section
                entries.appendChild(entry);
                j++;
           }else{
                System.out.println("Adding to the Failed Compound List !");
                failedCompounds.add(compoundAcc);
           }
        }
        System.out.println( " Entry count : " + entries.getChildNodes().getLength());
        //Add the complete study list to the entries section
        //doc.getDocumentElement().appendChild(entries);        //Moved the calling method

        if (failedCompounds != null) {
            System.out.println("======================================");
            System.out.println("List of failed compound(s)" +failedCompounds.size());
            for (String failedCompound : failedCompounds) {
                System.out.println("Retrying failed compound : ");
                System.out.println(failedCompound);
                Element entry1 = processCompoundToEntry(failedCompound);
                if(entry1 != null) {
                    //Add the complete study to the entry section
                    entries.appendChild(entry1);
                    j++;
                    System.out.println("Added the Compound " + j);
                }else{
                    System.out.println("Retrying failed !");
                }
            }
        }
    }

    private static Element processCompoundToEntry(String compoundAcc){
        //TODO, add ontologies

        MetaboLightsCompound compound = null;

        Compound comp_response = getCompound(compoundAcc);
        if (comp_response != null){
            compound = getCompound(compoundAcc).getMc();
        }

        if (compound == null) {
            System.out.println("Could not find compound " + compoundAcc);
            return null;
        } else {
            System.out.println("Got the  compound !");
            Element entry = doc.createElement("entry");
            entry.setAttribute("id", compoundAcc);

            if (compound.getName() != null)
                entry.appendChild(addGenericElement("name", compound.getName()));
            if (compound.getDescription() != null)
                entry.appendChild(addGenericElement("description", compound.getDescription()));

            //Add the sub tree "additional_fields"
            Element additionalField = doc.createElement("additional_fields");

            if (compound.getInchi() != null)
                additionalField.appendChild(createChildElement(FIELD, "inchi", compound.getInchi()));

            if (compound.getIupacNames() != null)
                additionalField.appendChild(createChildElement(FIELD, "iupac", compound.getIupacNames()));

            if (compound.getFormula() != null)
                additionalField.appendChild(createChildElement(FIELD, "formula", compound.getFormula()));

            Element crossreferences = doc.createElement("cross_references");
            try {

                if (compound.getMetSpecies() != null) {
                    ArrayList<MetSpecies> metSpecies = compound.getMetSpecies();
                    ArrayList<String> StudyList = new ArrayList<>();
                    for (MetSpecies species : metSpecies) {
                        if (species.getSpecies() != null && species.getSpecies().getSpecies() != null) {
                            additionalField.appendChild(createChildElement(FIELD, "organism", species.getSpecies().getSpecies()));

                            if (species.getSpecies().getSpecies().equalsIgnoreCase("reference compound")) {
                                //TODO, complete this....
                            } else {
                                if (species.getSpecies().getSpeciesMember() != null) {
                                    additionalField.appendChild(createChildElement(FIELD, "organism_group", species.getSpecies().getSpeciesMember().getSpeciesGroup().getName()));
                                }
                            }
                        }

                        if (species.getCrossReference() != null) {
                            Element crossref = createChildElement("ref", species.getCrossReference().getAccession(), species.getCrossReference().getDb().getName());
                            crossreferences.appendChild(crossref);

                            if (species.getCrossReference().getDb().getName().equalsIgnoreCase("MTBLS")) {
                                if (StudyList.contains(species.getCrossReference().getAccession())) {
                                    System.out.println("exists");
                                } else {
                                    StudyList.add(species.getCrossReference().getAccession());
                                }
                            }
                        }
                    }

                    for (String study : StudyList) {
                        additionalField.appendChild(createChildElement(FIELD, "study", study));
                    }
                }

                Element chebiCrossRef = createChildElement(REF, compound.getChebiId(), "ChEBI");
                crossreferences.appendChild(chebiCrossRef);

                entry.appendChild(getElementWithUniqueChildElements(crossreferences));
                entry.appendChild(additionalField);
                return entry;

            } catch (Exception e) {
                System.out.println("Error While Processing Compound : " + e.getMessage());
                return null;
            }
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

    private static void setRootXmlElements(Boolean includeCompounds, Element entries){
        // create the root element node
        Element database = doc.createElement("database");
        //element.setAttribute("id", "MetaboLights\" visible=\"true\" order=\"0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance");
        doc.appendChild(database);

        int exportLength = allStudies.length;
        if (includeCompounds)
            exportLength = exportLength + allCompounds.length;

        //Section for the standard headings
        createRootItemElement("name", "MetaboLights");
        createRootItemElement("description", "MetaboLights is a database for Metabolomics experiments and derived information");
        createRootItemElement("release", "4");
        createRootItemElement("release_date", getDateString(new Date()));
        createRootItemElement("entry_count", String.valueOf( + entries.getChildNodes().getLength()));

    }

    private static Study getStudy(String accession){
        RestResponse<Study> response = getWsClient().getStudy(accession);
        return response.getContent();
    }

    private static String tidyProtocolName(String protocolName){

        protocolName = protocolName.toLowerCase();
        protocolName = protocolName.trim();

        if (protocolName.equalsIgnoreCase("derivatization") || protocolName.equalsIgnoreCase("chemical derivatization"))
            protocolName = "derivatisation";

        if (protocolName.equalsIgnoreCase("normalization"))
            protocolName = "normalisation";

        if (protocolName.equalsIgnoreCase("nmr sample") || protocolName.equalsIgnoreCase("ms sample") || protocolName.equalsIgnoreCase("sample"))
            protocolName = "sample collection";

        if (protocolName.equalsIgnoreCase("fia") || protocolName.equalsIgnoreCase("flow injection"))
            protocolName = "flow injection analysis";

        if (protocolName.equalsIgnoreCase("installation"))
            protocolName = "software";

        if (protocolName.equalsIgnoreCase("analysis"))
            protocolName = "data analysis";

        if (protocolName.equalsIgnoreCase("metabolite identification"))
            protocolName = "metabolite id";

        if (protocolName.equalsIgnoreCase("nmr spectroscopy"))
            protocolName = "nmr spec";

        if (protocolName.equalsIgnoreCase("software"))
            protocolName = "software processing";

        protocolName = protocolName.replaceAll("/","_");
        protocolName = protocolName.replaceAll("-","_");

        if (protocolName.equalsIgnoreCase("gc_ms derivatization"))
            protocolName = "derivatisation";

        if (protocolName.contains("mass spectrometry"))
            protocolName = "mass spec";

        if (protocolName.contains("chromatography"))
            protocolName = "chromatography";

        protocolName = protocolName.replaceAll(" ","_");
        protocolName = protocolName.replaceAll(",","_");
        protocolName = protocolName.replaceAll("__","_");
        protocolName = protocolName.replace("\\&amp;","_");
        protocolName = protocolName.replaceAll("\\s+","");

        return protocolName +"_protocol";
    }

    private static void addProtocols(Element entry, Study study){

        for (Protocol protocol : study.getProtocols()) {
            String protocolName = protocol.getName();
            String protocolDesc = tidyNonPrintChars(protocol.getDescription(), "Protocol "+protocolName);
            entry.appendChild(createChildElement(FIELD, tidyProtocolName(protocolName), protocolDesc));
        }

    }

    private static void addDates(Element dateFields, Study study){
        dateFields.appendChild(createChildElement(DATE, "submission", getDateString(study.getStudySubmissionDate())));
        dateFields.appendChild(createChildElement(DATE, "publication", getDateString(study.getStudyPublicReleaseDate())));
    }

    private static void addXrefs(Element crossRefs, Study study){
        List<String> xrefList = new ArrayList<>();

        //PubMed
        for (Publication publication : study.getPublications()) {
            if (hasValue(publication.getPubmedId()))
                crossRefs.appendChild(createChildElement(REF, publication.getPubmedId(), "pubmed"));
        }

        for (int i = 0; i < study.getAssays().size(); i++) {
            i++;
            RestResponse<MetaboliteAssignment> response = getWsClient().getMetabolites(study.getStudyIdentifier(), i);

            if (response.getContent() != null) {
                MetaboliteAssignment maf = response.getContent();
                if (maf.getMetaboliteAssignmentLines() != null) {
                    for (MetaboliteAssignmentLine metaboliteAssignmentLine : maf.getMetaboliteAssignmentLines()) {
                        String dbId = metaboliteAssignmentLine.getDatabaseIdentifier();
                        String metName = metaboliteAssignmentLine.getMetaboliteIdentification();
                        metName = tidyNonPrintChars(metName, "Metabolite name: ");
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

        for (User user: study.getUsers()){
            if (hasValue(user.getOrcid()))
                crossRefs.appendChild(createChildElement(REF, user.getOrcid(), "ORCID"));
        }
   }

    private static String tidyNonPrintChars(String s, String description){
        String clean = s.replaceAll("\\p{C}", "").
                replaceAll("[^\\u0009\\u000a\\u000d\\u0020-\\uD7FF\\uE000-\\uFFFD]", "");

        if (clean != s) {
            return clean;
        }
        return s;
    }

    private static String tidyAutors(Contact contact){

        String completeAuthor = "";

        if (contact == null)
            return completeAuthor;

        String name = contact.getFirstName() + " " + contact.getLastName();
        String affiliation = contact.getAffiliation();
        String address = contact.getAddress();
        String email = contact.getEmail();
        String phone = contact.getPhone();


        if (hasValue(name))
            completeAuthor =  name + ". ";

        if (hasValue(affiliation))
            completeAuthor = completeAuthor + affiliation + ". ";

        if (hasValue(address))
            completeAuthor = completeAuthor + address + ". ";

        if (hasValue(email))
            completeAuthor = completeAuthor + contact.getEmail() + ". ";

        if (hasValue(phone))
            completeAuthor = completeAuthor + contact.getPhone() + ". ";

        return completeAuthor.trim();
    }

    private static Element addDetailedAuthors(Study study){
        //Add the sub tree "authorship"
        Element authorshipField = doc.createElement("authorship");
        for (Contact contact : study.getContacts()) {
            Element author = doc.createElement("author");

            String name = contact.getFirstName() + " " + contact.getLastName();
            String affiliation = contact.getAffiliation();
            String address = contact.getAddress();
            String email = contact.getEmail();
            String phone = contact.getPhone();

            if (hasValue(name))
                author.appendChild(addGenericElement("name", name));

            if (hasValue(affiliation))
                author.appendChild(addGenericElement("affiliation", affiliation));

            if (hasValue(address))
                author.appendChild(addGenericElement("address", address));

            if (hasValue(email))
                author.appendChild(addGenericElement("email", email));

            if (hasValue(phone))
                author.appendChild(addGenericElement("phone", phone));

            authorshipField.appendChild(author);

        }

        return authorshipField;

    }
    private static String getPathForFile(String studyId, String ftpPath){

        // Try the public folder
        File file = new File (ftpPath + studyId +"/");

        if (file.exists()) return file.getAbsolutePath();

        // File not found
        return "";

    }

    public static File[] getStudyFileList(String studyId, String ftpPath) {

        String studyFolders = getPathForFile(studyId, ftpPath);

        // If found
        if (!studyFolders.equals("")) {
            File studyFolder = new File(studyFolders);
            return studyFolder.listFiles();
        }

        return null;
    }

    private static void addStudies(Element entries, Boolean detailedTags){
        //First, add the surrounding <entries> tags
        System.out.println( "All Studies count " + allStudies.length);
        int j = 0;
        int k = 0;
        int null_title = 0;
        List<String> failedStudies = new ArrayList<>();

        //Add all the public studies
        for (String studyAcc : allStudies){
            try{
                j++;
                System.out.println("Processing study count " + j);
                System.out.println("Processing study acc : - " + studyAcc);

                //TODO, add ontologies

                // Empty the metabolites list
                setMetaboliteList(new ArrayList<String>());
                Element entry = processStudyToEntry(studyAcc, detailedTags);
                if(entry == null) {
                    null_title++;
                    System.out.println( "--Empty study--");
                    continue;
                }
                //Add the complete study to the entry section
                //  System.out.println("Adding study entry to the List ");
                entries.appendChild(entry);
                k++;
                System.out.println( "--- Adding the study;  Study count "+k);

            } catch (Exception e){
               System.out.println("Exception : " + e.getMessage());
               System.out.println( "--- Adding to the failed study list");
               failedStudies.add(studyAcc);
            }
        }
        System.out.println( " Iteration total " +j);
        System.out.println( " Added studies Total  " +k);
        System.out.println( " Failed studies   " +failedStudies.size());
        System.out.println( " Empty study count : " +null_title);
        System.out.println( " Entry count : " + entries.getChildNodes().getLength());
        if(failedStudies.size() > 0){
            System.out.println( "=== Retrying failed studies from Failed list ...");
            for (String studyAcc : failedStudies){
                try{
                    Element entry = processStudyToEntry(studyAcc, detailedTags);
                    if(entry != null) {
                        entries.appendChild(entry);
                        System.out.println( "Retry success for study : "+studyAcc);
                    }
                } catch (Exception e){
                    System.out.println("Exception : " + e.getMessage());
                    System.out.println( "---Retry failed for study : "+studyAcc);
                }
            }
            System.out.println( " Entry final count : " + entries.getChildNodes().getLength());
        }

    }

    private static Element processStudyToEntry(String studyAcc, Boolean detailedTags){
        Study study = getStudy(studyAcc);

        Element entry = doc.createElement("entry");
        entry.setAttribute("id", studyAcc);
        // System.out.println(" study.getTitle() "+study.getTitle());
        // System.out.println(" study.getDescription() "+study.getDescription());
        // System.out.println(" study.getStudyDescription() "+study.getStudyDescription());
        if(study.getTitle() == null){
            System.out.println( " #######Study content empty" );
            return null;
        }

        entry.appendChild(addGenericElement("name", tidyNonPrintChars(study.getTitle(),"Study title")));

        entry.appendChild(addGenericElement("description", tidyNonPrintChars(study.getDescription(),"Study description")));
        //Add the sub tree "cross_references"
        Element crossRefs = doc.createElement("cross_references");
        entry.appendChild(crossRefs);
        addXrefs(crossRefs, study);   //Add cross refs AND get the list of metabolites to add to additional_fields
        //Add the sub branch "dates"
        Element dateFields = doc.createElement("dates");
        entry.appendChild(dateFields);
        addDates(dateFields, study);
        if (detailedTags) { // Required for ThomsonReuters feed
            entry.appendChild(addDetailedAuthors(study));
            entry.appendChild(addStucturedPublicaitons(study));
        }

        //Add the sub tree "additional_fields"
        Element additionalField = doc.createElement("additional_fields");
        entry.appendChild(additionalField);

        additionalField.appendChild(createChildElement(FIELD, "repository", "MetaboLights"));
        additionalField.appendChild(createChildElement(FIELD, "omics_type", "Metabolomics"));

        if (!detailedTags){ // Standard author feed for EBI, DDI and MX
            for (Contact contact : study.getContacts()) {
                additionalField.appendChild(createChildElement(FIELD, "author",
                        tidyNonPrintChars(tidyAutors(contact), "Authors")));
            }
        }

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

            //TODO, MS: Autosampler model - Chromatography Instrument - Instrument
            //TODO, NMR: Autosampler model - Instrument

        }

        //List of filenames

        File[] files = getStudyFileList(studyAcc, ML_BASE_FTP_DIR_CODON);

        if (files != null) {
            for (File file : files) {
                String filename = file.toString().replaceAll(ML_BASE_FTP_DIR_CODON, ML_BASE_FTP);

                if (filename != null)
                    additionalField.appendChild(createChildElement(FIELD, "dataset_file", filename));

            }
        }

        additionalField.appendChild(createChildElement(FIELD, "disease", ""));  //We currently do not capture this in a concise way
        additionalField.appendChild(createChildElement(FIELD, "ptm_modification", ""));  //Proteins only?

        for (User user: study.getUsers()){
            additionalField.appendChild(createChildElement(FIELD, "submitter", tidyNonPrintChars(user.getFirstName() + " " + user.getLastName(),"Submitter name" )));
            additionalField.appendChild(createChildElement(FIELD, "submitter_email", user.getEmail() ));
            if (hasValue(user.getAffiliation()))
                additionalField.appendChild(createChildElement(FIELD, "submitter_affiliation", user.getAffiliation() ));
        }

        for (StudyDesignDescriptors studyDesignDescriptors : study.getDescriptors()) {
            String studyDesc = studyDesignDescriptors.getDescription();

            if (studyDesc.contains(":")) {
                int i = 1;
                for (String retval : studyDesignDescriptors.getDescription().split(":")) {
                    if (i == 2) { //Get rid of ontology refs before ":"
                        additionalField.appendChild(createChildElement(FIELD, "study_design", retval));
                        additionalField.appendChild(createChildElement(FIELD, "curator_keywords", retval)); //We do not capture this
                    }
                    i++;
                }
            } else {
                additionalField.appendChild(createChildElement(FIELD, "study_design", studyDesc));
                additionalField.appendChild(createChildElement(FIELD, "curator_keywords", studyDesc)); //We do not capture this
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

        if (!detailedTags) //Do not need a structured set of fields for Publication
            for (Publication publication : study.getPublications()) {
                String completePublications = composePublications(publication);

                if (hasValue(completePublications))
                    additionalField.appendChild(createChildElement(FIELD, "publication", completePublications));
            }

        //Add the metabolites from the the xrefs loop to the additional_fields sub-tree
        for (String met : metaboliteList) {
            if (met.length() <= 8191) {   //Lucene cannot index more than 8191 in any given field
                additionalField.appendChild(createChildElement(FIELD, "metabolite_name", met));
            } else {
                System.out.println("Metabolite name to long, ignoring ");
            }

        }
        return entry;
    }

    private static String tidyDoi(String doi){
        String tidyStr = doi.toLowerCase();

        tidyStr = tidyStr.replaceAll("https://","").replaceAll("http://","").replaceFirst("dx.", "");
        tidyStr = tidyStr.replaceAll("doi.org/","");
        tidyStr = tidyStr.replaceAll("doi:","");
        tidyStr = tidyStr.replaceAll("na","");
        tidyStr = tidyStr.replaceAll("n/a","");

        return tidyStr.trim();
    }

    private static String tidyPubmed(String pubmed){
        String tidyStr = pubmed.toLowerCase();

        tidyStr = tidyStr.replaceAll("none","");
        tidyStr = tidyStr.replaceAll("na","");
        tidyStr = tidyStr.replaceAll("n/a","");

        return tidyStr.trim();
    }

    private static String composePublications(Publication publication){
        String completePublication = null;
        String sep = ".";

        String pubmed = tidyPubmed(publication.getPubmedId());
        String doi = tidyDoi(publication.getDoi());

        if (hasValue(publication.getTitle()))
            completePublication = publication.getTitle().trim();

//        assert completePublication != null;
        if (hasValue(completePublication) && !completePublication.endsWith(sep))
            completePublication = completePublication + sep;

        if (hasValue(doi))
            completePublication = completePublication + " " + doi;

        if (hasValue(completePublication) && !completePublication.endsWith(sep))
            completePublication = completePublication + sep;

        if (hasValue(pubmed))
            completePublication = completePublication + " " + "PMID:"+pubmed;

        if (hasValue(completePublication))
            completePublication = completePublication.trim();

        return completePublication;
    }

    private static Element addStucturedPublicaitons(Study study){
        Element publicationsElement = doc.createElement("publications");


        for (Publication publication : study.getPublications()) {
            Element publicationElement = doc.createElement("publication");
            String title = publication.getTitle();
            String doi = tidyDoi(publication.getDoi());
            String pubmed = tidyPubmed(publication.getPubmedId());

            if (hasValue(title))
                publicationElement.appendChild(addGenericElement("title", title));

            if (hasValue(doi))
                publicationElement.appendChild(addGenericElement("doi", doi));

            if (hasValue(pubmed))
                publicationElement.appendChild(addGenericElement("pubmed", "PMID:" + pubmed));


            publicationsElement.appendChild(publicationElement);
        }

        return publicationsElement;


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

    private static Element getElementWithUniqueChildElements(Element parent){
        NodeList childNodes = parent.getChildNodes();

        Node parentClone = parent.cloneNode(true);

        while (parentClone.hasChildNodes())
            parentClone.removeChild(parentClone.getFirstChild());

        ArrayList<String> uniqueCrossRefs = new ArrayList<>();
        ArrayList<Integer> duplicateNodeIndexes = new ArrayList<>();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);
            String dbKey = currentNode.getAttributes().getNamedItem("dbkey").getNodeValue();
            if(uniqueCrossRefs.indexOf(dbKey) < 0){
                parentClone.appendChild(currentNode);
            }
        }

        return (Element) parentClone;
    }

    private static Element createChildElement(String elementType, String attributeValue, String attributeText){

        // add element after the last child of the root element
        Element element = doc.createElement(elementType);


        attributeValue = attributeValue.trim();

        // add an attribute to the node
        switch (elementType) {
            case FIELD:
                element.setAttribute("name", attributeValue);
                //element.setAttribute("type", "text");
                element.setTextContent(attributeText);
                //} else if (elementType.equals(DBKEY)){
                //    element.setAttribute(DBKEY, attributeValue);
                break;
            case REF:
                element.setAttribute("dbkey", attributeValue);
                element.setAttribute("dbname", attributeText);
                break;
            case DATE:
                element.setAttribute("type", attributeValue);
                element.setAttribute("value", attributeText);
                break;
        }

        return element;

    }

    private static void writeDocument(Document xml) throws Exception {
        System.out.println("Setting up new XML document");
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        System.out.println("Writing XML document");
        transformer.transform(new DOMSource(xml), new StreamResult(new File(xmlFileName)));
    }

}
