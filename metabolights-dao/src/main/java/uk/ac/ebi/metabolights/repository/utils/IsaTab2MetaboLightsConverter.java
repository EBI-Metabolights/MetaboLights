/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 24/09/13 12:15
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.utils;

import org.apache.commons.collections15.OrderedMap;
import org.isatools.isacreator.configuration.FieldObject;
import org.isatools.isacreator.model.Factor;
import org.isatools.isacreator.model.StudyDesign;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.model.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 11:34
 */
public class IsaTab2MetaboLightsConverter {

    /*
    From isaTab specifications:
    "Dates
    Dates should be supplied in the ISO 8601 format “YYYY-MM-DD”."
    */
    private static final String ISA_TAB_DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat isaTabDateFormat = new SimpleDateFormat(ISA_TAB_DATE_FORMAT);

    private static final String ASSAY_COLUMN_SAMPLE_NAME = "Sample Name";
    private static final String METABOLITE_ASSIGNMENT_FILE = "Metabolite Assignment File";

    private static MzTabDAO mzTabDAO = new MzTabDAO();
    //Below are for sample tab
    private static final String SOURCE_NAME = "Source Name";
    private static final String CHARACTERISTICS_ORGANISM = "Characteristics[Organism]";
    private static final String CHARACTERISTICS_ORGANISM_PART = "Characteristics[Organism part]";
    private static final String PROTOCOL_REF = "Protocol REF";
    private static final String SAMPLE_NAME = "Sample Name";
    private static final String FACTOR = "Factor";


    public static Study convert( org.isatools.isacreator.model.Investigation investigation, String studyFolder, boolean includeMetabolites){

        // Convert the study from the ISAcreator model...
        Study metStudy = isaTabInvestigation2MetaboLightsStudy(investigation, studyFolder, includeMetabolites);

        // Convert the authors...
        return metStudy;

    }

    /**
     * Reads and maps the MAF to a MetaboliteAssignment class
     * @param fileName
     * @return MetaboliteAssignment with metabolite data
     */
    private static MetaboliteAssignment getMAF(String fileName){
        //Fully qualified file name, incl path!
        return mzTabDAO.mapMetaboliteAssignmentFile(fileName);
    }

    private static Study isaTabInvestigation2MetaboLightsStudy(org.isatools.isacreator.model.Investigation source, String studyFolder, boolean includeMetabolites){

        // Instantiate new MetaboLights investigation object
        Study metStudy = new Study();


        // Get the first and unique study
        org.isatools.isacreator.model.Study isaStudy = source.getStudies().values().iterator().next();

        // Populate direct study members
        metStudy.setStudyIdentifier(isaStudy.getStudyId());
        metStudy.setTitle(isaStudy.getStudyTitle());
        metStudy.setDescription(isaStudy.getStudyDesc());
        metStudy.setStudyPublicReleaseDate(isaTabDate2Date(isaStudy.getPublicReleaseDate()));
        metStudy.setStudySubmissionDate(isaTabDate2Date(isaStudy.getDateOfSubmission()));


        // Now collections
        // Contacts
        metStudy.setContacts(isaTabContacts2MetaboLightsContacts(isaStudy));

        // Study design descriptors
        metStudy.setDescriptors(isaTabStudyDesign2MetaboLightsStudiesDesignDescriptors(isaStudy));

        // Study factors
        metStudy.setFactors(isaTabStudyFactors2MetaboLightsStudyFactors(isaStudy));

        // Publications
        metStudy.setPublications(isaTabPublications2MetaboLightsPublications(isaStudy));

        // Protocols
        metStudy.setProtocols(isaTabProtocols2MetaboLightsProtocols(isaStudy));

        //Assays
        metStudy.setAssays(isaTabAssays2MetabolightsAssays(isaStudy, studyFolder, includeMetabolites));

        //Samples
        metStudy.setSamples(isaTabSamples2MetabolightsSamples((isaStudy)));


        return metStudy;
    }


