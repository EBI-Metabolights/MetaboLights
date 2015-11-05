package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;

import java.util.*;

/**
 * Created by kalai on 01/10/15.
 */

public class SampleValidations implements IValidationProcess {

    @Override
    public String getAbout() {
        return Group.SAMPLES.toString();

    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> sampleValidations = new LinkedList<>();
        sampleValidations.add(getSampleValidation(study));
        sampleValidations.add(getSampleNameConsistencyValidation(study));
        return sampleValidations;

    }

    public static Validation getSampleValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.SAMPLES, Requirement.MANDATORY, Group.SAMPLES);
        if (study.getSampleTable().getData().isEmpty()) {
            validation.setMessage("No Sample Names are provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }


    public static Validation getSampleNameConsistencyValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAY_SAMPLE_NAMEMATCH, Requirement.MANDATORY, Group.ASSAYS);
        if (study.getSampleTable().getData().isEmpty()) {
            validation.setMessage("No Sample Names are provided");
            validation.setPassedRequirement(false);
        } else {
            int sampleNameIndexInSamples = getSampleNameIndexFrom(study.getSampleTable().getFields());
            List<String> sampleNamesFromSampleTable = getSampleNames(sampleNameIndexInSamples, study.getSampleTable().getData());
            Map<Integer, List<String>> assay_SampleNames = new HashMap<>();
            for (Assay assay : study.getAssays()) {
                int sampleNameIndexInAssay = getSampleNameIndexFrom(assay.getAssayTable().getFields());
                List<String> sampleNamesFromAssayTable = getSampleNames(sampleNameIndexInAssay, assay.getAssayTable().getData());
                List<String> noMatch = match(sampleNamesFromAssayTable, sampleNamesFromSampleTable);
                if (!noMatch.isEmpty()) {
                    assay_SampleNames.put(new Integer(assay.getAssayNumber()), noMatch);
                }
            }
            if (!assay_SampleNames.isEmpty()) {
                validation.setPassedRequirement(false);
                validation.setMessage(getMisMatchErrMsg(assay_SampleNames));
            }
            // get sample names list
            // create a map of Integer,List<String> assay number, sampleNames

            /*

            for each assay
            get the sample name index, and the sample names
            List of moMatch
            Look up each name in the above sample names list, if there is mismatch add it to the noMatch list
            if noMatch is not empty, put this list and assay number into a map

             */


            // if the map is not empty, fail the validation
            // generate error message based on the map information


        }
        validation.setStatus();
        return validation;

    }

    private static int getSampleNameIndexFrom(LinkedHashMap<String, Field> tableFields) {
        int index = -1;
        for (Map.Entry<String, Field> entry : tableFields.entrySet()) {
            if (Utilities.getfieldName(entry.getKey()).equalsIgnoreCase("sample name")) {
                index = Utilities.indexToCheck(entry.getKey());
                break;
            }
        }
        return index;
    }

    private static List<String> getSampleNames(int index, List<List<String>> tableData) {
        List<String> sampleNames = new ArrayList<>();
        for (List<String> data : tableData) {
            String sampleName = data.get(index);
            if (!sampleName.isEmpty()) {
                sampleNames.add(sampleName);
            }
        }
        return sampleNames;
    }

    private static List<String> match(List<String> sampleNameInAssays, List<String> sampleNameFromSample) {
        List<String> noMatch = new ArrayList<>();
        for (String sampleName : sampleNameInAssays) {
            if (!sampleNameFromSample.contains(sampleName)) {
                noMatch.add(sampleName);
            }
        }
        return noMatch;
    }

    private static String getMisMatchErrMsg(Map<Integer, List<String>> assay_SampleNames) {
        String message = "The following Sample Names are not consistent: \n";
        for (Map.Entry<Integer, List<String>> entry : assay_SampleNames.entrySet()) {
            int index = entry.getKey().intValue();
            message += " Assay " + index + ":";
            for (String name : entry.getValue()) {
                message += name.trim() + "; ";
            }
            message += "\n";
        }
        return message;
    }
}
