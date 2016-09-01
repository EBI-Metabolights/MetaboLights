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


    private ChebiWebServiceClient chebiWS = GenericCompoundWSClients.getChebiWS();

    public ChebiWebServiceClient getChebiWS() {
        return chebiWS;
    }


    public void searchAndFillByName(String compoundName, String[] nameMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = extractChebiID(nameMatch, compoundName);
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(extractFormula(nameMatch, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
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

    public boolean searchAndFillByName(String compoundName, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundName, SearchCategory.CHEBI_NAME);
            List<LiteEntity> resultList = entities.getListElement();
            if (!resultList.isEmpty()) {
                String matchingChebiID = getChEBIIDOfExactNameMatch(resultList, compoundName);
                if (!matchingChebiID.isEmpty()) {
                    fillWithChebiCompleteEntity(matchingChebiID, compoundSearchResult);
                    return true;
                }
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

    private LiteEntityList getChebiLiteEntityList(String name, SearchCategory category) throws ChebiWebServiceFault_Exception {
        return getChebiWS().getLiteEntity(name,
                category,
                200,
                StarsCategory.ALL);
    }

    private String getChEBIIDOfExactNameMatch(List<LiteEntity> entities, String compoundName) {
        for (int i = 0; i < entities.size(); i++) {
            LiteEntity liteEntity = entities.get(i);
            if (liteEntity.getChebiAsciiName().equalsIgnoreCase(compoundName)) {
                return liteEntity.getChebiId();
            }
        }
        return "";
    }


    private String extractChebiID(String[] nameMatch, String stringToMatch) {
        String chebiID = nameMatch[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()];
        if (chebiID.contains("|")) {
            String[] chebiIDS = chebiID.split("\\|");
            return chebiIDS[getMatchingIndex(extractCompoundName(nameMatch), stringToMatch)];

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

    private String extractFormula(String[] matchingRow, String termToMatch, int indexToSearch) {
        String formula = matchingRow[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()];
        if (formula.contains("|")) {
            String[] formulas = formula.split("\\|");
            return formulas[getMatchingIndex(extractSearchTerms(matchingRow,indexToSearch), termToMatch)];

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

    private String[] extractSearchTerms(String[] matchingRow, int searchIndex) {
        String searchTerm = matchingRow[searchIndex];
        if (searchTerm.contains("|")) {
            return searchTerm.split("\\|");
        } else {
            return new String[]{searchTerm};
        }
    }

    private String[] extractCompoundInChi(String[] inchiMatch) {
        String compoundName = inchiMatch[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()];
        if (compoundName.contains("|")) {
            return compoundName.split("\\|");
        } else {
            return new String[]{compoundName};
        }
    }

    private int getMatchingIndex(String[] availableTerms, String termToMatch) {
        for (int i = 0; i < availableTerms.length; i++) {
            if (availableTerms[i].equalsIgnoreCase(termToMatch)) {
                return i;
            }
        }
        return 0;
    }

    public void searchAndFillByInChI(String compoundInChI, String[] inchiMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = extractChebiID(inchiMatch, compoundInChI);
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(extractFormula(inchiMatch, compoundInChI,CuratedMetabolitesFileColumnIdentifier.INCHI.getID() ));
        }
    }

    public boolean searchAndFillByInChI(String compoundInChI, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundInChI, SearchCategory.INCHI_INCHI_KEY);
            List<LiteEntity> resultList = entities.getListElement();
            if (!resultList.isEmpty()) {
                String matchingChebiID = getChEBIIDOfExactInChIMatch(resultList, compoundInChI);
                if (!matchingChebiID.isEmpty()) {
                    fillWithChebiCompleteEntity(matchingChebiID, compoundSearchResult);
                    return true;
                }
            }
        } catch (ChebiWebServiceFault_Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return false;
    }

    private String getChEBIIDOfExactInChIMatch(List<LiteEntity> entities, String compoundName) {
        for (int i = 0; i < entities.size(); i++) {
            LiteEntity liteEntity = entities.get(i);
            if (liteEntity.getChebiAsciiName().equalsIgnoreCase(compoundName)) {
                return liteEntity.getChebiId();
            }
        }
        return "";
    }


}
