package uk.ac.ebi.metabolights.search.service.imp.es;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: conesa
 * Date: 28/05/15
 * Time: 16:17
 */
public class EscapingTest {

	private static final Logger logger = LoggerFactory.getLogger(EscapingTest.class);

	@Test
	public void testEscapingCharacters() {

		// From elastic search web: https://www.elastic.co/guide/en/elasticsearch/reference/1.x/query-dsl-query-string-query.html
		String esSpecialCharacters = "+ - = && || > < ! ( ) { } [ ] ^ \" ~ * ? : \\ /";

		String[] esSpecialCharArray = esSpecialCharacters.split(" ");

		for (String special : esSpecialCharArray) {

			String escaped = org.apache.lucene.queryparser.classic.QueryParser.escape(special);

			Assert.assertNotSame("Character " + special + " has not been escaped!", special, escaped);

			if (escaped.equals(special)) logger.error("Character {} not escaped.", escaped);

		}



	}
}