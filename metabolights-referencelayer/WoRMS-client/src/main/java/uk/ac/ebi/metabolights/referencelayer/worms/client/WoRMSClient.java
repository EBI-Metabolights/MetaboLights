package uk.ac.ebi.metabolights.referencelayer.worms.client;

import aphia.v1_0.AphiaRecord;
import aphia.v1_0.Classification;
import aphia.v1_0.aphiaid.GetAphiaIDDocument;
import aphia.v1_0.aphiaid.GetAphiaIDResponseDocument;
import aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument;
import aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument;
import aphia.v1_0.classification.GetAphiaClassificationByIDDocument;
import aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;

/**
 * User: conesa
 * Date: 21/11/2013
 * Time: 11:15
 */
public class WoRMSClient {

	static AphiaNameServiceStub service ;

	public WoRMSClient() throws AxisFault {
		setUpService();
	}

	public static void setUpService() throws AxisFault {

		service = new uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceStub();

	}

	public int getAphiaID(String scientificName) throws Exception {

		GetAphiaIDDocument doc = GetAphiaIDDocument.Factory.newInstance();
		GetAphiaIDDocument.GetAphiaID aphiaID =  doc.addNewGetAphiaID();
		aphiaID.setScientificname(scientificName);

		GetAphiaIDResponseDocument response = service.getAphiaID(doc);

		return  response.getGetAphiaIDResponse().getReturn();

	}

	public AphiaRecord getAphiaRecordByID(int aphiaID) throws RemoteException {

		GetAphiaRecordByIDDocument doc = GetAphiaRecordByIDDocument.Factory.newInstance();
		GetAphiaRecordByIDDocument.GetAphiaRecordByID aphiaById =  doc.addNewGetAphiaRecordByID();
		aphiaById.setAphiaID(aphiaID);

		GetAphiaRecordByIDResponseDocument response = service.getAphiaRecordByID(doc);


		 return response.getGetAphiaRecordByIDResponse().getReturn();

	}

	public Classification getAphiaClasificationByID(int aphiaID) throws Exception {

		GetAphiaClassificationByIDDocument doc = GetAphiaClassificationByIDDocument.Factory.newInstance();
		GetAphiaClassificationByIDDocument.GetAphiaClassificationByID aphiaById =  doc.addNewGetAphiaClassificationByID();
		aphiaById.setAphiaID(aphiaID);

		GetAphiaClassificationByIDResponseDocument response = service.getAphiaClassificationByID(doc);

		// Classification starts from the root, so, you only have, getChild methods to navigate.
		return response.getGetAphiaClassificationByIDResponse().getReturn();


	}


}
