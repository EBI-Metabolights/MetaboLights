package uk.ac.ebi.metabolights.search.service.imp.es;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
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
	public static final String ACCESSION = "MTBLC123456";


	@Test
	public void testSimpleCompoundIndex() throws IndexingFailureException {

		MetaboLightsCompound compound = new MetaboLightsCompound();

		compound.setAccession(ACCESSION);
		compound.setName("a compound");
		compound.setStudyStatus("PUBLIC");


		ElasticSearchService elasticSearchService = new ElasticSearchService();

		elasticSearchService.index(compound);

		// A search must return it
		SearchQuery searchQuery = new SearchQuery("'_id:" + ACCESSION);

		SearchResult searchResult = elasticSearchService.search(searchQuery);

		Assert.assertEquals("Search does not returns the compound 123456" , searchResult.getResults().size());


	}
}