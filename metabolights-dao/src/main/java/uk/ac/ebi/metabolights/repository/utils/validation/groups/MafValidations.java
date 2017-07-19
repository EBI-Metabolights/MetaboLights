package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.*;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;

import java.io.*;
import java.util.*;

/**
 * Created by kalai on 21/10/15.
 */
public class MafValidations implements IValidationProcess {

    private static List<Assay> assaysWithNoMaf;
    private static Map<Assay,Integer> mafIndex_assaysWithMaf_map;
    private static File studiesLocation;

    public MafValidations(File studiesLocation) {
        this.studiesLocation = studiesLocation;
    }

    @Override
    public String getAbout() {
        return Group.FILES.toString();
    }

    @Override
    public Collection<Validation> getValidations(Study study) {
        assaysWithNoMaf = new ArrayList<>();
        mafIndex_assaysWithMaf_map = new LinkedHashMap<>();
        Collection<Validation> mafValidations = new LinkedList<>();
        int descriptionSize = ProtocolValidations.getMetaboliteIdentificationProtocolDescriptionSize(study);
        sortAssaysForMAFIn(study);
        if (metaboliteIdentificationProtocolIsPresent(descriptionSize)) {
            if (mafReferenceIsComprehensive()) {
                mafValidations.add(comprehensiveMafValidation());
            } else if (noMafReferencedIn(study)) {
                mafValidations.add(minimumMafReferenceValidation(study));
            } else {
                mafValidations.add(failComprehensiveMafCoverageValidation(study));
                mafValidations.add(minimumMafReferenceValidation(study));
            }
            if (mafIsPresentInSomeorAllAssaysIn(study)) {
                Validation maf_file_validation = mafPhysicalFilesValidation(study);
                mafValidations.add(maf_file_validation);
                if (maf_file_validation.getPassedRequirement()) {
//                    mafValidations.add(correctMafFormatValidation(study));
                    Validation correctMafFormatValidation = correctMafFormatValidation(study);
                    mafValidations.add(correctMafFormatValidation);
                    if (correctMafFormatValidation.getPassedRequirement()) {
                        Validation mafContentValidation = mafContentValidation(study);
                        mafValidations.add(mafContentValidation);
                    }
                }
            }
          }
        if (!noMafReferencedIn(study)) {
            if (descriptionSize <= 3) {
                mafValidations.add(metaboliteIdentificationProtocolContentValidation());
            }

        }

        return mafValidations;
    }

    private boolean metaboliteIdentificationProtocolIsPresent(int descriptionSize) {
        return descriptionSize != -1;
    }

    private static void sortAssaysForMAFIn(Study study) {
        for (Assay assay : study.getAssays()) {
            int mafIndex = getMAFIndex(assay.getAssayTable().getFields());
            if (mafIndex != -1) {
                if (!isMafReferenced(mafIndex, assay.getAssayTable().getData())) {
                    assaysWithNoMaf.add(assay);
                } else {
                    mafIndex_assaysWithMaf_map.put(assay,new Integer(mafIndex));
                }
            } else {
                assaysWithNoMaf.add(assay);
            }
        }
    }

    private boolean mafReferenceIsComprehensive() {
        return assaysWithNoMaf.size() == 0;
    }

    public static Validation comprehensiveMafValidation() {
        Validation comprehensiveMafValidation = new Validation(DescriptionConstants.ASSAY_ALL_MAF_REFERENCE, Requirement.OPTIONAL, Group.FILES);
        comprehensiveMafValidation.setId(ValidationIdentifier.ASSAY_ALL_MAF_REFERENCE.getID());
        return comprehensiveMafValidation;
    }

    private boolean noMafReferencedIn(Study study) {
        return assaysWithNoMaf.size() == study.getAssays().size();
    }

    public static Validation failComprehensiveMafCoverageValidation(Study study) {
        Validation all_assays_have_maf_reference_validation = comprehensiveMafValidation();
        if (assaysWithNoMaf.size() > 0) {
            all_assays_have_maf_reference_validation.setPassedRequirement(false);
            all_assays_have_maf_reference_validation.setMessage(getMafErrMessage(assaysWithNoMaf, study.getAssays().size()));
        }
        return all_assays_have_maf_reference_validation;
    }


    public static Validation minimumMafReferenceValidation(Study study) {
        Validation some_assays_have_maf_reference_validation = new Validation(DescriptionConstants.ASSAY_ATLEAST_SOME_MAF_REFERENCE, Requirement.MANDATORY, Group.FILES);
        some_assays_have_maf_reference_validation.setId(ValidationIdentifier.ASSAY_ATLEAST_SOME_MAF_REFERENCE.getID());
        if (assaysWithNoMaf.size() == study.getAssays().size()) {
            some_assays_have_maf_reference_validation.setPassedRequirement(false);
            some_assays_have_maf_reference_validation.setMessage("Metabolite identification protocol is described " +
                    "but no Metabolite Assignment File (MAF) is referenced in the Assay table");
        }
        return some_assays_have_maf_reference_validation;
    }


