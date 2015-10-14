package uk.ac.ebi.metabolights.search.service.imp.es;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 31/07/15
 * Time: 09:12
 */
public class IndexAdmin {

	private static final Logger logger = LoggerFactory.getLogger(IndexAdmin.class);


	@Test
	public void testIndexAdmin() throws IndexingFailureException {


		String studyPrefix = "STUDY_PREFIX";

		ElasticSearchService elasticSearchService = new ElasticSearchService();

		elasticSearchService.setStudyPrefix(studyPrefix);

		// Start with a clean index.
		elasticSearchService.deleteIndex();
		assertFalse("Index hasn't been deleted", elasticSearchService.doesIndexExists());

		// Create just the index
		elasticSearchService.addIndex();
		assertTrue("Index hasn't been created", elasticSearchService.doesIndexExists());

		// Create the study mapping
		elasticSearchService.addStudyMapping();
		assertTrue("Study mapping hasn't been created", elasticSearchService.doesMappingExists(elasticSearchService.STUDY_TYPE_NAME));

		// Create the compound mapping
		elasticSearchService.addCompoundMapping();
		assertTrue("Compounds mapping hasn't been created", elasticSearchService.doesMappingExists(elasticSearchService.COMPOUND_TYPE_NAME));

		// This should delete the whole study mapping and all possible documents...
		elasticSearchService.delete(studyPrefix + "*");

		assertFalse("Study mapping hasn't been deleted", elasticSearchService.doesMappingExists(elasticSearchService.STUDY_TYPE_NAME));

		// This should delete the whole compound mapping and all possible documents...
		elasticSearchService.delete(elasticSearchService.getCompoundPrefix() + "*");

		assertFalse("Compound mapping hasn't been deleted", elasticSearchService.doesMappingExists(elasticSearchService.COMPOUND_TYPE_NAME));


	}
}