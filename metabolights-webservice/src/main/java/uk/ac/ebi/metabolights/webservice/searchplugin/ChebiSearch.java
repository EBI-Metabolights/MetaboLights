package uk.ac.ebi.metabolights.webservice.searchplugin;

import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalai on 28/07/2016.
 */
public class ChebiSearch {


    private ChebiWebServiceClient chebiWS =  GenericCompoundWSClients.getChebiWS();

    public ChebiWebServiceClient getChebiWS() {
        return chebiWS;
    }


    public void searchAndFill(String compoundName, String[] nameMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = extractChebiID(nameMatch, compoundName);
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(extractFormula(nameMatch, compoundName));
        }
    }

    private void fillWithChebiCompleteEntity(String chebiID, CompoundSearchResult compoundSearchResult) {
        try {
            compoundSearchResult.setChebiId(chebiID);
            Entity chebiEntity = getChebiEntity(chebiID);
            compoundSearchResult.setSmiles(chebiEntity.getSmiles());
            compoundSearchResult.setInchi(chebiEntity.getInchi());
            if (thisChebiResultisValid(chebiEntity.getFormulae())) {
                compoundSearchResult.setFormula(chebiEntity.getFormulae().get(0).getData());
            }
            compoundSearchResult.setName(chebiEntity.getChebiAsciiName());
        } catch (ChebiWebServiceFault_Exception e) {
            e.printStackTrace();
        }
    }

    public boolean searchAndFill(String compoundName, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntity(compoundName, SearchCategory.ALL_NAMES);
            List<LiteEntity> resultList = entities.getListElement();
            if (!resultList.isEmpty()) {
                LiteEntity liteEntity = resultList.get(0);
                String chebiID = liteEntity.getChebiId();
                fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
                return true;
            }

        } catch (ChebiWebServiceFault_Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return false;
    }

    private boolean thisChebiResultisValid(List<DataItem> result) {
        return result != null && result.size() != 0 ? true : false;
    }

    private Entity getChebiEntity(String chebiId) throws ChebiWebServiceFault_Exception {
        return getChebiWS().getCompleteEntity(chebiId);
    }

    private LiteEntityList getChebiLiteEntity(String name, SearchCategory category) throws ChebiWebServiceFault_Exception {
        return getChebiWS().getLiteEntity(name,
                category,
                1,
                StarsCategory.ALL);
    }



    private String extractChebiID(String[] nameMatch, String compoundName) {
        String chebiID = nameMatch[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()];
        if (chebiID.contains("|")) {
            String[] chebiIDS = chebiID.split("\\|");
            return chebiIDS[getMatchingIndex(extractCompoundName(nameMatch), compoundName)];

        } else {
            return chebiID;
        }
    }

    private String extractFormula(String[] nameMatch, String compoundName) {
        String formula = nameMatch[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()];
        if (formula.contains("|")) {
            String[] formulas = formula.split("\\|");
            return formulas[getMatchingIndex(extractCompoundName(nameMatch), compoundName)];

        } else {
            return formula;
        }
    }

    private String[] extractCompoundName(String[] nameMatch) {
        String compoundName = nameMatch[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()];
        if (compoundName.contains("|")) {
            return compoundName.split("\\|");
        } else {
            return new String[]{compoundName};
        }
    }

    private int getMatchingIndex(String[] compoundNames, String compoundName) {
        for (int i = 0; i < compoundNames.length; i++) {
            if (compoundNames[i].equalsIgnoreCase(compoundName)) {
                return i;
            }
        }
        return 0;
    }


}