    private boolean mafIsPresentInSomeorAllAssaysIn(Study study) {
        return assaysWithNoMaf.size() != study.getAssays().size();
    }

    public static Validation mafPhysicalFilesValidation(Study study) {
        Validation maf_file_validation = new Validation(DescriptionConstants.ASSAY_MAF_FILE, Requirement.MANDATORY, Group.FILES);
        maf_file_validation.setId(ValidationIdentifier.ASSAY_MAF_FILE.getID());
        List<String> notPresentInStudyFolder = referencedMafFilesNotPresentInFileSystem(mafIndex_assaysWithMaf_map, study);
        if (notPresentInStudyFolder.size() > 0) {
            maf_file_validation.setPassedRequirement(false);
            maf_file_validation.setMessage(getMafFileErrMessage(notPresentInStudyFolder));
        }
        return maf_file_validation;
    }

    public static Validation correctMafFormatValidation(Study study) {
        Validation maf_file_correct_format_validation = new Validation(DescriptionConstants.ASSAY_CORRECT_MAF_FILE, Requirement.MANDATORY, Group.FILES);
        maf_file_correct_format_validation.setId(ValidationIdentifier.ASSAY_CORRECT_MAF_FILE.getID());
        Map<Assay,Integer> mafIndex_assaysWithIncorrectMaf_map = getIncorrectMafFileNames(mafIndex_assaysWithMaf_map);
        if (mafIndex_assaysWithIncorrectMaf_map.size() > 0) {
            maf_file_correct_format_validation.setPassedRequirement(false);
            maf_file_correct_format_validation.setMessage(getIncorrectMafErrMsg(mafIndex_assaysWithIncorrectMaf_map));
        }
        return maf_file_correct_format_validation;
    }

