package uk.ac.ebi.metabolights.webservice.searchplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.metabolights.webservice.controllers.GenericCompoundWSController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by kalai on 28/07/2016.
 */
public class ChebiSearch implements Serializable, Cloneable, Callable<CompoundSearchResult> {
    private static Logger logger = LoggerFactory.getLogger(ChebiSearch.class);
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
        if (oneNameContainsManyID(nameMatch)) {
            straightAwayfillWithMatchedRow(compoundSearchResult, nameMatch);
            return compoundSearchResult;
        }
        try {
            String chebiID = CuratedMetaboliteTableUtilities.extractChebiID(nameMatch, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID());
            fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
            if (compoundSearchResult.getFormula() == null) {
                compoundSearchResult.setFormula(CuratedMetaboliteTableUtilities.extractFormula(nameMatch, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
            }
            if (!compoundSearchResult.isComplete()) {
                straightAwayfillWithMatchedRow(compoundSearchResult, nameMatch, compoundName);
            }
        } catch (Exception e) {
            logger.error("Something went wrong while filling result from curatedRow: " + e);
        }
        return compoundSearchResult;
    }

    private void straightAwayfillWithMatchedRow(CompoundSearchResult compoundSearchResult, String[] matchedRow) {
        try {
            compoundSearchResult.setSmiles(matchedRow[CuratedMetabolitesFileColumnIdentifier.SMILES.getID()]);
            compoundSearchResult.setInchi(matchedRow[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()]);
            compoundSearchResult.setDatabaseId(matchedRow[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()]);
            compoundSearchResult.setFormula(matchedRow[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()]);
            compoundSearchResult.setName(matchedRow[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()]);
        } catch (Exception e) {
            logger.error("Something went wrong while parsing row to fill result: " + e);
        }
    }

    private void straightAwayfillWithMatchedRow(CompoundSearchResult compoundSearchResult, String[] matchedRow, String compoundName) {
        try {
            compoundSearchResult.setSmiles(CuratedMetaboliteTableUtilities.extractSMILES(matchedRow, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
            compoundSearchResult.setInchi(CuratedMetaboliteTableUtilities.extractInChI(matchedRow, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
            compoundSearchResult.setDatabaseId(CuratedMetaboliteTableUtilities.extractChebiID(matchedRow, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
            compoundSearchResult.setFormula(CuratedMetaboliteTableUtilities.extractFormula(matchedRow, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
            compoundSearchResult.setName(CuratedMetaboliteTableUtilities.extractName(matchedRow, compoundName, CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()));
        } catch (Exception e) {
            logger.error("Something went wrong while parsing row to fill result: " + e);
        }
    }


    public void fillWithChebiCompleteEntity(String chebiID, CompoundSearchResult compoundSearchResult) {
        try {
            if (chebiID == null) return;
            Entity chebiEntity = getChebiEntity(chebiID);
            if (chebiEntity == null) return;
            compoundSearchResult.setDatabaseId(chebiID);
            compoundSearchResult.setSmiles(chebiEntity.getSmiles());
            compoundSearchResult.setInchi(chebiEntity.getInchi());
            compoundSearchResult.setFormula(processFromulaWithCharge(chebiEntity));
            compoundSearchResult.setName(chebiEntity.getChebiAsciiName());
        } catch (ChebiWebServiceFault_Exception e) {
            logger.error("While trying to access Chebi with id: ", e);
            e.printStackTrace();
        }
    }

    private String processFromulaWithCharge(Entity chebiEntity) {
        if (thisChebiResultisValid(chebiEntity.getFormulae())) {
            try {
                String formula = chebiEntity.getFormulae().get(0).getData();
                String charge = chebiEntity.getCharge();
                if (!charge.equals("0")) {
                    if (charge.equals("+1")) {
                        formula = formula + "+";
                    } else if (charge.equals("-1")) {
                        formula = formula + "-";
                    } else {
                        formula = formula + charge;
                    }
                }
                return formula;

            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public CompoundSearchResult searchAndFillByName(String compoundName, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundName, SearchCategory.ALL_NAMES);
            List<LiteEntity> resultList = entities.getListElement();
            if (!resultList.isEmpty()) {
                String matchingChebiID = getChEBIIDOfExactNameMatch(resultList, compoundName);
                if (!matchingChebiID.isEmpty()) {
                    String checkedChebiID = checkForAnionCase(compoundName, matchingChebiID);
                    fillWithChebiCompleteEntity(checkedChebiID, compoundSearchResult);
                }
            }
        } catch (ChebiWebServiceFault_Exception e) {
            logger.error("something went wrong when searching for name: " + compoundName, e);
        }
        return compoundSearchResult;
    }

    private boolean oneNameContainsManyID(String[] matchedRow) {
        if (!matchedRow[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()].contains("|") &&
                matchedRow[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()].contains("|")) {
            return true;
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
            if (Utilities.hit(liteEntity.getChebiAsciiName(), compoundName)) {
                return liteEntity.getChebiId();
            }
        }
        return getSynonymMatchFrom(entities, compoundName);
    }


    private String getSynonymMatchFrom(List<LiteEntity> entities, String compoundName) {
        ExecutorService executor = Executors.newFixedThreadPool(entities.size());
        List<Future<Entity>> searchResultsFromChebi = new ArrayList<Future<Entity>>();
        for (int i = 0; i < entities.size(); i++) {
            LiteEntity liteEntity = entities.get(i);
            searchResultsFromChebi.add(executor.submit(new ChebiEntitySearch(liteEntity.getChebiId())));
        }
        executor.shutdown();
        return extractMatchingChebiID(searchResultsFromChebi, compoundName);
    }

    private String extractMatchingChebiID(List<Future<Entity>> searchResultsFromChebi, String termToMatch) {
        for (Future<Entity> futureEntity : searchResultsFromChebi) {
            try {
                Entity entity = futureEntity.get();
                if (Utilities.hit(entity.getChebiAsciiName(), termToMatch)) {
                    return entity.getChebiId();
                }
                List<DataItem> synonyms = entity.getSynonyms();
                for (DataItem synonym : synonyms) {
                    if (Utilities.hit(synonym.getData(), termToMatch)) {
                        return entity.getChebiId();
                    }
                }
                List<DataItem> iupacNames = entity.getIupacNames();
                for (DataItem iupacName : iupacNames) {
                    if (Utilities.hit(iupacName.getData(), termToMatch)) {
                        return entity.getChebiId();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Something went wrong while extracting matching chebi id for " + termToMatch, e);
                continue;
            }
        }
        return "";
    }


    private String checkForAnionCase(String compoundName, String chebiID) throws ChebiWebServiceFault_Exception {
        if (isAnion(compoundName)) {
            return getChebiIDofConjugateAcid(chebiID);
        }
        return chebiID;
    }

    private boolean isAnion(String compoundName) {
        return compoundName.endsWith("ate");
    }

    private String getChebiIDofConjugateAcid(String anionChebiID) throws ChebiWebServiceFault_Exception {
        OntologyDataItemList ontologyChildren = getChebiWS().getOntologyChildren(anionChebiID);
        for (OntologyDataItem dataItem : ontologyChildren.getListElement()) {
            if (dataItem.getType().equalsIgnoreCase("is conjugate acid of") ||
                    dataItem.getType().equalsIgnoreCase("is conjugate base of")) {
                return dataItem.getChebiId();
            }
        }
        return anionChebiID;
    }


    public void searchAndFillByInChI(String compoundInChI, String[] inchiMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = CuratedMetaboliteTableUtilities.extractChebiID(inchiMatch, compoundInChI, CuratedMetabolitesFileColumnIdentifier.INCHI.getID());
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(CuratedMetaboliteTableUtilities.extractFormula(inchiMatch, compoundInChI, CuratedMetabolitesFileColumnIdentifier.INCHI.getID()));
        }
    }

    public boolean searchAndFillByInChI(String compoundInChI, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundInChI, SearchCategory.INCHI_INCHI_KEY);
            return fillSearchResults(entities, compoundSearchResult);
        } catch (ChebiWebServiceFault_Exception e) {
            logger.error("Something went wrong with the InChI search in ChEBI for " + compoundInChI, e);
            return false;
        }
    }

    public void searchAndFillBySMILES(String compoundSMILES, String[] smilesMatch, CompoundSearchResult compoundSearchResult) {
        String chebiID = CuratedMetaboliteTableUtilities.extractChebiID(smilesMatch, compoundSMILES, CuratedMetabolitesFileColumnIdentifier.SMILES.getID());
        fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
        if (compoundSearchResult.getFormula() == null) {
            compoundSearchResult.setFormula(CuratedMetaboliteTableUtilities.extractFormula(smilesMatch, compoundSMILES, CuratedMetabolitesFileColumnIdentifier.SMILES.getID()));
        }
    }

    public boolean searchAndFillBySMILES(String compoundSMILES, CompoundSearchResult compoundSearchResult) {
        try {
            LiteEntityList entities = getChebiLiteEntityList(compoundSMILES, SearchCategory.SMILES);
            return fillSearchResults(entities, compoundSearchResult);
        } catch (ChebiWebServiceFault_Exception e) {
            logger.error("Something went wrong with the SMILES search in ChEBI for " + compoundSMILES, e);
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

    private class ChebiEntitySearch implements Serializable, Cloneable, Callable<Entity> {

        public String chebiid;


        private ChebiWebServiceClient chebiWS = GenericCompoundWSClients.getChebiWS();

        public ChebiWebServiceClient getChebiWS() {
            return chebiWS;
        }

        public ChebiEntitySearch(String chebiid) {
            this.chebiid = chebiid;
        }

        @Override
        public Entity call() throws Exception {
            return getChebiWS().getCompleteEntity(this.chebiid);
        }
    }
}