    private static Collection<Contact> isaTabContacts2MetaboLightsContacts(org.isatools.isacreator.model.Study isaStudy){

        List<org.isatools.isacreator.model.Contact> isaContacts = isaStudy.getContacts();

        List<Contact> contacts = new LinkedList<Contact>();

        for (org.isatools.isacreator.model.Contact isaContact: isaContacts){
            Contact contact = new Contact();

            contact.setAddress(isaContact.getAddress());
            contact.setAffiliation((isaContact.getAffiliation()));
            contact.setEmail(isaContact.getEmail());
            contact.setFax(isaContact.getFax());
            contact.setFirstName(isaContact.getFirstName());
            contact.setLastName(isaContact.getLastName());
            contact.setMidInitial(isaContact.getMidInitial());
            contact.setPhone(isaContact.getPhone());
            contact.setRole(isaContact.getRole());

            contacts.add(contact);
        }


        return contacts;

    }

    private static Collection<StudyDesignDescriptors> isaTabStudyDesign2MetaboLightsStudiesDesignDescriptors(org.isatools.isacreator.model.Study isaStudy){

        List<StudyDesign> isaStudyDesigns = isaStudy.getStudyDesigns();

        List<StudyDesignDescriptors> descriptors = new LinkedList<StudyDesignDescriptors>();

        for (StudyDesign isaStudyDesign: isaStudyDesigns){


            StudyDesignDescriptors descriptor = new StudyDesignDescriptors();

            descriptor.setDescription(isaStudyDesign.getIdentifier());

            descriptors.add(descriptor);
        }

        return descriptors;

    }


    private static Collection<StudyFactor> isaTabStudyFactors2MetaboLightsStudyFactors(org.isatools.isacreator.model.Study isaStudy){

        List<Factor> isaFactors = isaStudy.getFactors();

        List<StudyFactor> studyFactors = new LinkedList<StudyFactor>();

        for (Factor isaFactor : isaFactors){
            StudyFactor studyFactor = new StudyFactor();

            studyFactor.setName(isaFactor.getFactorName());

            studyFactors.add(studyFactor);
        }

        return studyFactors;

    }

    private static Collection<Sample> isaTabSamples2MetabolightsSamples(org.isatools.isacreator.model.Study isaStudy){

        List<List<String>> isaSamplesData = isaStudy.getStudySample().getTableReferenceObject().getReferenceData().getData();
        List<Sample> metSamples = new LinkedList<Sample>();

        for (List<String> isaSamples: isaSamplesData){

            Sample metSample = new Sample();

            Ontology ontology = new Ontology();

            metSample.setSourceName(isaSamples.get(mapIsaStudyFieldName(isaStudy, SOURCE_NAME)));

            metSample.setCharactersticsOrg(ontology.getName(isaSamples.get(mapIsaStudyFieldName(isaStudy, CHARACTERISTICS_ORGANISM))));

            metSample.setCharactersticsOrgPart(ontology.getName(isaSamples.get(mapIsaStudyFieldName(isaStudy, CHARACTERISTICS_ORGANISM_PART ))));

            metSample.setProtocolRef(isaSamples.get(mapIsaStudyFieldName(isaStudy, PROTOCOL_REF)));

            metSample.setSampleName(isaSamples.get(mapIsaStudyFieldName(isaStudy, SAMPLE_NAME)));

            metSample.setFactors(isaTabFactors2MetaboLightsFactors(isaStudy, isaSamples));

            metSamples.add(metSample);

        }

        return metSamples;
    }

    private static Collection<Factors> isaTabFactors2MetaboLightsFactors(org.isatools.isacreator.model.Study isaStudy, List<String> isaSamples) {

        OrderedMap<String, FieldObject> isa2MetFactors = isaStudy.getStudySample().getTableReferenceObject().getFieldLookup();
        List<Factors> metFactors = new LinkedList<Factors>();

        for(Map.Entry<String, FieldObject> isaFactorEntrySet : isa2MetFactors.entrySet()){
            String isaFactorKeys = isaFactorEntrySet.getKey();
            FieldObject isaFactorValue = isaFactorEntrySet.getValue();

            if(isaFactorValue.getFieldName().startsWith(FACTOR)){
                Factors factor = new Factors();
                Ontology ontology = new Ontology();
                factor.setFactorKey(trimIsaFactorKeys(isaFactorKeys)); //
                factor.setFactorValue(ontology.getName(isaSamples.get(isaFactorValue.getColNo())));
                metFactors.add(factor);
            }
        }

        return metFactors;
    }

