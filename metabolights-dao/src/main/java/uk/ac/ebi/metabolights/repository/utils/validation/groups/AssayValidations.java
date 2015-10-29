package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;

import java.io.File;
import java.util.*;

/**
 * Created by kalai on 01/10/15.
 */

public class AssayValidations implements IValidationProcess {

    private static int[] indicesToCrosscheck;


    @Override
    public String getAbout() {
        return Group.ASSAYS.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> assayValidations = new LinkedList<>();
        //assayValidations.add(getAssayValidation(study));
        assayValidations.addAll(getAssayValidations(study));
        return assayValidations;
    }

    public static Validation getAssayValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAYS, Requirement.MANDATORY, Group.ASSAYS);
        if (study.getAssays().isEmpty()) {
            validation.setMessage("No assay data is provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    public static Collection<Validation> getAssayValidations(Study study) {
        Collection<Validation> assayValidations = new LinkedList<>();
        Validation validation = new Validation(DescriptionConstants.ASSAYS, Requirement.MANDATORY, Group.ASSAYS);
        if (study.getAssays().isEmpty()) {
            validation.setMessage("No assay data is provided");
            validation.setPassedRequirement(false);
        } else {
            assayValidations.add(getAssayHasPlatformInfoValidation(study));
            Validation assayRawFileValidation = getAssayHasFilesValidation(study);
            assayValidations.add(assayRawFileValidation);
            assayValidations.add(referencedFilesArePresentInFileSystem(study, assayRawFileValidation.getPassedRequirement()));
        }
        assayValidations.add(validation);
        validation.setStatus();
        return assayValidations;
    }

    public static Validation getAssayHasPlatformInfoValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_PLATFORM, Requirement.OPTIONAL, Group.ASSAYS);
        List<Integer> assaysWithNoPlatform = new ArrayList<>();
        for (Assay assay : study.getAssays()) {
            if (assay.getPlatform().isEmpty()) {
                assaysWithNoPlatform.add(assay.getAssayNumber());
            }
        }
        if (assaysWithNoPlatform.size() > 0) {
            validation.setPassedRequirement(false);
            validation.setMessage(getPlatformErrMsg(assaysWithNoPlatform, study.getAssays().size()));
        }

        validation.setStatus();
        return validation;
    }

    private static String getPlatformErrMsg(List<Integer> assaysWithNoPlatform, int assaySize) {
        if (assaySize == 1) {
            return "Assay has no platform information";
        }
        String errMessage = "The following assays have no platform information:";
        for (int i = 0; i < assaysWithNoPlatform.size(); i++) {
            errMessage += " Assay " + assaysWithNoPlatform.get(i);
            if (i < assaysWithNoPlatform.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }

    public static Validation getAssayHasFilesValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_FILES, Requirement.MANDATORY, Group.FILES);
        for (Assay assay : study.getAssays()) {
            List<String> fileFields = getFileFieldsExceptMAFFrom(assay.getAssayTable().getFields());
            List<String> fileColumnsThatAreEmpty = new ArrayList<>();
            for (String fileField : fileFields) {
                if (!thisFileColumnHasFilesReferenced(fileField, assay.getAssayTable().getData())) {
                    fileColumnsThatAreEmpty.add(fileField);
                }
            }
            if (fileColumnsThatAreEmpty.size() == fileFields.size()) {
                validation.setPassedRequirement(false);
                validation.setMessage(getErrMessage(assay, fileColumnsThatAreEmpty));
                validation.setStatus();
                return validation;
            }
        }

        validation.setStatus();
        return validation;
    }

    private static List<String> getFileFieldsFrom(LinkedHashMap<String, Field> tableFields) {
        List<String> fileFields = new ArrayList<>();
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            if (Utilities.containsKeyword(entry.getKey(), " file")) {
                fileFields.add(entry.getKey());
            }
        }
        return fileFields;
    }


    private static List<String> getFileFieldsExceptMAFFrom(LinkedHashMap<String, Field> tableFields) {
        List<String> fileFields = new ArrayList<>();
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            if (Utilities.containsKeyword(entry.getKey()," file")) {
                if (!Utilities.getfieldName(entry.getKey()).equalsIgnoreCase("metabolite assignment file")) {
                    fileFields.add(entry.getKey());
                }
            }
        }
        return fileFields;
    }

    private static List<String> getFileFieldsThatHasRefValue(List<String> fileFields, Table assayData) {
        List<String> fileColumnsThatAreEmpty = new ArrayList<>();
        for (String fileField : fileFields) {
            if (!thisFileColumnHasFilesReferenced(fileField, assayData.getData())) {
                fileColumnsThatAreEmpty.add(fileField);
            }
        }
        return fileColumnsThatAreEmpty;
    }

    private static boolean thisFileColumnHasFilesReferenced(String fileColumn, List<List<String>> tableData) {
        int index = Utilities.indexToCheck(fileColumn);
        for (List<String> data : tableData) {
            if (!data.get(index).isEmpty()) {
                return true;
            }
        }
        return false;
    }


    private static String getErrMessage(Assay assay, List<String> fileColumnsThatAreEmpty) {
        String errMessage = "";
        int assayNumber = assay.getAssayNumber();
        //TODO Handle one Assay case
        errMessage = "Assay " + assayNumber + " has raw files missing. Column(s):";
        for (String column : fileColumnsThatAreEmpty) {
            errMessage += " " + Utilities.getfieldName(column) + ",";
        }
        errMessage += " has no files referenced";
        return errMessage;
    }


    public static Validation referencedFilesArePresentInFileSystem(Study study, boolean assayRawFileValidationHasPassed) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_FILES_IN_FILESYSTEM, Requirement.MANDATORY, Group.FILES);
        if (!assayRawFileValidationHasPassed) {
            validation.setPassedRequirement(false);
            validation.setMessage("No assay raw files are referenced");
        } else {
            List<String> rawFilesListFromFilesystem = Utilities.getFileNamesInDirectory(study.getStudyLocation());
            Map<Boolean, List<String>> booleanListMap = allAssayColumnsHasFilesMatchedInFileSystem(rawFilesListFromFilesystem, study.getAssays());
            for (Map.Entry<Boolean, List<String>> result : booleanListMap.entrySet()) {
                if (!result.getKey()) {
                    validation.setPassedRequirement(result.getKey());
                    validation.setMessage(getNonMatchedFileMessage(result.getValue()));
                }
            }
        }
        validation.setStatus();
        return validation;
    }



    private static Map<Boolean, List<String>> allAssayColumnsHasFilesMatchedInFileSystem(List<String> rawFilesList, List<Assay> assays) {
        Map<Boolean, List<String>> hasPassed_FailedFileNamesMap = new LinkedHashMap<>();
        List<String> noPhysicalReference = new ArrayList<>();

        for (Assay assay : assays) {
            List<String> fileFields = getFileFieldsFrom(assay.getAssayTable().getFields());
            //int[] indexesToBeLookedUpon = getAllIndexes(fileFields);
            for (int i = 0; i < fileFields.size(); i++) {
                if (thisFileColumnHasFilesReferenced(fileFields.get(i), assay.getAssayTable().getData())) {
                    HashSet<String> uniqueFileNames = new HashSet();

                    for (List<String> data : assay.getAssayTable().getData()) {
                        String rawFileName = data.get(Utilities.indexToCheck(fileFields.get(i)));
                        if (!rawFileName.isEmpty()) {
                            uniqueFileNames.add(rawFileName);
                        }
                    }

                    for (String uniqFileName : uniqueFileNames) {
                        if (!Utilities.match(uniqFileName, rawFilesList)) {
                            noPhysicalReference.add(uniqFileName);
                        }
                    }
                }
            }
        }
        if (noPhysicalReference.size() > 0) {
            hasPassed_FailedFileNamesMap.put(false, noPhysicalReference);
            return hasPassed_FailedFileNamesMap;
        }
        hasPassed_FailedFileNamesMap.put(true, new ArrayList<String>());
        return hasPassed_FailedFileNamesMap;
    }

    private static HashSet<String> getUniqueFileNames(List<String> fileDataColumn) {
        HashSet<String> uniqNames = new HashSet();
        for (String fileName : fileDataColumn) {
            uniqNames.add(fileName);
        }
        return uniqNames;
    }

    private static int[] getAllIndexes(List<String> fileFields) {
        int[] indexes = new int[fileFields.size()];
        for (int i = 0; i < fileFields.size(); i++) {
            indexes[i] = Utilities.indexToCheck(fileFields.get(i));
        }
        return indexes;
    }


    private static String getNonMatchedFileMessage(List<String> rawFilesNoMatchList) {
        String errMessage = "Files reported in assay columns, are not present in study folder. Missing:";
        for (int i = 0; i < rawFilesNoMatchList.size(); i++) {
            errMessage += " " + rawFilesNoMatchList.get(i);
            if (i < rawFilesNoMatchList.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }
}
