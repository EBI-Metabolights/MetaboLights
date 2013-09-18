package uk.ac.ebi.metabolights.repository.utils;

import org.isatools.isacreator.configuration.FieldObject;
import org.isatools.isacreator.model.*;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Contact;
import uk.ac.ebi.metabolights.repository.model.Protocol;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;

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

    //Below are for sample tab
    private static final String SOURCE_NAME = "Source Name";
    private static final String CHARACTERISTICS_ORGANISM = "Characteristics[Organism]";
    private static final String CHARACTERISTICS_ORGANISM_PART = "Characteristics[Organism part]";
    private static final String PROTOCOL_REF = "Protocol REF";
    private static final String SAMPLE_NAME = "Sample Name";

    public static Study convert( org.isatools.isacreator.model.Investigation source){


        // Convert the study...
        Study metStudy = isaTabInvestigation2MetaboLightsStudy(source);

        // Convert the authors...
        return metStudy;


    }

    private static Study isaTabInvestigation2MetaboLightsStudy(org.isatools.isacreator.model.Investigation source){

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
        metStudy.setFactors(isaTabFactors2MetaboLightsFactors(isaStudy));

        // Publications
        metStudy.setPublications(isaTabPublications2MetaboLightsPublications(isaStudy));

        // Protocols
        metStudy.setProtocols(isaTabProtocols2MetaboLightsProtocols(isaStudy));

        //Assays
        metStudy.setAssays(isaTabAssays2MetabolightsAssays(isaStudy));

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


    private static Collection<StudyFactor> isaTabFactors2MetaboLightsFactors(org.isatools.isacreator.model.Study isaStudy){

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

            metSample.setSourceName(isaSamples.get(mapIsaStudyFieldName(isaStudy, SOURCE_NAME)));

            metSample.setCharactersticsOrg(isaSamples.get(mapIsaStudyFieldName(isaStudy, CHARACTERISTICS_ORGANISM)));

            metSample.setCharactersticsOrgPart(isaSamples.get(mapIsaStudyFieldName(isaStudy, CHARACTERISTICS_ORGANISM_PART )));

            metSample.setProtocolRef(isaSamples.get(mapIsaStudyFieldName(isaStudy, PROTOCOL_REF)));

            metSample.setSampleName(isaSamples.get(mapIsaStudyFieldName(isaStudy, SAMPLE_NAME)));

            metSamples.add(metSample);

        }

        return metSamples;
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

    private static Collection<AssayLine> isaTabAssayLines2MetabolightsAssayLines(org.isatools.isacreator.model.Assay isaAssay){

        List<List<String>> isaAssaysLines = isaAssay.getTableReferenceObject().getReferenceData().getData();
        List<AssayLine> metAssayLines = new LinkedList<AssayLine>();

        for (List<String> isaAssayLine: isaAssaysLines){

            AssayLine metAssayLine = new AssayLine();

            metAssayLine.setSampleName(isaAssayLine.get(mapIsaFieldName(isaAssay, ASSAY_COLUMN_SAMPLE_NAME)));

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


    private static Collection<Assay> isaTabAssays2MetabolightsAssays(org.isatools.isacreator.model.Study isaStudy){

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
            metAssay.setAsssayLines(isaTabAssayLines2MetabolightsAssayLines(isaAssay));

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