    private static String trimIsaFactorKeys(String isaFactorKeys) {

        String replaceFirst = "Factor Value\\[";
        String replaceLast = "]";
        String factorName = isaFactorKeys.replaceFirst(replaceFirst,"");
        factorName = factorName.replace(replaceLast,"");

        return factorName;
    }


    private static int mapIsaStudyFieldName(org.isatools.isacreator.model.Study isaStudy, String sourceName){

        Collection<FieldObject> isaStudyFieldValue = isaStudy.getStudySample().getTableReferenceObject().getFieldLookup().values();
        int colNo = 0;

        if(!isaStudyFieldValue.isEmpty()){
            for(FieldObject fieldValue: isaStudyFieldValue){
                if(fieldValue.getFieldName().equalsIgnoreCase(sourceName)){
                    colNo = fieldValue.getColNo();
                }
            }
        }

        return colNo;
    }

    private static Collection<AssayLine> isaTabAssayLines2MetabolightsAssayLines(org.isatools.isacreator.model.Assay isaAssay, Assay metAssay, String studyFolder){

        List<List<String>> isaAssaysLines = isaAssay.getTableReferenceObject().getReferenceData().getData();
        List<AssayLine> metAssayLines = new LinkedList<AssayLine>();

        for (List<String> isaAssayLine: isaAssaysLines){

            AssayLine metAssayLine = new AssayLine();

            metAssayLine.setSampleName(isaAssayLine.get(mapIsaFieldName(isaAssay, ASSAY_COLUMN_SAMPLE_NAME)));

            if (metAssay.getMetaboliteAssignment().getMetaboliteAssignmentFileName() == null)  // Set the metabolite assignment file name if not known (aka MAF)
                metAssay.getMetaboliteAssignment().setMetaboliteAssignmentFileName(studyFolder + File.separator+ isaAssayLine.get(mapIsaFieldName(isaAssay, METABOLITE_ASSIGNMENT_FILE)));

            metAssayLines.add(metAssayLine);
        }

        return metAssayLines;

    }


    private static int mapIsaFieldName(org.isatools.isacreator.model.Assay isaAssay, String fieldName) {

        Collection<FieldObject> isaAssyaValues = isaAssay.getTableReferenceObject().getFieldLookup().values();

        int colNo = 0;

        for(FieldObject sampleName : isaAssyaValues){
            if(sampleName.getFieldName().equals(fieldName)){
                colNo = sampleName.getColNo();
            }
        }

        return colNo;
    }

    private static Collection<Assay> isaTabAssays2MetabolightsAssays(org.isatools.isacreator.model.Study isaStudy, String studyFolder, boolean includeMetabolites){

        Map<String, org.isatools.isacreator.model.Assay> isaAssays = isaStudy.getAssays();

        List<Assay> assays = new LinkedList<Assay>();

        for(Map.Entry<String, org.isatools.isacreator.model.Assay> isaAssayEntry: isaAssays.entrySet() ){

            org.isatools.isacreator.model.Assay isaAssay = isaAssayEntry.getValue();

            Assay metAssay = new Assay();

            metAssay.setFileName(isaAssay.getAssayReference());

            metAssay.setMeasurement(isaAssay.getMeasurementEndpoint());

            metAssay.setPlatform(isaAssay.getAssayPlatform());

            metAssay.setTechnology(isaAssay.getTechnologyType());

            // Add assay lines
            metAssay.setAssayLines(isaTabAssayLines2MetabolightsAssayLines(isaAssay, metAssay, studyFolder));

            // Add the metabolite assignment file (MAF)
            if (includeMetabolites)
                metAssay.setMetaboliteAssignment(
                    getMAF(metAssay.getMetaboliteAssignment().getMetaboliteAssignmentFileName()));

            assays.add(metAssay);
        }

        return assays;

    }

