package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;

import java.util.*;

/**
 * Created by kalai on 21/10/15.
 */
public class MafValidations implements IValidationProcess {

    private static List<Assay> assaysWithNoMaf;
    private static Map<Integer, Assay> mafIndex_assaysWithMaf_map;

    @Override
    public String getAbout() {
        return Group.FILES.toString();
    }

    @Override
    public Collection<Validation> getValidations(Study study) {
        assaysWithNoMaf = new ArrayList<>();
        mafIndex_assaysWithMaf_map = new LinkedHashMap<>();
        Collection<Validation> mafValidations = new LinkedList<>();
        if (ProtocolValidations.metaboliteIdentificationProtocolIsPresent(study)) {
            sortAssaysForMAFIn(study);
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
                    mafValidations.add(correctMafFormatValidation(study));
                }
            }
        }
        return mafValidations;
    }

    private static void sortAssaysForMAFIn(Study study) {
        for (Assay assay : study.getAssays()) {
            int mafIndex = getMAFIndex(assay.getAssayTable().getFields());
            if (mafIndex != -1) {
                if (!isMafReferenced(mafIndex, assay.getAssayTable().getData())) {
                    assaysWithNoMaf.add(assay);
                } else {
                    mafIndex_assaysWithMaf_map.put(new Integer(mafIndex), assay);
                }
            }
        }
    }

    private boolean mafReferenceIsComprehensive() {
        return assaysWithNoMaf.size() == 0;
    }

    public static Validation comprehensiveMafValidation() {
        Validation comprehensiveMafValidation = new Validation(DescriptionConstants.ASSAY_ALL_MAF_REFERENCE, Requirement.OPTIONAL, Group.FILES);
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
        all_assays_have_maf_reference_validation.setStatus();
        return all_assays_have_maf_reference_validation;
    }


    public static Validation minimumMafReferenceValidation(Study study) {
        Validation some_assays_have_maf_reference_validation = new Validation(DescriptionConstants.ASSAY_ATLEAST_SOME_MAF_REFERENCE, Requirement.MANDATORY, Group.FILES);
        if (assaysWithNoMaf.size() == study.getAssays().size()) {
            some_assays_have_maf_reference_validation.setPassedRequirement(false);
            some_assays_have_maf_reference_validation.setMessage("Metabolite identification protocol is described \n" +
                    "but no MAF file is referenced in the Assay(s)");
        }
        some_assays_have_maf_reference_validation.setStatus();
        return some_assays_have_maf_reference_validation;
    }


    private boolean mafIsPresentInSomeorAllAssaysIn(Study study) {
        return assaysWithNoMaf.size() != study.getAssays().size();
    }

    public static Validation mafPhysicalFilesValidation(Study study) {
        Validation maf_file_validation = new Validation(DescriptionConstants.ASSAY_MAF_FILE, Requirement.MANDATORY, Group.FILES);
        List<String> notPresentInStudyFolder = referencedMafFilesNotPresentInFileSystem(mafIndex_assaysWithMaf_map, study);
        if (notPresentInStudyFolder.size() > 0) {
            maf_file_validation.setPassedRequirement(false);
            maf_file_validation.setMessage(getMafFileErrMessage(notPresentInStudyFolder));
        }
        maf_file_validation.setStatus();
        return maf_file_validation;
    }

    public static Validation correctMafFormatValidation(Study study) {
        Validation maf_file_correct_format_validation = new Validation(DescriptionConstants.ASSAY_CORRECT_MAF_FILE, Requirement.MANDATORY, Group.FILES);
        Map<Integer, Assay> mafIndex_assaysWithIncorrectMaf_map = getIncorrectMafFileNames(mafIndex_assaysWithMaf_map);
        if (mafIndex_assaysWithIncorrectMaf_map.size() > 0) {
            maf_file_correct_format_validation.setPassedRequirement(false);
            maf_file_correct_format_validation.setMessage(getIncorrectMafErrMsg(mafIndex_assaysWithIncorrectMaf_map));
        }
        maf_file_correct_format_validation.setStatus();
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
                " but no MAF file is referenced in the Assay table";
        String errMessage = "Metabolite identification protocol is described " +
                "but the following assay(s) have no MAF file reference:";
        for (int i = 0; i < assaysWithNoMaf.size(); i++) {
            errMessage += " Assay " + assaysWithNoMaf.get(i).getAssayNumber();
            if (i < assaysWithNoMaf.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }

    public static List<String> referencedMafFilesNotPresentInFileSystem(Map<Integer, Assay> mafIndex_assaysWithMaf_map, Study study) {
        List<String> notPresentInStudyFolder = new ArrayList<>();
        List<String> fileNames = Utilities.getFileNamesInDirectory(study.getStudyLocation());
        for (String maf_file : getMafFileNames(mafIndex_assaysWithMaf_map)) {
            if (!fileNames.contains(maf_file)) {
                notPresentInStudyFolder.add(maf_file);
            }
        }
        return notPresentInStudyFolder;
    }

    private static List<String> getMafFileNames(Map<Integer, Assay> mafIndex_assaysWithMaf_map) {
        List<String> mafFileNames = new ArrayList<>();
        for (Map.Entry<Integer, Assay> entry : mafIndex_assaysWithMaf_map.entrySet()) {
            int index = entry.getKey().intValue();
            String maf_file_name = entry.getValue().getAssayTable().getData().get(0).get(index);
            if (!maf_file_name.isEmpty()) {
                mafFileNames.add(maf_file_name);
            }
        }
        return mafFileNames;
    }

    private static String getMafFileErrMessage(List<String> notPresentInStudyFolder) {
        String errMessage = "The following referenced MAF files are not present in study folder:";
        for (int i = 0; i < notPresentInStudyFolder.size(); i++) {
            errMessage += " " + notPresentInStudyFolder.get(i);
            if (i < notPresentInStudyFolder.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }

    private static Map<Integer, Assay> getIncorrectMafFileNames(Map<Integer, Assay> mafIndex_assaysWithMaf_map) {
        Map<Integer, Assay> mafIndex_assaysWithIncorrectMaf_map = new LinkedHashMap<>();

        for (Map.Entry<Integer, Assay> entry : mafIndex_assaysWithMaf_map.entrySet()) {
            int index = entry.getKey().intValue();
            String maf_file_name = entry.getValue().getAssayTable().getData().get(0).get(index);
            if (!maf_file_name.isEmpty()) {
                if (!maf_file_name.startsWith("m_")) {
                    mafIndex_assaysWithIncorrectMaf_map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return mafIndex_assaysWithIncorrectMaf_map;
    }

    private static String getIncorrectMafErrMsg(Map<Integer, Assay> mafIndex_assaysWithIncorrectMaf_map) {
        String message = "The following MAF file(s) are of incorrect format: ";
        for (Map.Entry<Integer, Assay> entry : mafIndex_assaysWithIncorrectMaf_map.entrySet()) {
            int index = entry.getKey().intValue();
            String maf_file_name = entry.getValue().getAssayTable().getData().get(0).get(index);
            message += maf_file_name + " (Assay " + entry.getValue().getAssayNumber() + ");";
        }
        return message;
    }

}
