package uk.ac.ebi.metabolights;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.*;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;

import java.io.File;
import java.io.IOException;

/**
 * User: conesa
 * Date: 24/06/15
 * Time: 10:14
 */
public class JSONserialization {

	private static final Logger logger = LoggerFactory.getLogger(JSONserialization.class);
	private static final String COMPOUND__ID = "compoundId";
	private static final String STUDY_ID = "studyId";
	private MetSpecies Meta;

	@Test
	public void testMetaboLightsCompoundSerialization() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		MetaboLightsCompound compound = getNewMetaboLightsCompound();

		String compoundJSON = mapper.writeValueAsString(compound);

		logger.info("MetaboLightsCompound serialised into {}", compoundJSON);

		compound = mapper.readValue(compoundJSON,compound.getClass());

		Assert.assertEquals("Compound not serialised properly", COMPOUND__ID, compound.getAccession());


	}


	@Test
	public void testCompoundSerialization() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		Compound compound = getNewCompound();

		String compoundJSON = mapper.writeValueAsString(compound);

		logger.info("Compound serialised into {}", compoundJSON);

		compound = mapper.readValue(compoundJSON,compound.getClass());

		Assert.assertEquals("Compound not serialised properly", COMPOUND__ID, compound.getMc().getAccession());

		Assert.assertEquals("Compound.spectra not serialised properly", 1, compound.getMc().getMetSpectras().size());

		Spectra spectra = compound.getMc().getMetSpectras().iterator().next();

		Assert.assertNotNull("Compound.spectra. not serialised properly", spectra.getPathToJsonSpectra());
		Assert.assertEquals("Compound.spectra.attribute not serialised properly", 1, spectra.getAttributes().size());


		Assert.assertEquals("Compound.species not serialised properly", 1, compound.getMc().getMetSpecies().size());
		Assert.assertEquals("Compound.pathways not serialised properly", 1, compound.getMc().getMetPathways().size());

	}

	@Test
	public void testSpectraSerialization() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		Spectra spectra = getNewSpectra();

		String spectraJSON = mapper.writeValueAsString(spectra);

		logger.info("Spectra serialised into {}", spectraJSON);

		spectra = mapper.readValue(spectraJSON,spectra.getClass());

		Assert.assertNotNull("Spectra not serialised properly", spectra.getPathToJsonSpectra());


	}


	private Compound getNewCompound() {
		MetaboLightsCompound mc = getNewMetaboLightsCompound();

		Compound compound = new Compound(mc, null);


		return compound;
	}


	private MetaboLightsCompound getNewMetaboLightsCompound() {
		MetaboLightsCompound compound = new MetaboLightsCompound();

		compound.setAccession(COMPOUND__ID);
		compound.setName("a compound");
		compound.setStudyStatus("PUBLIC");

		compound.getMetPathways().add(getNewPathWay());
		compound.setDescription("compound description");
		compound.getMetSpecies().add(getNewMetSpecie());
		compound.getMetSpectras().add(getNewSpectra());

		return compound;
	}

	private Spectra getNewSpectra() {
		Spectra spectra = new Spectra(5L,"path_to_spectra","spectra_name", Spectra.SpectraType.NMR);
		spectra.getAttributes().add(getNewAttribute());

		return spectra;
	}

	private Attribute getNewAttribute() {
		Attribute attribute = new Attribute();

		attribute.setId(8L);
		attribute.setAttributeDefinition(getNewAttrinuteDef());
		attribute.setValue("Attribute value");

		return attribute;
	}

	private AttributeDefinition getNewAttrinuteDef() {
		AttributeDefinition attributeDefinition = new AttributeDefinition();

		attributeDefinition.setDescription("Attribute def dectription");
		attributeDefinition.setName("attribute def_name");
		attributeDefinition.setId(9L);

		return attributeDefinition;
	}

	private Pathway getNewPathWay() {
		Pathway pathway = new Pathway(1, "pathwayname", getNewDatabase(), new File("afile"), getNewSpecie());
		return pathway;

	}

	private Database getNewDatabase() {
		Database database = new Database();

		database.setName("database_name");
		database.setId(1L);

		return database;
	}

	@Test
	public void testStudySerialization() throws IOException {


		ObjectMapper mapper = new ObjectMapper();
		LiteStudy study = getNewStudy();

		String studyJSON = mapper.writeValueAsString(study);

		logger.info("Study serialised into {}" , studyJSON);

		study = mapper.readValue(studyJSON,study.getClass());

		Assert.assertEquals("Study not serialised properly.", STUDY_ID, study.getStudyIdentifier());

	}

	private LiteStudy getNewStudy() {
		LiteStudy study = new LiteStudy();

		study.setStudyIdentifier(STUDY_ID);
		study.setTitle("a study");
		study.setStudyStatus(LiteStudy.StudyStatus.PUBLIC);
		return study;
	}

	@Test
	public void testBaseClassDeserialization() throws IOException {


		ObjectMapper mapper = new ObjectMapper();


		LiteStudy study = getNewStudy();
		MetaboLightsCompound compound = getNewMetaboLightsCompound();


		// Create a list of Entities
		EntityList entities = new EntityList();


		entities.add( compound);
		entities.add(study);

		String entitiesJSON = mapper.writeValueAsString(entities);


		logger.info("Entities serialised into {}" , entitiesJSON);

		entities = mapper.readValue(entitiesJSON, entities.getClass());

		Assert.assertEquals("Entities (base class for studies and compounds) not serialised properly.", 2, entities.size());

		// Should cast into a compound
		compound = (MetaboLightsCompound) entities.get(0);

		// Should cast into a study
		study = (LiteStudy) entities.get(1);


	}

	public Species getNewSpecie() {

		Species specie = new Species();
		specie.setSpecies("specie's name");
		specie.setId(2L);
		specie.setDescription("species_description");
		specie.setTaxon("taxon");
		specie.setSpeciesMember(getNeSpeciesMemeber());

		return specie;
	}

	private SpeciesMembers getNeSpeciesMemeber() {
		SpeciesMembers speciesMembers = new SpeciesMembers();
		speciesMembers.setTaxon("taxon");

		return  speciesMembers;
	}

	public MetSpecies getNewMetSpecie() {
		MetSpecies metSpecies = new MetSpecies(3L,getNewSpecie(),getNewCrossReference());
		metSpecies.setId(3L);

		return metSpecies;

	}

	private CrossReference getNewCrossReference() {
		CrossReference crossReference = new CrossReference();
		crossReference.setId(4L);
		crossReference.setAccession("cross_reference_accession");
		crossReference.setDb(getNewDatabase());

		return crossReference;
	}
}