package uk.ac.ebi.metabolights.referencelayer.importer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntityList;
import uk.ac.ebi.chebi.webapps.chebiWS.model.RelationshipType;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;

public class ReferenceLayerImporter{

    private Logger LOGGER = Logger.getLogger(ReferenceLayerImporter.class);

    private MetaboLightsCompoundDAO mcd;
    private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient();


    // Root chebi entity that holds all the compound to import, by default is "metabolite".
    private String chebiIDRoot = "CHEBI:25212";
    private RelationshipType relationshipType = RelationshipType.HAS_ROLE;

    // Instantiate with a connection object
    public ReferenceLayerImporter(Connection connection) throws IOException {
            this.mcd = new MetaboLightsCompoundDAO(connection);
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getChebiIDRoot() {
        return chebiIDRoot;
    }

    public void setChebiIDRoot(String chebiIDRoot) {
        this.chebiIDRoot = chebiIDRoot;
    }

    public void importMetabolitesFromChebi(){

        LOGGER.info("Importing metabolites from chebi. Root: " + chebiIDRoot + ", relationship type: " + relationshipType);
        LiteEntityList el = null;

        // Try to get the list of metabolites from chebi...
        try {
             el = chebiWS.getAllOntologyChildrenInPath(chebiIDRoot, relationshipType,true);
        } catch (ChebiWebServiceFault_Exception e) {
            LOGGER.error("Can't import Metabolites from chebi. Chebi WS can't be accessed");
            return;
        }

        LOGGER.info(el.getListElement().size() + " Chebi compounds returned.");

        Long imported = new Long(0);

        // Now try to save the metabolites
        try {



            // Now we should have a list of chebi ids...
            for (LiteEntity le: el.getListElement()){

                imported = imported + chebiID2MetaboLights(le);

            }

        } catch (DAOException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        LOGGER.info(imported + " new compounds imported.");

    }
    /*
    Returns 1 if successful
     */
    private int chebiID2MetaboLights(LiteEntity chebiId) throws DAOException {

        try {

            MetaboLightsCompound mc = new MetaboLightsCompound();
            // Populate the metabolite compound with chebi data.
            mc.setAccession(chebiID2MetaboLightsID(chebiId.getChebiId()));
            mc.setChebiId(chebiId.getChebiId());
            mc.setName(chebiId.getChebiAsciiName());

            mcd.save(mc);

            return 1;

        } catch (DAOException e){

            Throwable cause = e.getCause();

            if (cause instanceof SQLException){

                SQLException sqle = (SQLException) cause;
                // If it's bacause a duplicate key...
                //http://stackoverflow.com/questions/1988570/how-to-catch-a-specific-exceptions-in-jdbc
                if (sqle.getSQLState().startsWith("23")){
                    LOGGER.info("The compound " + chebiId.getChebiId() + " is already imported into the database (Duplicated primary key)");
                    return 0;
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }
    private String chebiID2MetaboLightsID(String chebiID){
        return (chebiID.replaceFirst("CHEBI:", "MTBLC"));
    }
}