    private static int getMAFIndex(LinkedHashMap<String, Field> tableFields) {
        int i = -1;
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            i++;
            if (entry.getKey().contains("metabolite assignment file")) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isMafReferenced(int index, List<List<String>> tableData) {
        for (List<String> data : tableData) {
            if (!data.get(index).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static String getMafErrMessage(List<Assay> assaysWithNoMaf, int assaySize) {
        if (assaySize == 1) return "Metabolite identification protocol is described" +
                " but no Metabolite Assignment File (MAF) is referenced in the Assay table";
        String errMessage = "Metabolite identification protocol is described " +
                "but the following Assay(s) have no Metabolite Assignment File (MAF) reference:";
        for (int i = 0; i < assaysWithNoMaf.size(); i++) {
            errMessage += " Assay " + assaysWithNoMaf.get(i).getAssayNumber();
            if (i < assaysWithNoMaf.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }

    public static List<String>  referencedMafFilesNotPresentInFileSystem(Map<Assay,Integer> mafIndex_assaysWithMaf_map, Study study) {
        List<String> notPresentInStudyFolder = new ArrayList<>();
        List<String> fileNames = Utilities.getFileNamesInDirectory(study.getStudyLocation());
        for (String maf_file : getMafFileNames(mafIndex_assaysWithMaf_map)) {
            if (!fileNames.contains(maf_file)) {
                notPresentInStudyFolder.add(maf_file);
            }
        }
        return notPresentInStudyFolder;
    }

    private static List<String> getMafFileNames(Map<Assay,Integer> mafIndex_assaysWithMaf_map) {
        List<String> mafFileNames = new ArrayList<>();
        for (Map.Entry<Assay,Integer> entry : mafIndex_assaysWithMaf_map.entrySet()) {
            int index = entry.getValue().intValue();
            String maf_file_name = entry.getKey().getAssayTable().getData().get(0).get(index);
            if (!maf_file_name.isEmpty()) {
                mafFileNames.add(maf_file_name);
            }
        }
        return mafFileNames;
    }

    private static String getMafFileErrMessage(List<String> notPresentInStudyFolder) {
        String errMessage = "The following referenced Metabolite Assignment Files (MAFs) are not present in Study folder:";
        for (int i = 0; i < notPresentInStudyFolder.size(); i++) {
            errMessage += " " + notPresentInStudyFolder.get(i);
            if (i < notPresentInStudyFolder.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }

    private static Map<Assay,Integer> getIncorrectMafFileNames(Map<Assay,Integer> mafIndex_assaysWithMaf_map) {
        Map<Assay,Integer> mafIndex_assaysWithIncorrectMaf_map = new LinkedHashMap<>();

        for (Map.Entry<Assay,Integer> entry : mafIndex_assaysWithMaf_map.entrySet()) {
            int index = entry.getValue().intValue();
            String maf_file_name = entry.getKey().getAssayTable().getData().get(0).get(index);
            if (!maf_file_name.isEmpty()) {
                if (!maf_file_name.startsWith("m_")) {
                    mafIndex_assaysWithIncorrectMaf_map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return mafIndex_assaysWithIncorrectMaf_map;
    }

    private static String getIncorrectMafErrMsg(Map<Assay,Integer> mafIndex_assaysWithIncorrectMaf_map) {
        String message = "The following Metabolite Assignment File(s) (MAF(s)) has wrong format: ";
        for (Map.Entry<Assay,Integer> entry : mafIndex_assaysWithIncorrectMaf_map.entrySet()) {
            int index = entry.getValue().intValue();
            String maf_file_name = entry.getKey().getAssayTable().getData().get(0).get(index);
            message += maf_file_name + " (Assay " + entry.getKey().getAssayNumber() + ");";
        }
        return message;
    }

    public static Validation mafContentValidation(Study study) {
        Validation maf_file_content_validation = new Validation(DescriptionConstants.MAF_FILE_CONTENT, Requirement.MANDATORY, Group.FILES);
        maf_file_content_validation.setId(ValidationIdentifier.MAF_FILE_CONTENT.getID());
        Map<Assay, Integer> mafIndex_assaysWithEmptytMaf_map = getEmptyMafFileNames(mafIndex_assaysWithMaf_map, study.getStudyIdentifier());
        if (mafIndex_assaysWithEmptytMaf_map.size() > 0) {
            maf_file_content_validation.setPassedRequirement(false);
            maf_file_content_validation.setMessage(getNoContentMafErrMsg(mafIndex_assaysWithEmptytMaf_map));
        }
        return maf_file_content_validation;
    }

    private static Map<Assay, Integer> getEmptyMafFileNames(Map<Assay, Integer> mafIndex_assaysWithMaf_map, String studyID) {
        Map<Assay, Integer> mafIndex_assaysWithEmptytMaf_map = new LinkedHashMap<>();

        for (Map.Entry<Assay, Integer> entry : mafIndex_assaysWithMaf_map.entrySet()) {
            int index = entry.getValue().intValue();
            String maf_file_name = entry.getKey().getAssayTable().getData().get(0).get(index);
            if (!maf_file_name.isEmpty()) {
                if (maf_file_name.startsWith("m_")) {
                    BufferedReader TSVFile =
                            null;
                    try {
                        String mafFile = studiesLocation + File.separator + studyID + File.separator + maf_file_name;
                        ;
                        TSVFile = new BufferedReader(new FileReader(mafFile));


                        String dataRow = TSVFile.readLine(); // Read first line.
                        int count = 0;
                        if (dataRow != null) {
                            while (dataRow != null) {
                                count++;
                                if (count >= 2) {
                                    break;
                                }
                                dataRow = TSVFile.readLine();
                            }
                            if (count < 2) {
                                mafIndex_assaysWithEmptytMaf_map.put(entry.getKey(), entry.getValue());
                            }
                        } else {
                            mafIndex_assaysWithEmptytMaf_map.put(entry.getKey(), entry.getValue());
                        }


                        TSVFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mafIndex_assaysWithEmptytMaf_map;
    }

    private static String getNoContentMafErrMsg(Map<Assay, Integer> mafIndex_assaysWithEmptytMaf_map) {
        String message = "The following Metabolite Assignment File(s) (MAF(s)) is/are empty: ";
        for (Map.Entry<Assay, Integer> entry : mafIndex_assaysWithEmptytMaf_map.entrySet()) {
            int index = entry.getValue().intValue();
            String maf_file_name = entry.getKey().getAssayTable().getData().get(0).get(index);
            message += maf_file_name + " (Assay " + entry.getKey().getAssayNumber() + ");";
        }
        return message;
    }

    public static Validation metaboliteIdentificationProtocolContentValidation() {
        Validation mi_protocol_correct_format_validation = new Validation(DescriptionConstants.MAF_PROTOCOLS, Requirement.MANDATORY, Group.PROTOCOLS);
        mi_protocol_correct_format_validation.setId(ValidationIdentifier.MI_PROTOCOL_CONTENT.getID());
        mi_protocol_correct_format_validation.setPassedRequirement(false);
        mi_protocol_correct_format_validation.setMessage("Metabolite Assignment File(s) are referenced, but Metabolite Identification protocol is either not sufficiently described or absent");
        return mi_protocol_correct_format_validation;
    }

}
