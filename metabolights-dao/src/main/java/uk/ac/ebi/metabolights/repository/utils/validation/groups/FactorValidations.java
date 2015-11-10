package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.StudyFactor;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.*;

/**
 * Created by kalai on 18/09/15.
 */
public class FactorValidations implements IValidationProcess {

    @Override
    public String getAbout() {
        return Group.FACTORS.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> factorValidations = new LinkedList<>();
        Validation basicValidation = getFactorNameValidation(study);
        factorValidations.add(basicValidation);
        if (basicValidation.getPassedRequirement()) {
            Validation factorsInSamples = getFactorInSamplesValidation(study);
            if (!factorsInSamples.getPassedRequirement()) {
                factorValidations.add(factorsInSamples);
            }
            Validation factorsInAssays = getFactorInAssaysValidation(study);
            if (!factorsInSamples.getPassedRequirement()) {
                factorValidations.add(factorsInAssays);
            }
        }
        return factorValidations;
    }


    public static Validation getFactorNameValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.FACTOR_NAME, Requirement.MANDATORY, Group.FACTORS);
        if (!study.getFactors().isEmpty()) {
            for (StudyFactor studyFactor : study.getFactors()) {
                if (!Utilities.minCharRequirementPassed(studyFactor.getName(), 3)) {
                    validation.setMessage("Study Factor " + studyFactor.getName() + "is not valid");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("No Study Factor information is provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    public static Validation getFactorInSamplesValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.FACTOR_IN_SAMPLES, Requirement.MANDATORY, Group.FACTORS);
        if (!study.getFactors().isEmpty()) {
            Map<String, Integer> map = getMatchingFactorIndices(study.getFactors(), study.getSampleTable().getFields());
            if (getMissingFactorColumnsCount(map) > 0) {
                validation.setPassedRequirement(false);
                validation.setMessage(getMissingFactorsErrMsg(map, "Sample"));
            } else {
                List<String> emptyFactorColumns = getEmptyFactorColumns(map, study.getSampleTable().getData());
                if (emptyFactorColumns.size() > 0) {
                    validation.setPassedRequirement(false);
                    validation.setMessage(getEmptyFactorColumnErrMsg(emptyFactorColumns, "Sample"));
                }

            }

        }
        validation.setStatus();
        return validation;
    }

    private static int getMissingFactorColumnsCount(Map<String, Integer> map) {
        int count = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().intValue() == -1) {
                count++;
            }
        }
        return count;
    }


    private static Map<String, Integer> getMatchingFactorIndices(Collection<StudyFactor> factors, LinkedHashMap<String, Field> tableFields) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (StudyFactor factor : factors) {
            int matchingIndex = getFieldIndex(tableFields, factor.getName());
            map.put(factor.getName(), new Integer(matchingIndex));
        }

        return map;
    }

    public static int getFieldIndex(LinkedHashMap<String, Field> tableFields, String fieldName) {
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            if (Utilities.getfieldName(entry.getKey()).equalsIgnoreCase("factor value[" + fieldName + "]")) {
                return Utilities.indexToCheck(entry.getKey());
            }
        }
        return -1;
    }

    private static String getMissingFactorsErrMsg(Map<String, Integer> map, String type) {
        String message = "The following Study Factor column(s) are are not present in the " + type + " sheet:";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().intValue() == -1) {
                message += " " + entry.getKey() + ";";
            }
        }
        return message;
    }

    private static List<String> getEmptyFactorColumns(Map<String, Integer> map, List<List<String>> tableData) {
        List<String> emptyColumns = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (!thisColumnHasSomething(entry.getValue().intValue(), tableData)) {
                emptyColumns.add(entry.getKey());
            }
        }
        return emptyColumns;
    }

    private static boolean thisColumnHasSomething(int columnIndex, List<List<String>> tableData) {
        for (List<String> data : tableData) {
            if (!data.get(columnIndex).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static String getEmptyFactorColumnErrMsg(List<String> emptyFactors, String type) {
        String message = "The following Study Factor column(s) are missing in the " + type + " sheet:";
        for (String s : emptyFactors) {
            message += " " + s + ";";
        }
        return message;
    }

    public static Validation getFactorInAssaysValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.FACTOR_IN_ASSAYS, Requirement.MANDATORY, Group.FACTORS);
        if (!study.getFactors().isEmpty()) {
            Map<String, Integer> map = getMatchingFactorIndices(study.getFactors(), study.getAssays().get(0).getAssayTable().getFields());
            if (getMissingFactorColumnsCount(map) > 0) {
                validation.setPassedRequirement(false);
                validation.setMessage(getMissingFactorsErrMsg(map, "Assay"));
            } else {
                List<String> emptyFactorColumns = getEmptyFactorColumns(map, study.getAssays().get(0).getAssayTable().getData());
                if (emptyFactorColumns.size() > 0) {
                    validation.setPassedRequirement(false);
                    validation.setMessage(getEmptyFactorColumnErrMsg(emptyFactorColumns, "Assay"));
                }

            }

        }
        validation.setStatus();
        return validation;
    }


}
