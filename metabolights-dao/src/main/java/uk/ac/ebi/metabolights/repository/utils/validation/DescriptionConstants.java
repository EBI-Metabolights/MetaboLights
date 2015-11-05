package uk.ac.ebi.metabolights.repository.utils.validation;

/**
 * Created by kalai on 18/09/15.
 */
public class DescriptionConstants {

    //STUDY
    public static final String STUDY_IDENTIFIER = "Study Identifier";
    public static final String STUDY_TITLE = "Study Title";
    public static final String STUDY_DESCRIPTION = "Study Description";
    public static final String STUDY_DESIGN_DESCRIPTORS = "Study design descriptors";
    public static final String STUDY_MAX_ONE = "Maximum only one study found";
    public static final String STUDY_TEXT = "Study text successfully parsed";
    public static final String STUDY_CONTACT_EMAIL = "Study Contact(s) have listed email";

    //FACTORS
    public static final String FACTOR_NAME= "Study Factors";
    public static final String FACTOR_TYPE = "Study Factor Type";

    //ORGANISM
    public static final String ORGANISM_NAME= "Organism name";
    public static final String ORGANISM_PART = "Organism part";


    //PUBLICATIONS

    public static final String PUBLICATION_TITLE = "Publication Title";
    public static final String PUBLICATION_AUTHORS = "Authors of Publication";
    public static final String PUBLICATION_IDS  = "Publication IDs";


    public static final String STUDY_FACTORS = "Study factors used in the experiment";
    public static final String PROTOCOLS_ALL = "Comprehensive Experimental protocol";
    public static final String PROTOCOLS_MINIMUM = "Minimal Experimental protocol";
    public static final String PROTOCOLS_SAMPLE_COLLECTION = "Sample Collection protocol";
    public static final String PROTOCOLS_TEXT = "Protocols text successfully parsed";


    public static final String ASSAYS = "Assay(s)";
    public static final String ASSAY_SAMPLE_NAMEMATCH = "Sample Name consistency check";
    public static final String ASSAY_PLATFORM = "Assay platform information";
    public static final String ASSAY_FILES = "Assay has raw files referenced";
    public static final String ASSAY_FILES_IN_FILESYSTEM = "Assay referenced raw files are present in filesystem";
    public static final String ASSAY_ALL_MAF_REFERENCE = "All Assays have Metabolite Assignment File (MAF) referenced";
    public static final String ASSAY_ATLEAST_SOME_MAF_REFERENCE = "At least one assay has Metabolite Assignment Files (MAFs) referenced";

    public static final String ASSAY_MAF_FILE = "Metabolite Assignment File (MAF) is present in Study folder";
    public static final String ASSAY_CORRECT_MAF_FILE = "Metabolite Assignment File (MAF) has correct format";


    public static final String SAMPLES = "Sample(s)";
    public static final String PUBLICATIONS = "Publication(s) associated with this Study";
    public static final String ISATAB_INVESTIGATION = "ISA-Tab investigation file check";


    //OTHER
    public static final String EXCEPTION = "For any study we should be able to run all the validations";

    //FILES
    public static final String MAF_FILE = "Metabolite Identification file";
    public static final String MAF_FILE_ASSAY_CROSSCHECK = "Metabolites reported in Assays";
}
