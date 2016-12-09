package uk.ac.ebi.metabolights.webservice.searchplugin;

import com.chemspider.www.MassSpecAPIStub;
import com.chemspider.www.SearchStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.webservice.controllers.GenericCompoundWSController;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kalai on 05/08/2016.
 */
public class ChemSpiderSearch implements Serializable, Cloneable, Callable<Collection<CompoundSearchResult>> {
    private static Logger logger = LoggerFactory.getLogger(ChemSpiderSearch.class);
    private final String ChemSpiderToken = PropertiesUtil.getProperty("chemspiderSecurityToken");
    private List<CompoundSearchResult> compoundSearchResults = new ArrayList<CompoundSearchResult>();
    private String searchTerm;

    public ChemSpiderSearch() {

    }

    public ChemSpiderSearch(String searchTerm) {
        this.searchTerm = searchTerm;
    }


    public List<CompoundSearchResult> searchAndFill(String searchTerm) {
        int[] id = getChemSpiderID(searchTerm);
        if (id.length == 0) return compoundSearchResults;
        getExtendedCompoundInfoArrayAndFill(id);
        return compoundSearchResults;
    }

    private int[] getChemSpiderID(String name) {
        int[] matchingIDs = get_Search_SimpleSearch_Results(name, this.ChemSpiderToken);
        if (matchingIDs == null || matchingIDs.length == 0) return new int[0];
        return matchingIDs;
    }

    /**
     * Function to call the SimpleSearch operation of ChemSpider's Search SOAP 1.2 webservice (http://www.chemspider.com/search.asmx?op=SimpleSearch)
     * Search by Name, SMILES, InChI, InChIKey, etc. Returns a list of found CSIDs (first 100 - please use AsyncSimpleSearch instead if you like to get the full list). Security token is required.
     *
     * @param query: String representing search term (can be Name, SMILES, InChI, InChIKey)
     * @param token: string containing your user token (listed at your http://www.chemspider.com/UserProfile.aspx page)
     * @return: int[] array containing the ChemSpider IDs. If more than 100 are found then only the first 100 are returned.
     */
    private int[] get_Search_SimpleSearch_Results(String query, String token) {
        int[] output = null;
        try {
            final SearchStub thisSearchStub = new SearchStub();
            SearchStub.SimpleSearch SimpleSearchInput = new SearchStub.SimpleSearch();
            SimpleSearchInput.setQuery(query);
            SimpleSearchInput.setToken(token);
            final SearchStub.SimpleSearchResponse thisSimpleSearchResponse = thisSearchStub.simpleSearch(SimpleSearchInput);
            output = thisSimpleSearchResponse.getSimpleSearchResult().get_int();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Something went wrong while searching for " + query + " in ChemSpider", e);
            return new int[0];
        }
        return output;
    }

    /**
     * Function to call the GetExtendedCompoundInfoArray operation of ChemSpider's MassSpecAPI SOAP 1.2 webservice (http://www.chemspider.com/massspecapi.asmx?op=GetExtendedCompoundInfoArray)
     * Get array of extended record details by an array of CSIDs. Security token is required.
     *
     * @param csids: integer array containing the CSIDs of compounds for which information will be returned
     * @return: a Map> containing the results array for each CSID (with Properties CSID, MF, SMILES, InChIKey, AverageMass, MolecularWeight, MonoisotopicMass, NominalMass, ALogP, XLogP, CommonName)
     */
    private void getExtendedCompoundInfoArrayAndFill(int[] csids) {
        try {
            final MassSpecAPIStub thisMassSpecAPIStub = new MassSpecAPIStub();
            MassSpecAPIStub.ArrayOfInt inputCSIDsArrayofInt = new MassSpecAPIStub.ArrayOfInt();
            inputCSIDsArrayofInt.set_int(csids);
            MassSpecAPIStub.GetExtendedCompoundInfoArray extendedCompoundInfoArrayInput = new MassSpecAPIStub.GetExtendedCompoundInfoArray();
            extendedCompoundInfoArrayInput.setCSIDs(inputCSIDsArrayofInt);
            extendedCompoundInfoArrayInput.setToken(this.ChemSpiderToken);
            final MassSpecAPIStub.GetExtendedCompoundInfoArrayResponse thisGetExtendedCompoundInfoArrayResponse = thisMassSpecAPIStub.getExtendedCompoundInfoArray(extendedCompoundInfoArrayInput);
            MassSpecAPIStub.ExtendedCompoundInfo[] extendedCompoundInfoOutput = thisGetExtendedCompoundInfoArrayResponse.getGetExtendedCompoundInfoArrayResult().getExtendedCompoundInfo();
            for (int i = 0; i < extendedCompoundInfoOutput.length; i++) {
                this.compoundSearchResults.add(getResult(extendedCompoundInfoOutput, i));
            }

        } catch (Exception e) {
            logger.error("Something went wrong while extracting search results in ChemSpider for: " + searchTerm, e);
        }

    }

    private CompoundSearchResult getResult(MassSpecAPIStub.ExtendedCompoundInfo[] extendedCompoundInfoOutput, int index) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.CHEMSPIDER);
        String smiles = extendedCompoundInfoOutput[index].getSMILES();
        String inchi = extendedCompoundInfoOutput[index].getInChI();
        String commonName = extendedCompoundInfoOutput[index].getCommonName();
        String molFormula = extendedCompoundInfoOutput[index].getMF();
        int csid = extendedCompoundInfoOutput[index].getCSID();
        compoundSearchResult.setName(commonName);
        compoundSearchResult.setSmiles(smiles);
        compoundSearchResult.setInchi(inchi);
        compoundSearchResult.setFormula(format(molFormula));
        compoundSearchResult.setDatabaseId(appendNameSpaceAndConvert(csid));
        return compoundSearchResult;

    }

    private String format(String formula) {
        String formatted = formula.replaceAll("_", "");
        formatted = formatted.replaceAll("\\{", "");
        formatted = formatted.replaceAll("\\}", "");
        return formatted;
    }

    private String appendNameSpaceAndConvert(int csid) {
        return "CSID " + new Integer(csid).toString();
    }

    @Override
    public List<CompoundSearchResult> call() throws Exception {
        return searchAndFill(this.searchTerm);
    }
}

