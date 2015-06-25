package uk.ac.ebi.metabolights;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;

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

}