package uk.ac.ebi.metabolights.repository.model.studyvalidator;

/**
 * Created by kalai on 18/09/15.
 */
public class DescriptionConstants {

    //STUDY
    public static final String STUDY_IDENTIFIER = "Study identifier";
    public static final String STUDY_TITLE = "Study title";
    public static final String STUDY_DESCRIPTION = "Study description";
    public static final String STUDY_DESIGN_DESCRIPTORS = "Study design descriptors";
    public static final String STUDY_MAX_ONE = "Maximum only one study found";

    //FACTORS
    public static final String FACTOR_NAME= "Study factors";
    public static final String FACTOR_TYPE = "Study factor type";

    //ORGANISM
    public static final String ORGANISM_NAME= "Organism name";
    public static final String ORGANISM_PART = "Organism part";


    //PUBLICATIONS

    public static final String PUBLICATION_TITLE = "Publication title";
    public static final String PUBLICATION_AUTHORS = "Authors of Publication";
    public static final String PUBLICATION_IDS  = "Publication IDs";


    public static final String STUDY_FACTORS = "Study factors used in the experiment";
    public static final String PROTOCOLS = "Experimental protocol(s) defined";
    public static final String ASSAYS = "Assay(s) reported";
    public static final String SAMPLES = "Sample(s) reported";
    public static final String PUBLICATIONS = "Publication(s) associated with this study";
    public static final String ISATAB = "Isatab file check";


    //OTHER
    public static final String EXCEPTION = "For any study we should be able to run all the validations";
}
