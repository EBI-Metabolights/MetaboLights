package uk.ac.ebi.metabolights.referencelayer.model;

import java.sql.Date;

/**
 * Created by CS76Bot on 23/03/2017.
 */
public class Metabolite {
    // The public accession number of this compound.
    private String accession;

    // The name of this compound
    private String name;

    // The description of this compound
    private String description;

    // Standard inchi of the compound
    private String inchi;

    // ChEBI id
    private String chebiId;

    // Formula
    private String formula;

    // Iupac Names (separated by |)
    private String iupacNames;

    //Status (Always public so far)
    private String studyStatus = "PUBLIC";

    private boolean hasLiterature;

    private boolean hasReactions;

    private boolean hasSpecies;

    private boolean hasPathways;

    private boolean hasNMR;

    private boolean hasMS;

    private Date updatedDate;

}
