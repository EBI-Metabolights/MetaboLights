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
    public static final String FACTOR_IN_SAMPLES= "No Study Factor columns present in Sample Definition sheet";
    public static final String FACTOR_IN_ASSAYS= "No Study Factor columns present in Assay sheet(s)";
    public static final String FACTOR_IN_SAMPLES_ASSAYS= "Study Factor columns present in Sample Definitions or Assay sheets";
    public static final String FACTOR_COLUMNS_SAMPLES_ASSAYS= "Study Factor columns present in Sample Definitions or Assay sheets have no values assigned";

    //ORGANISM
    public static final String ORGANISM_NAME= "Organism name";
    public static final String ORGANISM_PART = "Organism part";


    //PUBLICATIONS
    public static final String PUBLICATIONS = "Publication(s) associated with this Study";
    public static final String PUBLICATION_TITLE = "Publication Title is missing";
    public static final String PUBLICATION_AUTHORS = "Publication Author list is missing";
    public static final String PUBLICATION_DOI  = "Publication DOI";
    public static final String PUBLICATION_IDS  = "Publication PubMed IDs";
    public static final String PUBLICATION_IDS_DOI  = "Publication PubMed ID and Publication DOI are missing";


    public static final String STUDY_FACTORS = "Study factors used in the experiment";
    public static final String PROTOCOLS_ALL = "Comprehensive Experimental protocol";
    public static final String PROTOCOLS_MINIMUM = "Minimal Experimental protocol";
    public static final String PROTOCOLS_SAMPLE_COLLECTION = "Sample Collection protocol description";
    public static final String PROTOCOLS_TEXT = "Protocols text successfully parsed";
    public static final String PROTOCOLS_EXTRACTION = "Extraction protocol description";
    public static final String PROTOCOLS_DATA_EXTRACTION = "Data transformation protocol description";

    public static final String MAF_PROTOCOLS = "Metabolite Identification protocol description";
    public static final String MS_PROTOCOLS_CROMA = "Chromatography protocol description";
    public static final String MS_PROTOCOLS_MASS_SPEC = "Mass spectrometry protocol description";
    public static final String NMR_PROTOCOLS_SPECTROSCOPY = "NMR spectroscopy protocol description";
    public static final String NMR_PROTOCOLS_NMR_ASSAY = "NMR assay protocol description";
    public static final String NMR_PROTOCOLS_NMR_SAMPLE = "NMR sample protocol description";


    public static final String ASSAYS = "Assay(s)";
    public static final String ASSAYS_EMPTY_COLUMNS = "Missing Study Assay sheet columns";
    public static final String ASSAY_SAMPLE_NAMEMATCH = "Sample Name consistency check";
    public static final String ASSAY_PLATFORM = "Assay platform information";
    public static final String ASSAY_FILES = "Assay has raw files referenced";
    public static final String ASSAY_FILES_IN_FILESYSTEM = "Assay referenced raw files detection in filesystem";
    public static final String ASSAY_CORRECT_RAW_FILE = "Raw files in the Assay(s) have the correct format";
    public static final String ASSAY_ALL_MAF_REFERENCE = "All Assays have Metabolite Assignment File (MAF) referenced";
    public static final String ASSAY_ATLEAST_SOME_MAF_REFERENCE = "At least one assay has Metabolite Assignment Files (MAFs) referenced";

    public static final String ASSAY_MAF_FILE = "Metabolite Assignment File (MAF) is present in Study folder";
    public static final String ASSAY_CORRECT_MAF_FILE = "Metabolite Assignment File (MAF) has correct format";


    public static final String SAMPLES = "Sample(s)";
    public static final String SAMPLES_EMPTY_COLUMNS = "Missing Study Sample Definition sheet columns";


    public static final String ISATAB_INVESTIGATION = "ISA-Tab investigation file check";


    //OTHER
    public static final String EXCEPTION = "For any study we should be able to run all the validations";

    //FILES
    public static final String MAF_FILE = "Metabolite Identification file";
    public static final String MAF_FILE_ASSAY_CROSSCHECK = "Metabolites reported in Assays";
    public static final String MAF_FILE_CONTENT = "Metabolite Identification File (MAF) content";
}
