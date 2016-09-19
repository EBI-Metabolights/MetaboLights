package uk.ac.ebi.metabolights.webservice.searchplugin;

import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kalai on 28/07/2016.
 */
public class ChebiSearch implements Serializable, Cloneable, Callable<CompoundSearchResult> {

    private CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.CHEBI);
    private SearchTermCategory searchTermCategory;
    private String[] rowMatchedFromCuratedFile;
    private String searchTerm;
    private boolean searchFromScratch = false;

    public ChebiSearch(SearchTermCategory searchTermCategory, String searchTerm) {
        this.searchTermCategory = searchTermCategory;
        this.searchTerm = searchTerm;
        this.searchFromScratch = true;
    }


    public ChebiSearch(SearchTermCategory searchTermCategory, String searchTerm, String[] rowMatchedFromCuratedFile) {
        this.searchTermCategory = searchTermCategory;
        this.rowMatchedFromCuratedFile = rowMatchedFromCuratedFile;
        this.searchTerm = searchTerm;
    }

    public ChebiSearch() {

    }


    private ChebiWebServiceClient chebiWS = GenericCompoundWSClients.getChebiWS();

    public ChebiWebServiceClient getChebiWS() {
        return chebiWS;
    }


    public CompoundSearchResult searchAndFillByName(String compoundName, String[] nameMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = extractChebiID(nameMatch, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID());
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(extractFormula(nameMatch, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
        }
        if (!compoundSearchResult.isComplete()) {
            fillWithMatchedRow(compoundSearchResult, nameMatch);
        }
        return compoundSearchResult;
    }

    private void fillWithMatchedRow(CompoundSearchResult compoundSearchResult, String[] matchedRow) {
        compoundSearchResult.setSmiles(matchedRow[CuratedMetabolitesFileColumnIdentifier.SMILES.getID()]);
        compoundSearchResult.setInchi(matchedRow[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()]);
        compoundSearchResult.setChebiId(matchedRow[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()]);
        compoundSearchResult.setFormula(matchedRow[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()]);
        compoundSearchResult.setName(matchedRow[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()]);
    }

    public void fillWithChebiCompleteEntity(String chebiID, CompoundSearchResult compoundSearchResult) {
        try {
            Entity chebiEntity = getChebiEntity(chebiID);
            if (chebiEntity == null) return;
            compoundSearchResult.setChebiId(chebiID);
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

    public CompoundSearchResult searchAndFillByName(String compoundName, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundName, SearchCategory.CHEBI_NAME);
            List<LiteEntity> resultList = entities.getListElement();
            if (!resultList.isEmpty()) {
                String matchingChebiID = getChEBIIDOfExactNameMatch(resultList, compoundName);
                if (!matchingChebiID.isEmpty()) {
                    fillWithChebiCompleteEntity(matchingChebiID, compoundSearchResult);
                }
            }
        } catch (ChebiWebServiceFault_Exception e) {
            System.err.println(e.getMessage());
        }
        return compoundSearchResult;
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


    private String extractChebiID(String[] matchingRow, String termToMatch, int indexToSearch) {
        String chebiID = matchingRow[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()];
        if (chebiID.contains("|")) {
            String[] chebiIDS = chebiID.split("\\|");
            return chebiIDS[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];
        } else {
            return chebiID;
        }
    }


    private String extractFormula(String[] matchingRow, String termToMatch, int indexToSearch) {
        String formula = matchingRow[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()];
        if (formula.contains("|")) {
            String[] formulas = formula.split("\\|");
            return formulas[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];

        } else {
            return formula;
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

    private int getMatchingIndex(String[] availableTerms, String termToMatch) {
        for (int i = 0; i < availableTerms.length; i++) {
            if (availableTerms[i].equalsIgnoreCase(termToMatch)) {
                return i;
            }
        }
        return 0;
    }

    public void searchAndFillByInChI(String compoundInChI, String[] inchiMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = extractChebiID(inchiMatch, compoundInChI, CuratedMetabolitesFileColumnIdentifier.INCHI.getID());
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(extractFormula(inchiMatch, compoundInChI, CuratedMetabolitesFileColumnIdentifier.INCHI.getID()));
        }
    }

    public boolean searchAndFillByInChI(String compoundInChI, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundInChI, SearchCategory.INCHI_INCHI_KEY);
            return fillSearchResults(entities, compoundSearchResult);
        } catch (ChebiWebServiceFault_Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public void searchAndFillBySMILES(String compoundSMILES, String[] smilesMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = extractChebiID(smilesMatch, compoundSMILES, CuratedMetabolitesFileColumnIdentifier.SMILES.getID());
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(extractFormula(smilesMatch, compoundSMILES, CuratedMetabolitesFileColumnIdentifier.SMILES.getID()));
        }
    }

    public boolean searchAndFillBySMILES(String compoundSMILES, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundSMILES, SearchCategory.SMILES);
            return fillSearchResults(entities, compoundSearchResult);
        } catch (ChebiWebServiceFault_Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean fillSearchResults(LiteEntityList entities, CompoundSearchResult compoundSearchResult) {
        List<LiteEntity> resultList = entities.getListElement();
        if (!resultList.isEmpty()) {
            String matchingChebiID = resultList.get(0).getChebiId();
            if (!matchingChebiID.isEmpty()) {
                fillWithChebiCompleteEntity(matchingChebiID, compoundSearchResult);
                return true;
            }
        }
        return false;
    }


    @Override
    public CompoundSearchResult call() throws Exception {
        if (this.searchTermCategory.equals(SearchTermCategory.INCHI)) {
            doInChISearch();
        } else if (this.searchTermCategory.equals(SearchTermCategory.NAME)) {
            doNameSearch();
        } else if (this.searchTermCategory.equals(SearchTermCategory.SMILES)) {
            doSMILESSearch();
        }
        return this.compoundSearchResult;
    }

    private void doNameSearch() {
        if (searchFromScratch) {
            searchAndFillByName(this.searchTerm, this.compoundSearchResult);
        } else {
            searchAndFillByName(this.searchTerm, this.rowMatchedFromCuratedFile, compoundSearchResult);
        }
    }

    private void doInChISearch() {
        if (searchFromScratch) {
            searchAndFillByInChI(this.searchTerm, this.compoundSearchResult);
        } else {
            searchAndFillByInChI(this.searchTerm, this.rowMatchedFromCuratedFile, compoundSearchResult);
        }
    }

    private void doSMILESSearch() {
        if (searchFromScratch) {
            searchAndFillBySMILES(this.searchTerm, this.compoundSearchResult);
        } else {
            searchAndFillBySMILES(this.searchTerm, this.rowMatchedFromCuratedFile, compoundSearchResult);
        }
    }
}
