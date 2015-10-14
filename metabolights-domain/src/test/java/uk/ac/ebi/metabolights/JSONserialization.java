package uk.ac.ebi.metabolights;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * User: conesa
 * Date: 24/06/15
 * Time: 10:14
 */
public class JSONserialization {

	private static final Logger logger = LoggerFactory.getLogger(JSONserialization.class);
	private static final String COMPOUND__ID = "compoundId";
	private static final String STUDY_ID = "studyId";

	@Test
	public void testCompoundSerialization() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		MetaboLightsCompound compound = getNewMetaboLightsCompound();

		String compoundJSON = mapper.writeValueAsString(compound);

		logger.info("Compound serialised into {}", compoundJSON);

		compound = mapper.readValue(compoundJSON,compound.getClass());

		Assert.assertEquals("Compound not serialised properly", COMPOUND__ID, compound.getAccession());


	}

	private MetaboLightsCompound getNewMetaboLightsCompound() {
		MetaboLightsCompound compound = new MetaboLightsCompound();

		compound.setAccession(COMPOUND__ID);
		compound.setName("a compound");
		compound.setStudyStatus("PUBLIC");
		return compound;
	}

	@Test
	public void testStudySerialization() throws IOException {


		ObjectMapper mapper = new ObjectMapper();
		LiteStudy study = getNewStudy();

		String studyJSON = mapper.writeValueAsString(study);

		logger.info("Study serialised into {}", studyJSON);

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


		entities.add(compound);
		entities.add(study);

		String entitiesJSON = mapper.writeValueAsString(entities);


		logger.info("Entities serialised into {}", entitiesJSON);

		entities = mapper.readValue(entitiesJSON, entities.getClass());

		Assert.assertEquals("Entities (base class for studies and compounds) not serialised properly.", 2, entities.size());

		// Should cast into a compound
		compound = (MetaboLightsCompound) entities.get(0);

		// Should cast into a study
		study = (LiteStudy) entities.get(1);


	}

	@Test
	public void testFullStudySerialization() throws IOException {


		ObjectMapper mapper = new ObjectMapper();
		Study study = getNewFullStudy();

		logger.info("Before mapping Validation status " + study.getValidations().getEntries().iterator().next().getStatus().name());
		Assert.assertEquals("Before mapping Validation status", Status.RED, study.getValidations().getEntries().iterator().next().getStatus());


		String studyJSON = mapper.writeValueAsString(study);

		logger.info("Full Study serialised into {}", studyJSON);

		study = mapper.readValue(studyJSON,study.getClass());

		Assert.assertEquals("Study not serialised properly.", STUDY_ID, study.getStudyIdentifier());

		logger.info("Validation size : " + study.getValidations().getEntries().size());
		Assert.assertEquals("Study hasn't got a validation.", 1, study.getValidations().getEntries().size());

		Assert.assertEquals("study title Validation status", Status.RED, study.getValidations().getEntries().iterator().next().getStatus());

		for(Validation validation : study.getValidations().getEntries()){
			logger.info(validation.getDescription());

		}

	}

	private Study getNewFullStudy() {
		Study study = new Study();

		study.setStudyIdentifier(STUDY_ID);
		//study.setTitle("a study");
		study.setStudyStatus(LiteStudy.StudyStatus.PUBLIC);


		study.setTitle("Life is so cool, all is well!");

		Collection<Publication> publicationCollection = new LinkedList<>();
		Publication publication = new Publication();
		publication.setTitle("scooby dooby doo scooby dooby doo scooby dooby doo scooby dooby doo scooby dooby doo");
		publication.setPubmedId("123456");
		publicationCollection.add(publication);
		study.setPublications(publicationCollection);

		// Add a validation
		Validation validation = new Validation("description", Requirement.MANDATORY, Group.PROTOCOLS);
		study.getValidations().getEntries().add(validation);


		return study;
	}


}