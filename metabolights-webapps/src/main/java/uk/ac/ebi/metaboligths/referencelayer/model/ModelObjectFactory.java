package uk.ac.ebi.metaboligths.referencelayer.model;


import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.service.AppContext;

public class ModelObjectFactory {
	
	//private static ModelObjectFactory mof;
	
	private static Logger logger = Logger.getLogger(ModelObjectFactory.class);
	public static Compound getCompound(String accession) {
		
		try {
		
			logger.info("Getting compound" + accession);
			
			
			// Get the MetaboLights compound DAO
			MetaboLightsCompoundDAO mcd;
			
			mcd = new MetaboLightsCompoundDAO(AppContext.getConnection());
			
			MetaboLightsCompound mc = mcd.findByCompoundAccession(accession);
			
			mcd.close();

			
			// Get the chebi Entry
			Entity chebiEntity = getCompleteEntityExample(mc.getChebiId());
			
			
			Compound comp = new Compound(mc, chebiEntity);
			
			
			return comp;
		
		
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			return null;
		}
	}
	
	private static Entity getCompleteEntityExample (String chebiId){

		Entity entity = null;
		
	    try {

	      // Create client
	      ChebiWebServiceClient client = new ChebiWebServiceClient();
	      logger.info("Invoking getCompleteEntity");
	      
	      entity = client.getCompleteEntity(chebiId);
	      logger.info("GetName: " + entity.getChebiAsciiName());
	      

	    } catch ( ChebiWebServiceFault_Exception e ) {
	      logger.error(e.getMessage());
	    }
	    
	    return entity;
	}
	
//	public static ModelObjectFactory getInstance(){
//		if (mof == null) mof = new ModelObjectFactory();
//		return mof;
//	}
	private ModelObjectFactory(){}
	
	

}
