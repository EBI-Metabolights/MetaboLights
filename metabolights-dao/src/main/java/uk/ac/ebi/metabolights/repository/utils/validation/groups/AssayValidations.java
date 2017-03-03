package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import org.apache.commons.io.FilenameUtils;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.ValidationIdentifier;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;
import uk.ac.ebi.metabolights.utils.json.FileUtils;

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
        assayValidations.addAll(getAssayValidations(study));
        return assayValidations;
    }

    public static Validation getAssayValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAYS, Requirement.MANDATORY, Group.ASSAYS);
        validation.setId(ValidationIdentifier.ASSAYS.getID());
        if (study.getAssays().isEmpty()) {
            validation.setMessage("No Assay data is provided");
            validation.setPassedRequirement(false);
        }
        return validation;
    }

    public static Collection<Validation> getAssayValidations(Study study) {
        Collection<Validation> assayValidations = new LinkedList<>();
        Validation validation = new Validation(DescriptionConstants.ASSAYS, Requirement.MANDATORY, Group.ASSAYS);
        validation.setId(ValidationIdentifier.ASSAYS.getID());
        if (study.getAssays().isEmpty()) {
            validation.setMessage("No Assay data is provided");
            validation.setPassedRequirement(false);
        } else {
            assayValidations.add(getAssayHasPlatformInfoValidation(study));
            Validation assayRawFileValidation = getAssayHasFilesValidation(study);
            assayValidations.add(assayRawFileValidation);
            assayValidations.add(referencedFilesArePresentInFileSystem(study, assayRawFileValidation.getPassedRequirement()));
            if (assayRawFileValidation.getPassedRequirement()) {
                assayValidations.add(referencedRawFilesAreOfCorrectFormat(study));
            }
//            Validation areColumnsEmptyValidation = getEmptyColumnsValidation(study);
//            if (!areColumnsEmptyValidation.getPassedRequirement()) {
//                assayValidations.add(areColumnsEmptyValidation);
//            }
        }
        assayValidations.add(validation);
        return assayValidations;
    }

    public static Validation getAssayHasPlatformInfoValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_PLATFORM, Requirement.OPTIONAL, Group.ASSAYS);
        validation.setId(ValidationIdentifier.ASSAY_PLATFORM.getID());
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
        validation.setId(ValidationIdentifier.ASSAY_FILES.getID());
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
                return validation;
            }
        }
        return validation;
    }

    private static List<String> getFileFieldsFrom(LinkedHashMap<String, Field> tableFields) {
        List<String> fileFields = new ArrayList<>();
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            if (Utilities.containsKeyword(entry.getKey(), " file")) {
                String localField = Utilities.getfieldName(entry.getKey()).toLowerCase();
                if (!localField.contains("parameter value"))  // Ignore Parameter values describing files (mzML2ISA generated)
                    fileFields.add(entry.getKey());
            }
        }
        return fileFields;
    }


    private static List<String> getFileFieldsExceptMAFFrom(LinkedHashMap<String, Field> tableFields) {
        List<String> fileFields = new ArrayList<>();
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            if (Utilities.containsKeyword(entry.getKey(), " file")) {
                String localField = Utilities.getfieldName(entry.getKey()).toLowerCase();
                if (!localField.equalsIgnoreCase("metabolite assignment file") && !localField.contains("parameter value")) {   // Ignore Parameter values describing files (mzML2ISA generated)
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
        validation.setId(ValidationIdentifier.ASSAY_FILES_IN_FILESYSTEM.getID());
        if (!assayRawFileValidationHasPassed) {
            validation.setPassedRequirement(false);
            validation.setMessage("No Assay raw files are referenced");
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
        return validation;
    }


    private static Map<Boolean, List<String>> allAssayColumnsHasFilesMatchedInFileSystem(List<String> allFilesList, List<Assay> assays) {
        Map<Boolean, List<String>> hasPassed_FailedFileNamesMap = new LinkedHashMap<>();
        List<String> noPhysicalReference = filesThatHasnoPhysicalReference(allFilesList, assays);
        if (noPhysicalReference.size() > 0) {
            hasPassed_FailedFileNamesMap.put(false, noPhysicalReference);
            return hasPassed_FailedFileNamesMap;
        }
        hasPassed_FailedFileNamesMap.put(true, new ArrayList<String>());
        return hasPassed_FailedFileNamesMap;
    }

    private static List<String> filesThatHasnoPhysicalReference(List<String> allFilesList, List<Assay> assays) {
        List<String> noPhysicalReference = new ArrayList<>();
        HashSet<String> filesThatAreReferencedInAssays = getFilesThatAreReferencedInAssays(assays);
        for (String uniqueFileName : filesThatAreReferencedInAssays) {
            if (!Utilities.match(uniqueFileName, allFilesList)) {
                noPhysicalReference.add(uniqueFileName);
            }
        }
        return noPhysicalReference;
    }

    private static HashSet<String> getFilesThatAreReferencedInAssays(List<Assay> assays) {
        HashSet<String> uniqueFileNamesReferencedInAssays = new HashSet();
        for (Assay assay : assays) {
            List<String> fileFields = getFileFieldsFrom(assay.getAssayTable().getFields());
            //int[] indexesToBeLookedUpon = getAllIndexes(fileFields);
            for (int i = 0; i < fileFields.size(); i++) {
                if (thisFileColumnHasFilesReferenced(fileFields.get(i), assay.getAssayTable().getData())) {
                    for (List<String> data : assay.getAssayTable().getData()) {
                        String rawFileName = data.get(Utilities.indexToCheck(fileFields.get(i)));
                        if (!rawFileName.isEmpty()) {
                            uniqueFileNamesReferencedInAssays.add(rawFileName);
                        }
                    }
                }
            }
        }
        return uniqueFileNamesReferencedInAssays;
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
        String errMessage = "Files reported in Assay columns, are not present in Study folder. Missing:";
        for (int i = 0; i < rawFilesNoMatchList.size(); i++) {
            errMessage += " " + rawFilesNoMatchList.get(i);
            if (i < rawFilesNoMatchList.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }

    public static Validation referencedRawFilesAreOfCorrectFormat(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_CORRECT_RAW_FILE, Requirement.MANDATORY, Group.FILES);
        validation.setId(ValidationIdentifier.ASSAY_CORRECT_RAW_FILE.getID());
        HashSet<String> incorrectFormat = new HashSet<>();

        HashSet<String> filesThatAreReferencedInAssays = getFilesThatAreReferencedInAssays(study.getAssays());
        for (String file : filesThatAreReferencedInAssays) {
            try {
                if(!file.startsWith("m_")){
                    File newFile = new File(study.getStudyLocation() + File.separator + file);
                    if (newFile.isDirectory()) {
                        incorrectFormat.add(file);
                    } else {
                        String extension = FilenameUtils.getExtension(file);
                        if (extension.isEmpty()) {
                            incorrectFormat.add(file);
                        } else {
                            if (Utilities.incorrectFormat(extension)) {
                                incorrectFormat.add(file);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (incorrectFormat.size() > 0) {
            validation.setPassedRequirement(false);
            validation.setMessage(getIncorrectAssayFileFormatMessage(incorrectFormat));
        }
        return validation;
    }

    private static String getIncorrectAssayFileFormatMessage(HashSet<String> incorrectRawFiles) {
        List<String> list = new ArrayList<String>(incorrectRawFiles);
        String errMessage = "Raw files reported in the Assay(s), are not of valid format:";
        for (int i = 0; i < list.size(); i++) {
            errMessage += " " + list.get(i);
            if (i < list.size() - 1) {
                errMessage += ",";
            }
        }
        return errMessage;
    }


    private static Validation getEmptyColumnsValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAYS_EMPTY_COLUMNS, Requirement.OPTIONAL, Group.ASSAYS);
        validation.setId(ValidationIdentifier.ASSAYS_EMPTY_COLUMNS.getID());

        if (study.getAssays().size() == 1) {
            handleSingleAssayCase(validation, study);
        } else {
            handleMultipleAssayCase(validation, study);
        }

        return validation;
    }

    private static void handleSingleAssayCase(Validation validation, Study study) {
        HashSet emptyIndices = (HashSet) Utilities.getEmptyDataColumns(study.getAssays().get(0).getAssayTable().getData());
        if (!emptyIndices.isEmpty()) {
            List<String> emptyFieldNames = Utilities.getEmptyFieldNames(study.getAssays().get(0).getAssayTable().getFields(), emptyIndices);
            validation.setPassedRequirement(false);
            validation.setMessage(Utilities.getSampleColumnEmptyErrMsg(emptyFieldNames, "Assay"));
        }
    }

    private static void handleMultipleAssayCase(Validation validation, Study study) {
        Map<String, HashSet<Integer>> columnName_assay_map = new HashMap<>();
        for (int i = 0; i < study.getAssays().size(); i++) {
            HashSet emptyIndices = (HashSet) Utilities.getEmptyDataColumns(study.getAssays().get(i).getAssayTable().getData());
            if (!emptyIndices.isEmpty()) {
                List<String> emptyFieldNames = Utilities.getEmptyFieldNames(study.getAssays().get(i).getAssayTable().getFields(), emptyIndices);
                fill(columnName_assay_map, emptyFieldNames, i);
                validation.setPassedRequirement(false);
                validation.setMessage(getSampleColumnEmptyErrMsg(columnName_assay_map));
            }
        }
    }

    private static void fill(Map<String, HashSet<Integer>> columnName_assay_map, List<String> emptyFieldNames, int assayNumber) {
        for (String name : emptyFieldNames) {
            if (!columnName_assay_map.containsKey(name)) {
                HashSet<Integer> assayIndices = new HashSet<>();
                assayIndices.add(assayNumber);
                columnName_assay_map.put(name, assayIndices);
            } else {
                columnName_assay_map.get(name).add(assayNumber);
            }
        }
    }

    private static String getSampleColumnEmptyErrMsg(Map<String, HashSet<Integer>> columnName_assay_map) {
        String message = "The following columns are missing in the Assay Definition Sheet(s): ";

        for (Map.Entry<String, HashSet<Integer>> entry : columnName_assay_map.entrySet()) {
            String missingColumn = entry.getKey();
            message += missingColumn + " (";
            for (Integer assayNumber : entry.getValue()) {
                message += "Assay " + (assayNumber + 1) + ";";
            }
            message += ")";

        }


        return message;
    }


}