    private static Collection<Publication> isaTabPublications2MetaboLightsPublications(org.isatools.isacreator.model.Study isaStudy){

        List<org.isatools.isacreator.model.Publication> isaPublications = isaStudy.getPublications();

        List<Publication> studyPublications = new LinkedList<Publication>();

        for (org.isatools.isacreator.model.Publication isaPublication : isaPublications){
            Publication publication = new Publication();

            publication.setAbstractText(isaPublication.getAbstractText());
            publication.setDoi(isaPublication.getPublicationDOI());
            publication.setPubmedId(isaPublication.getPubmedId());
            publication.setTitle(isaPublication.getPublicationTitle());

            studyPublications.add(publication);
        }


        return studyPublications;

    }

    private static Collection<Protocol> isaTabProtocols2MetaboLightsProtocols(org.isatools.isacreator.model.Study isaStudy){

        List<org.isatools.isacreator.model.Protocol> isaStudyProtocols = isaStudy.getProtocols();

        List<Protocol> studyProtocols = new LinkedList<Protocol>();

        for (org.isatools.isacreator.model.Protocol isaProtocol : isaStudyProtocols){
            Protocol protocol = new Protocol();

            protocol.setName(isaProtocol.getProtocolName());
            protocol.setDescription(isaProtocol.getProtocolDescription());

            studyProtocols.add(protocol);
        }


        return studyProtocols;

    }
    public static Date isaTabDate2Date (String isaTabDate){

        try {
            return isaTabDateFormat.parse(isaTabDate);
        } catch (ParseException e) {
            return null;
        }
    }
}

/*
        List<List<String>> isaFactorsData = isaStudy.getStudySample().getTableReferenceObject().getReferenceData().getData();
//        OrderedMap<String, FieldObject> isaFactorsFieldLookup = isaStudy.getStudySample().getTableReferenceObject().getFieldLookup();
        List<Factors> metFactors = new LinkedList<Factors>();

        for(List<String> isaFactors : isaFactorsData){

            List<Integer> colNoList = mapIsaStudyFieldNameForFactors(isaStudy, FACTOR);

            for(int i = 0; i<colNoList.size(); i++){

                Factors metFactor = new Factors();
                metFactor.setFactorName(isaFactors.get(colNoList.get(i)));
                metFactors.add(metFactor);
            }
        }

//        for(List<String> isaFactor : isaFactors ){
//            Factors factors = new Factors();
//
//        }

        //            for(Map.Entry<String, FieldObject> isaFactorLookup : isaFactorsFieldLookup.entrySet()){
//                FieldObject isaFactorValues = isaFactorLookup.getValue();
//                if(isaFactorValues.getFieldName().contains(FACTOR)){

//            FieldObject isaFactorValues = isaFactor.getValue();
//
//            if(isaFactorValues.getFieldName().startsWith(FACTOR)){
//                Factors metFactor = new Factors();
//                metFactor.setFactorName(isaFactor.getValue().getFieldName());
//                metFactors.add(metFactor);
//            }
//        }
    private static List<Integer> mapIsaStudyFieldNameForFactors(org.isatools.isacreator.model.Study isaStudy, String factor) {

        Collection<FieldObject> isaStudyFieldValue = isaStudy.getStudySample().getTableReferenceObject().getFieldLookup().values();
        int colNo = 0;
        List<Integer> colNoList = new ArrayList<Integer>();

        if(!isaStudyFieldValue.isEmpty()){
            for(FieldObject fieldValue: isaStudyFieldValue){
                if(fieldValue.getFieldName().startsWith(factor)){
                    colNo = fieldValue.getColNo();
                    colNoList.add(colNo);
                }
            }
        }

        return colNoList;
    }

*/

