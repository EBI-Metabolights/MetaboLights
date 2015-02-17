/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Nov-11
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.utils;

import org.isatools.isacreator.configuration.FieldObject;
import org.isatools.isacreator.model.Factor;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.StudyDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final String FIELDS_KEY_SEP = "~";
    static Logger logger = LoggerFactory.getLogger(IsaTab2MetaboLightsConverter.class);
    /*
    From isaTab specifications:
    "Dates
    Dates should be supplied in the ISO 8601 format “YYYY-MM-DD”."
    */
    private static final String ISA_TAB_DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat isaTabDateFormat = new SimpleDateFormat(ISA_TAB_DATE_FORMAT);

    private static final String METABOLITE_ASSIGNMENT_FILE = "metabolite assignment file";

    private static MzTabDAO mzTabDAO = new MzTabDAO();
    //Below are for sample tab
    private static final String CHARACTERISTICS_ORGANISM = "characteristics[organism]";
    private static final String CHARACTERISTICS_ORGANISM_PART = "characteristics[organism part]";

	// To improve assay conversion performance...
	private static List<String[]> currentAssaySpreadsheet;
	private static String currentAssayName = "";


	public static Study convert(Investigation investigation, String studyFolder, boolean includeMetabolites, Study studyToFill){

        // Convert the study from the ISAcreator model...
        Study metStudy = isaTabInvestigation2MetaboLightsStudy(investigation, studyFolder, includeMetabolites, studyToFill);

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

    private static Study isaTabInvestigation2MetaboLightsStudy(org.isatools.isacreator.model.Investigation source, String studyFolder, boolean includeMetabolites, Study studyToFill) {

        // Instantiate new MetaboLights investigation object
        Study metStudy = studyToFill;


        // Get the first and unique study
        org.isatools.isacreator.model.Study isaStudy = source.getStudies().values().iterator().next();


        // Populate direct study members
        metStudy.setStudyIdentifier(isaStudy.getStudyId());
        metStudy.setTitle(isaStudy.getStudyTitle());
        metStudy.setDescription(isaStudy.getStudyDesc());

        if (isaStudy.getPublicReleaseDate() != null){

            // Give precedence to existing date
            // Fill it if it doesn't exist.
            if (metStudy.getStudyPublicReleaseDate() == null) {
                metStudy.setStudyPublicReleaseDate(isaTabDate2Date(isaStudy.getPublicReleaseDate()));
            }
        }

        // If release dates do not match...
        if (!isaStudy.getPublicReleaseDate().equals(metStudy.getStudyPublicReleaseDate())){

            logger.warn(studyToFill.getStudyIdentifier() + " release date from the DB (" + studyToFill.getStudyPublicReleaseDate() + ") doesn't match the same in the file (" + isaStudy.getPublicReleaseDate() + ")");

        }


        if (isaStudy.getDateOfSubmission() != null)
            metStudy.setStudySubmissionDate(isaTabDate2Date(isaStudy.getDateOfSubmission()));

        metStudy.setStudyLocation(studyFolder);


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
        metStudy.setAssays(isaTabAssays2MetabolightsAssays(isaStudy, metStudy, includeMetabolites));

        //Samples
        metStudy.setSampleTable(isaTabSamples2MetabolightsSamples(isaStudy, metStudy));

        //Organism and Organism part
        metStudy.setOrganism(sampleOrg2organism(metStudy));


        return metStudy;
    }

    private static Collection<Organism> sampleOrg2organism(Study metStudy) {

        Set<Organism> organisms = new HashSet<Organism>();
        List<String> sampleDeDuplication = new ArrayList<String>();

		// NOw, using a multimap we are getting a set instead but there should be only one field
//		Field organismField = metStudy.getSampleTable().getFields().get(CHARACTERISTICS_ORGANISM);
//		Field organismPartField = metStudy.getSampleTable().getFields().get(CHARACTERISTICS_ORGANISM_PART);
		Field organismField = getFieldByName(metStudy.getSampleTable(), CHARACTERISTICS_ORGANISM);
		Field organismPartField = getFieldByName(metStudy.getSampleTable(),CHARACTERISTICS_ORGANISM_PART);


		// If both field are null...exit with an empty collection
		if (organismField == null && organismPartField == null) return organisms;


        for (Row sample: metStudy.getSampleTable()) {
			Organism organism = new Organism();

			if (organismField != null) {
				organism.setOrganismName(sample.getValues().get(organismField.getIndex()));
			} else {
				logger.warn(CHARACTERISTICS_ORGANISM + " column not found in " + metStudy.getStudyLocation());
			}

			if (organismPartField != null){
				organism.setOrganismPart(sample.getValues().get(organismPartField.getIndex()));
			} else {
				logger.warn(CHARACTERISTICS_ORGANISM_PART + " column not found in " + metStudy.getStudyLocation());
			}

            if (!sampleDeDuplication.contains(organism.getOrganismName())) {
                organisms.add(organism);
                sampleDeDuplication.add(organism.getOrganismName());
            }
        }
        return organisms;
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

    private static Table isaTabSamples2MetabolightsSamples(org.isatools.isacreator.model.Study isaStudy, Study metStudy){

        List<List<String>> isaSamplesData = isaStudy.getStudySample().getTableReferenceObject().getReferenceData().getData();

		// Create the sample table object
		Table sampleTable = new Table(isaSamplesData,  getTableFieldsMap(isaStudy.getStudySample()));
		metStudy.setSampleTable(sampleTable);

        return sampleTable;
    }

    private static Field getFieldByName(Table table, String name){

        for (String key: table.getFields().keySet()){

            if (key.contains(FIELDS_KEY_SEP + name)){
                return table.getFields().get(key);
            }

        }

        return null;
    }
	private static void fillMetaboliteAssignmentFile(Assay metAssay, Study metStudy){

		//Field mafColumnField = metAssay.getAssayTable().getFields().get(METABOLITE_ASSIGNMENT_FILE);
        Field mafColumnField = getFieldByName(metAssay.getAssayTable(),METABOLITE_ASSIGNMENT_FILE);

		// If column not present
		if (mafColumnField == null) {
			logger.warn(METABOLITE_ASSIGNMENT_FILE + " column not found in file: " + metAssay.getFileName());
			return;
		}

		for(Row assayLine:metAssay.getAssayTable()){

			String mafValue = assayLine.getValues().get(mafColumnField.getIndex());

			// If not empty or null
			if (mafValue!= null && !mafValue.equals("")){

				String mafFileName = metStudy.getStudyLocation() + File.separator + mafValue;

				File mafFile = new File(mafFileName);

				if(mafFile.exists()){
					metAssay.getMetaboliteAssignment().setMetaboliteAssignmentFileName(mafFileName);
					return;
				} else {
					logger.warn("Identification file reported not present: " + mafFileName);
				}
			}
		}

	}

	// Returns a map to get the value in an assay line based on the name
	private static LinkedHashMap<String,Field> getTableFieldsMap(org.isatools.isacreator.model.Assay assay){

		LinkedHashMap<String,Field> tableFieldsMap = new LinkedHashMap();

        // First header is the row number...don't want that.
		Integer index = -1;

		for (String header :  assay.getTableReferenceObject().getPreDefinedHeaders()){

			if (index>-1) {

				FieldObject isaField = assay.getTableReferenceObject().getFieldByName(header);

				Field newField = isaField2Field(header,index, isaField);

				tableFieldsMap.put(index + FIELDS_KEY_SEP + header.toLowerCase(), newField);

			}

			index++;


		}


		return tableFieldsMap;

	}

	private static Field isaField2Field(String isaFieldHeader, Integer index, FieldObject isaField) {


		// Parse the header
		// Header can come with Field type [ xxx ] or plain...there are some cases with () but ignoring them so far.
		String header = isaFieldHeader;

		// Dafault type to basic
		String type = "basic";

		if (header.contains("[")){
			String[] headerChunks = header.split("\\[|\\]");
			header = headerChunks[1];
			type = headerChunks[0];
		}

		Field field = new Field(header, index,type);

		// Fill the description
		if (isaField != null)
			field.setDescription(isaField.getDescription());

		field.setCleanHeader(header);


		// Return the field
		return  field;


	}

    private static List<Assay> isaTabAssays2MetabolightsAssays(org.isatools.isacreator.model.Study isaStudy, Study metStudy, boolean includeMetabolites){

        Map<String, org.isatools.isacreator.model.Assay> isaAssays = isaStudy.getAssays();

        List<Assay> assays = new LinkedList<Assay>();

        int i = 1;

        for(Map.Entry<String, org.isatools.isacreator.model.Assay> isaAssayEntry: isaAssays.entrySet() ){

            org.isatools.isacreator.model.Assay isaAssay = isaAssayEntry.getValue();

            Assay metAssay = new Assay();

            metAssay.setFileName(isaAssay.getAssayReference());

            metAssay.setMeasurement(removeOntology(isaAssay.getMeasurementEndpoint()));

            metAssay.setPlatform(isaAssay.getAssayPlatform());

            metAssay.setTechnology(removeOntology(isaAssay.getTechnologyType()));

            metAssay.setAssayNumber(i); //To enable a simpler URL structure like "MTBLS1/assay/1 or MTBLS2/assay/2

			// Create the assay table object
			Table assayTable = new Table(isaAssay.getTableReferenceObject().getReferenceData().getData(), getTableFieldsMap(isaAssay));
			metAssay.setAssayTable(assayTable);

			// Look for the maf file
			fillMetaboliteAssignmentFile(metAssay, metStudy);

            // Add the metabolite assignment file (MAF)
            if (includeMetabolites)
                metAssay.setMetaboliteAssignment(
                    getMAF(metAssay.getMetaboliteAssignment().getMetaboliteAssignmentFileName()));

            assays.add(metAssay);

			i++;
        }

        return assays;

    }

    // Cleans the XXX: part of an ontologised field, if exists.
    private static String removeOntology(String ontologisedValue){
        int colonPos = ontologisedValue.indexOf(":");

        // if not found return same value
        if (colonPos == -1) return ontologisedValue;

        // Else remove it
        return ontologisedValue.substring(colonPos+1);


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
