package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.io.File;
import java.util.*;

/**
 * Created by kalai on 01/10/15.
 */

public class AssayValidations {

    public static Collection<Validation> getValidations(Study study) {
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
            Validation assayRawFileValidation = getAssayHasFilesValidation(study);
            assayValidations.add(assayRawFileValidation);
            assayValidations.add(referencedFilesArePresentInFileSystem(study, assayRawFileValidation.getPassedRequirement()));
        }
        assayValidations.add(validation);
        validation.setStatus();
        return assayValidations;
    }

    public static Validation getAssayHasFilesValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_FILES, Requirement.MANDATORY, Group.ASSAYS);
        for (Assay assay : study.getAssays()) {
            List<String> fileFields = getFileFieldsFrom(assay.getAssayTable().getFields());
            List<String> fileColumnsThatAreEmpty = new ArrayList<>();
            for (String fileField : fileFields) {
                if (!thisFileColumnHasFilesReferenced(fileField, assay.getAssayTable().getData())) {
                    fileColumnsThatAreEmpty.add(fileField);
                }
            }
            if (fileColumnsThatAreEmpty.size() >0 ) {
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
            if (containsFileKeyword(entry.getKey())) {
                fileFields.add(entry.getKey());
            }
        }
        return fileFields;
    }

    private static boolean containsFileKeyword(String field) {
        return field.contains(" file");
    }

    private static boolean thisFileColumnHasFilesReferenced(String fileColumn, List<List<String>> tableData) {
        int index = indexToCheck(fileColumn);
        for (List<String> data : tableData) {
            if (data.get(index).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static int indexToCheck(String fileColumn) {
        String index = fileColumn.split("~")[0];
        return Integer.parseInt(index);
    }

    private static String getErrMessage(Assay assay, List<String> fileColumnsThatAreEmpty) {
        String errMessage = "";
        int assayNumber = assay.getAssayNumber();
        errMessage = "Assay-" + assayNumber + " has raw files missing. Column(s):";
        for (String column : fileColumnsThatAreEmpty) {
            errMessage += " " + getfieldName(column) + ",";
        }
        errMessage += " has no files referenced";
        return errMessage;
    }

    private static String getfieldName(String fileColumn) {
        String fieldname = fileColumn.split("~")[1];
        return fieldname;
    }

    public static Validation referencedFilesArePresentInFileSystem(Study study, boolean assayRawFileValidationHasPassed) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_FILES_IN_FILESYSTEM, Requirement.MANDATORY, Group.ASSAYS);
        if (!assayRawFileValidationHasPassed) {
            validation.setPassedRequirement(false);
            validation.setMessage("Not all assay file columns are filled with reference files");
        } else {
            List<String> rawFilesListFromFilesystem = getFileNamesInDirectory(study.getStudyLocation());
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

    private static List<String> getFileNamesInDirectory(String studyDirectory) {
        File folder = new File(studyDirectory);
        File[] listOfFiles = folder.listFiles();
        List<String> possibleRawFiles = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                possibleRawFiles.add(listOfFiles[i].getName());
            }
        }
        return possibleRawFiles;
    }

    private static Map<Boolean, List<String>> allAssayColumnsHasFilesMatchedInFileSystem(List<String> rawFilesList, List<Assay> assays) {
        Map<Boolean, List<String>> hasPassed_FailedFilNamesMap = new LinkedHashMap<>();
        for (Assay assay : assays) {
            List<String> fileFields = getFileFieldsFrom(assay.getAssayTable().getFields());
            int[] indexesToBeLookedUpon = getAllIndexes(fileFields);
            List<String> noPhysicalReference = new ArrayList<>();
            for (int i = 0; i < indexesToBeLookedUpon.length; i++) {
                for (List<String> data : assay.getAssayTable().getData()) {
                    String tableRowEntry = data.get(indexesToBeLookedUpon[i]);
                    if (!match(tableRowEntry, rawFilesList)) {
                        noPhysicalReference.add(tableRowEntry);
                    }
                }
            }
            if (noPhysicalReference.size() > 0) {
                hasPassed_FailedFilNamesMap.put(false, noPhysicalReference);
                return hasPassed_FailedFilNamesMap;
            }
        }
        hasPassed_FailedFilNamesMap.put(true, new ArrayList<String>());
        return hasPassed_FailedFilNamesMap;
    }

    private static int[] getAllIndexes(List<String> fileFields) {
        int[] indexes = new int[fileFields.size()];
        for (int i = 0; i < fileFields.size(); i++) {
            indexes[i] = indexToCheck(fileFields.get(i));
        }
        return indexes;
    }

    private static boolean match(String tableRowEntry, List<String> rawFilesList) {
        return rawFilesList.contains(tableRowEntry);
    }

    private static String getNonMatchedFileMessage(List<String> rawFilesNoMatchList) {
        String errMessage = "Files reported in assay columns, are not present in file system. Example: ";
        for (String file : rawFilesNoMatchList) {
            errMessage += file + ";";
        }
        return errMessage;
    }
}
