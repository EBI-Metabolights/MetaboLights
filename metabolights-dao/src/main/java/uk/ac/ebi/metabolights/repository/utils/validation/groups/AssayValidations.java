package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;

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
        if (ProtocolValidations.metaboliteIdentificationProtocolIsPresent(study)) {
//            Validation mafValidation = mafFileReferencedValidation(study);
//            assayValidations.add(mafValidation);
//            if (mafValidation.getPassedRequirement()) {
//
//            }
            assayValidations.addAll(mafFileReferencedValidations(study));
        }
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
            List<String> fileFields = getFileFieldsFrom(assay.getAssayTable().getFields());
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
            if (containsFileKeyword(entry.getKey())) {
                fileFields.add(entry.getKey());
            }
        }
        return fileFields;
    }

    private static boolean containsFileKeyword(String field) {
        return field.contains(" file");
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
        int index = indexToCheck(fileColumn);
        for (List<String> data : tableData) {
            if (!data.get(index).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static int indexToCheck(String fileColumn) {
        String index = fileColumn.split("~")[0];
        return Integer.parseInt(index);
    }

    private static String getErrMessage(Assay assay, List<String> fileColumnsThatAreEmpty) {
        String errMessage = "";
        int assayNumber = assay.getAssayNumber();
        //TODO Handle one Assay case
        errMessage = "Assay " + assayNumber + " has raw files missing. Column(s):";
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
        Validation validation = new Validation(DescriptionConstants.ASSAY_FILES_IN_FILESYSTEM, Requirement.MANDATORY, Group.FILES);
        if (!assayRawFileValidationHasPassed) {
            validation.setPassedRequirement(false);
            validation.setMessage("No assay raw files are referenced");
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
        List<String> noPhysicalReference = new ArrayList<>();

        for (Assay assay : assays) {
            List<String> fileFields = getFileFieldsFrom(assay.getAssayTable().getFields());
            //int[] indexesToBeLookedUpon = getAllIndexes(fileFields);
            for (int i = 0; i < fileFields.size(); i++) {
                if (thisFileColumnHasFilesReferenced(fileFields.get(i), assay.getAssayTable().getData())) {
                    HashSet<String> uniqueNames = new HashSet();

                    for (List<String> data : assay.getAssayTable().getData()) {
                        String tableRowEntry = data.get(indexToCheck(fileFields.get(i)));
                        if (!tableRowEntry.isEmpty()) {
                            uniqueNames.add(tableRowEntry);
                        }
                    }

                    for (String uniqName : uniqueNames) {
                        if (!match(uniqName, rawFilesList)) {
                            noPhysicalReference.add(uniqName);
                        }
                    }
                }
            }
        }
        if (noPhysicalReference.size() > 0) {
            hasPassed_FailedFilNamesMap.put(false, noPhysicalReference);
            return hasPassed_FailedFilNamesMap;
        }
        hasPassed_FailedFilNamesMap.put(true, new ArrayList<String>());
        return hasPassed_FailedFilNamesMap;
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
            indexes[i] = indexToCheck(fileFields.get(i));
        }
        return indexes;
    }

    private static boolean match(String tableRowEntry, List<String> rawFilesList) {
        return rawFilesList.contains(tableRowEntry);
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

    private static Validation mafFileReferencedValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_MAF_REFERENCE, Requirement.MANDATORY, Group.FILES);
        List<Assay> assaysWithNoMaf = new ArrayList<>();
        //TODO
        for (Assay assay : study.getAssays()) {
            int mafIndex = getMAFIndex(assay.getAssayTable().getFields());
            if (mafIndex != -1) {
                if (!isMafReferenced(mafIndex, assay.getAssayTable().getData())) {
                    assaysWithNoMaf.add(assay);
                }
            }
        }
        if (assaysWithNoMaf.size() > 0) {
            validation.setPassedRequirement(false);
            validation.setMessage(getMafErrMessage(assaysWithNoMaf, study.getAssays().size()));
        }
        validation.setStatus();
        return validation;

    }

    private static Collection<Validation> mafFileReferencedValidations(Study study) {
        Collection<Validation> mafValidations = new ArrayList<>();
        Validation maf_reference_validation = new Validation(DescriptionConstants.ASSAY_MAF_REFERENCE, Requirement.MANDATORY, Group.FILES);
        Validation maf_file_validation = new Validation(DescriptionConstants.ASSAY_MAF_FILE, Requirement.MANDATORY, Group.FILES);
        List<Assay> assaysWithNoMaf = new ArrayList<>();
        Map<Integer, Assay> mafIndex_assaysWithMaf_map = new LinkedHashMap<>();
        //TODO
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
        if (assaysWithNoMaf.size() > 0) {
            maf_reference_validation.setPassedRequirement(false);
            maf_reference_validation.setMessage(getMafErrMessage(assaysWithNoMaf, study.getAssays().size()));
        } else {
            List<String> notPresentInStudyFolder = referencedMafFilesNotPresentInFileSystem(mafIndex_assaysWithMaf_map, study);
            if (notPresentInStudyFolder.size() > 0) {
                maf_file_validation.setPassedRequirement(false);
                maf_file_validation.setMessage(getMafFileErrMessage(notPresentInStudyFolder));
            }
            maf_file_validation.setStatus();
            mafValidations.add(maf_file_validation);
        }
        maf_reference_validation.setStatus();
        mafValidations.add(maf_reference_validation);
        return mafValidations;

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
        List<String> fileNames = getFileNamesInDirectory(study.getStudyLocation());
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
}
