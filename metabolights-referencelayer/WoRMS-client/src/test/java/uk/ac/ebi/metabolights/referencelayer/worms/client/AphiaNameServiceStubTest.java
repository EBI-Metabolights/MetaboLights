package uk.ac.ebi.metabolights.referencelayer.worms.client;

import org.apache.axis2.AxisFault;
import org.junit.BeforeClass;
import org.junit.Test;
import aphia.v1_0.AphiaRecord;
import aphia.v1_0.Classification;
import aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument;
import aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument;
import aphia.v1_0.classification.GetAphiaClassificationByIDDocument;
import aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument;

import static org.junit.Assert.assertEquals;


/**
 * User: conesa
 * Date: 20/11/2013
 * Time: 15:34
 *
 * Webservice help:
 * http://www.marinespecies.org/aphia.php?p=soap#
 * http://www.marinespecies.org/aphia.php?p=webservice
 *
 * Page for Solea solea (Test element)
 * http://www.marinespecies.org/aphia.php?p=taxdetails&id=127160
 */
public class AphiaNameServiceStubTest {

	static WoRMSClient woRMSClient;

	@BeforeClass
	public static void setUpService() throws AxisFault {


		woRMSClient = new WoRMSClient();

	}

	@Test
	public void testGetAphiaID() throws Exception {

		int aphiaID = woRMSClient.getAphiaID("Solea solea");

		assertEquals("Aphia id must be 127160" , 127160, aphiaID);

	}


	@Test
	public void testGetAphiaRecordByID() throws Exception {


		AphiaRecord record = woRMSClient.getAphiaRecordByID(127160);

		assertEquals("Scientific name", "Solea solea", record.getScientificname());
		assertEquals("Kingdom check", "Animalia", record.getKingdom());


	}
	@Test
	public void testGetAphiaClasificationByID() throws Exception {



		// Classification starts from the root, so, you only have, getChild methods to navigate.
		Classification record = woRMSClient.getAphiaClasificationByID(127160);

		// For animalia it returns from the root (Superdomain) to Animalia: one Child...
		// The next child is not null but has getRank == null!
		//Classification record = woRMSClient.getAphiaClasificationByID(2);

		assertEquals("Test rank", record.getRank(), "Superdomain");
		assertEquals("Test Kingdom", record.getChild().getScientificname(), "Animalia");

	}
}
