package uk.ac.ebi.metabolights.referencelayer.importer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
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
            // Get a complete entity....
            Entity entity = chebiWS.getCompleteEntity(chebiId.getChebiId());

            mc.setAccession(chebiID2MetaboLightsID(chebiId.getChebiId()));
            mc.setChebiId(chebiId.getChebiId());
            mc.setName(chebiId.getChebiAsciiName());

            mc.setDescription(entity.getDefinition());
            mc.setInchi(entity.getInchi());
            mc.setFormula(extractFormula(entity.getFormulae()));
            mc.setIupacNames(extractIupacNames(entity.getIupacNames()));

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
        } catch (ChebiWebServiceFault_Exception e) {
            LOGGER.info("Can't get a Complete entity for " + chebiId.getChebiId() );
            return 0;
        }
    }

    private String extractIupacNames(List<DataItem> iupacNames){

        if (iupacNames.size()>0) {
            return iupacNames.iterator().next().getData();
        }

        return null;
    }

    private String extractFormula(List<DataItem> formulas){

        if (formulas.size()>0) {
            return formulas.iterator().next().getData();
        }

        return null;
    }


    private String chebiID2MetaboLightsID(String chebiID){
        return (chebiID.replaceFirst("CHEBI:", "MTBLC"));
    }
}

