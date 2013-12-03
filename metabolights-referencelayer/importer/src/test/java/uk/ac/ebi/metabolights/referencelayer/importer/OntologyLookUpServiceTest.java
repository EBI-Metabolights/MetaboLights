package uk.ac.ebi.metabolights.referencelayer.importer;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

/**
 * User: conesa
 * Date: 03/12/2013
 * Time: 16:48
 */
public class OntologyLookUpServiceTest {
	private static OntologyLookUpService lus;


	@BeforeClass
	public static void setUp(){
		lus = new OntologyLookUpService();
	}
	@Test
	public void testGetTermsByName() throws Exception {



		Map<String,String> res = lus.getTermsByName("brain", "EFO");
		for (String tissue : res.keySet()) {
			System.out.println(tissue+"\t"+res.get(tissue));
		}

	}

	@Test
	public void testGetTermsPrefixed() throws Exception {



		Map<String,String> res2 = lus.getTermsPrefixed("brain");
		for (String tissue : res2.keySet()) {
			System.out.println(tissue+"\t"+res2.get(tissue));
		}

	}

	@Test
	public void testGetTermName() throws Exception {


		System.out.println("BTO trial :"+lus.getTermName("BTO:0000309", "BTO"));


		// ATENTION: For NEWT you shouldn't send the prefix!!!
		System.out.println("NEWT human :"+lus.getTermName("NEWT:9606", "NEWT"));

	}
}
