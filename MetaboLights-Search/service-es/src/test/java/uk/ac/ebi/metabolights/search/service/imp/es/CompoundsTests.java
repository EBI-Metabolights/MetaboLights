package uk.ac.ebi.metabolights.search.service.imp.es;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;

/**
 * User: conesa
 * Date: 19/06/15
 * Time: 14:57
 */
public class CompoundsTests {

	private static final Logger logger = LoggerFactory.getLogger(CompoundsTests.class);
	public static final String ACCESSION = "MTBLC15570";
	private static final String STUDY_ID = "LALALA1";


	@Test
	public void testSimpleCompoundIndex() throws IndexingFailureException {

		MetaboLightsCompound compound = new MetaboLightsCompound();

		compound.setAccession(ACCESSION);
		compound.setName("D-alanine");
		compound.setDescription("The D-enantiomer of alanine.");
		compound.setHasSpecies(true);
		compound.setHasNMR(true);
		compound.setHasMS(true);
		compound.setHasLiterature(true);
		compound.setStudyStatus("PUBLIC");


		ElasticSearchService elasticSearchService = new ElasticSearchService();

		elasticSearchService.index(compound);

		// A search must return it
		SearchQuery searchQuery = new SearchQuery("'_id:" + ACCESSION);

		SearchResult searchResult = elasticSearchService.search(searchQuery);

		Assert.assertEquals("Search does not returns the compound 123456" ,1, searchResult.getResults().size());

	}

	@Test
	public void testMixedIndex() throws IndexingFailureException {

		testSimpleCompoundIndex();
		testSimpleStudyIndex();

		ElasticSearchService elasticSearchService = new ElasticSearchService();

		// A search must return it
		SearchQuery searchQuery = new SearchQuery();

		SearchResult searchResult = elasticSearchService.search(searchQuery);

		Assert.assertEquals("Search does not returns the 2 different entities." ,2, searchResult.getResults().size());


	}

	@Test
	public void testSimpleStudyIndex() throws IndexingFailureException {

		Study study = new Study();

		study.setStudyIdentifier(STUDY_ID);
		study.setTitle("a study");
		study.setStudyStatus(LiteStudy.StudyStatus.PUBLIC);


		ElasticSearchService elasticSearchService = new ElasticSearchService();

		elasticSearchService.index(study);

		// A search must return it
		SearchQuery searchQuery = new SearchQuery("'_id:" + STUDY_ID);

		SearchResult searchResult = elasticSearchService.search(searchQuery);

		Assert.assertEquals("Search does not returns the study " + STUDY_ID ,1, searchResult.getResults().size());


	}


}