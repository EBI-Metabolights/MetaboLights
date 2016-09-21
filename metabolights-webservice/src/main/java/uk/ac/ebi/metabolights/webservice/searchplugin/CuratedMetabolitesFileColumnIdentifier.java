package uk.ac.ebi.metabolights.webservice.searchplugin;

/**
 * Created by kalai on 02/08/2016.
 */
public enum CuratedMetabolitesFileColumnIdentifier {
    CHEBI_ID(2),
    MOLECULAR_FORMULA(3),
    SMILES(4),
    INCHI(5),
    COMPOUND_NAME(6),
    PRIORITY(7);
    private final int id;

    CuratedMetabolitesFileColumnIdentifier(final int columnID) {
        id = columnID;
    }

    public int getID() {
        return id;
    }
}
