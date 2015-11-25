package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.StudyFactor;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.ValidationIdentifier;
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
            Collection<Validation> factorsInSamplesAssays = getFactorsPresentInSamplesOrAssaysValidation(study);
            if(!Utilities.allPassed(factorsInSamplesAssays)){
                factorValidations.addAll(factorsInSamplesAssays);
            }
        }
        return factorValidations;
    }


    public static Validation getFactorNameValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.FACTOR_NAME, Requirement.MANDATORY, Group.FACTORS);
        validation.setId(ValidationIdentifier.FACTOR_NAME.getID());
        if (!study.getFactors().isEmpty()) {
            for (StudyFactor studyFactor : study.getFactors()) {
                if (!Utilities.minCharRequirementPassed(studyFactor.getName(), 3)) {
                    validation.setMessage("Study Factor " + studyFactor.getName() + " is not valid");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("No Study Factor information is provided");
            validation.setPassedRequirement(false);
        }
        return validation;
    }

    public static Collection<Validation> getFactorsPresentInSamplesOrAssaysValidation(Study study) {
        Collection<Validation> validations = new LinkedList<>();
        Validation validation1 = new Validation(DescriptionConstants.FACTOR_IN_SAMPLES_ASSAYS, Requirement.MANDATORY, Group.FACTORS);
        validation1.setId(ValidationIdentifier.FACTOR_IN_SAMPLES_ASSAYS.getID());
        Validation validation2 = new Validation(DescriptionConstants.FACTOR_COLUMNS_SAMPLES_ASSAYS, Requirement.MANDATORY, Group.FACTORS);
        validation2.setId(ValidationIdentifier.FACTOR_COLUMNS_SAMPLES_ASSAYS.getID());

        Map<String, Integer> factorSampleMap = getMatchingFactorIndices(study.getFactors(), study.getSampleTable().getFields());
        Map<Assay, Map<String, Integer>> factorAssayMap = assayThatHasAllfactors(study.getFactors(), study.getAssays());
        boolean missingInSamples = someFactorsAreMissingIn(factorSampleMap);
        boolean presentInAssays = isAtleastFullyPresentInOneAssay(factorAssayMap);

        List<String> emptyFactorColumnsInSamples = new ArrayList<>();
        Map<Assay, Map<String, Integer>> nonEmptyAssayFactorMap = new HashMap<>();


        if (missingInSamples && !presentInAssays) {
            validation1.setPassedRequirement(false);
            validation1.setMessage(getMissingFactorsErrMsg(factorSampleMap, "Sample or Assay"));
            validations.add(validation1);
            return validations;
        }

        nonEmptyAssayFactorMap = removeAssaysWithEmptyFactorColumns(factorAssayMap);

        if (!missingInSamples) {
            emptyFactorColumnsInSamples = getEmptyFactorColumns(factorSampleMap, study.getSampleTable().getData());
            if (emptyFactorColumnsInSamples.size() != 0) {
                if (!presentInAssays || (presentInAssays && nonEmptyAssayFactorMap.size() == 0)) {
                    validation2.setPassedRequirement(false);
                    validation2.setMessage("Empty Study Factor column(s) found in the Sample and Assay sheet(s)");
                }
            }
        } else {
            if (!presentInAssays || (presentInAssays && nonEmptyAssayFactorMap.size() == 0)) {
                validation2.setPassedRequirement(false);
                validation2.setMessage("Empty Study Factor column(s) found in the Sample and Assay sheet(s)");
            }
        }
        validations.add(validation1);
        validations.add(validation2);
        return validations;
    }

    private static boolean someFactorsAreMissingIn(Map<String, Integer> map) {
        return (getMissingFactorColumnsCount(map) > 0);
    }

    private static Map<Assay, Map<String, Integer>> assayThatHasAllfactors(Collection<StudyFactor> factors, Collection<Assay> assays) {
        Map<Assay, Map<String, Integer>> assayNumber_factorAssayMap = new HashMap<>();
        for (Assay assay : assays) {
            Map<String, Integer> factorAssayMap = getMatchingFactorIndices(factors, assay.getAssayTable().getFields());
            if (!someFactorsAreMissingIn(factorAssayMap)) {
                assayNumber_factorAssayMap.put(assay, factorAssayMap);
            }
        }
        return assayNumber_factorAssayMap;
    }

    private static boolean isAtleastFullyPresentInOneAssay(Map<Assay, Map<String, Integer>> factorAssayMap) {
        return factorAssayMap.size() > 0;
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
        String message = "The following Study Factor column(s) are not present in the " + type + " sheet:";
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

    private static Map<Assay, Map<String, Integer>> removeAssaysWithEmptyFactorColumns(Map<Assay, Map<String, Integer>> factorAssayMap) {
        Map<Assay, Map<String, Integer>> factorAssayMapWithoutEmptyColumns = new HashMap<>();
        for (Map.Entry<Assay, Map<String, Integer>> entry : factorAssayMap.entrySet()) {
            List<String> emptyColumns = getEmptyFactorColumns(entry.getValue(), entry.getKey().getAssayTable().getData());
            if (emptyColumns.size() == 0) {
                factorAssayMapWithoutEmptyColumns.put(entry.getKey(), entry.getValue());
            }
        }
        return factorAssayMapWithoutEmptyColumns;
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


}
