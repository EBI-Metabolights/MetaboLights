package uk.ac.ebi.metabolights.search.service.imp.es;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchResult;

import java.io.IOException;

/**
 * User: conesa
 * Date: 19/06/15
 * Time: 14:57
 */
public class SerializationTest {

	private static final Logger logger = LoggerFactory.getLogger(SerializationTest.class);
	public static final String ACCESSION = "MTBLC123456";
	private static final String STUDY_ID = "LALALA1";


	@Test
	public void testMixedSearchResultsSerialization() throws IndexingFailureException, IOException {

		MetaboLightsCompound compound = new MetaboLightsCompound();

		compound.setAccession(ACCESSION);
		compound.setName("a compound");
		compound.setStudyStatus("PUBLIC");

		SearchResult searchResult = new SearchResult();

		searchResult.getResults().add(compound);


		Study study = new Study();

		study.setStudyIdentifier(STUDY_ID);
		study.setTitle("a study");
		study.setStudyStatus(LiteStudy.StudyStatus.PUBLIC);

		searchResult.getResults().add(study);

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		String serialization = mapper.writeValueAsString(searchResult);

		SearchResult deserialsed  = mapper.readValue(serialization,SearchResult.class);

		Assert.assertEquals("deserialization was wrong.", 2, deserialsed.getResults().size());

	}


}