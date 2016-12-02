package uk.ac.ebi.metabolights.webservice.searchplugin;

/**
 * Created by kalai on 02/11/2016.
 */
public class CuratedMetaboliteTableUtilities {

    public static String extractChebiID(String[] matchingRow, String termToMatch, int indexToSearch) {
        String chebiID = matchingRow[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()];
        if (chebiID == null) return chebiID;
        if (chebiID.contains("|")) {
            String[] chebiIDS = chebiID.split("\\|");
            return chebiIDS[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];
        } else {
            return chebiID;
        }
    }


    public static  String extractFormula(String[] matchingRow, String termToMatch, int indexToSearch) {
        String formula = matchingRow[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()];
        if (formula == null) return formula;
        if (formula.contains("|")) {
            String[] formulas = formula.split("\\|");
            return formulas[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];

        } else {
            return formula;
        }
    }

    public static  String extractInChI(String[] matchingRow, String termToMatch, int indexToSearch) {
        String inchi = matchingRow[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()];
        if (inchi == null) return inchi;
        if (inchi.contains("|")) {
            String[] inchis = inchi.split("\\|");
            return inchis[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];

        } else {
            return inchi;
        }
    }

    public static  String extractSMILES(String[] matchingRow, String termToMatch, int indexToSearch) {
        String smiles = matchingRow[CuratedMetabolitesFileColumnIdentifier.SMILES.getID()];
        if (smiles == null) return smiles;
        if (smiles.contains("|")) {
            String[] smilesList = smiles.split("\\|");
            return smilesList[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];

        } else {
            return smiles;
        }
    }

    public static  String extractName(String[] matchingRow, String termToMatch, int indexToSearch) {
        String name = matchingRow[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()];
        if (name == null) return name;
        if (name.contains("|")) {
            String[] names = name.split("\\|");
            return names[getMatchingIndex(extractSearchTerms(matchingRow, indexToSearch), termToMatch)];

        } else {
            return name;
        }
    }

    public static  String[] extractSearchTerms(String[] matchingRow, int searchIndex) {
        String searchTerm = matchingRow[searchIndex];
        if (searchTerm.contains("|")) {
            return searchTerm.split("\\|");
        } else {
            return new String[]{searchTerm};
        }
    }

    public static  int getMatchingIndex(String[] availableTerms, String termToMatch) {
        for (int i = 0; i < availableTerms.length; i++) {
            String current = availableTerms[i].trim();
            String toMatch = termToMatch.trim();
            if (current.equalsIgnoreCase(toMatch)) {
                return i;
            }
        }
        return 0;
    }

}
