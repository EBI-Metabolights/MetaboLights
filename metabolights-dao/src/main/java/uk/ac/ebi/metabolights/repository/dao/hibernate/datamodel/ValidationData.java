package uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel;

import uk.ac.ebi.metabolights.repository.dao.hibernate.Constants;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validations;

import javax.persistence.*;
import java.util.*;

/**
 * Created by kalai on 21/10/15.
 */
@Entity
@Table(name = Constants.VALIDATIONS_TABLE)
public class ValidationData extends DataModel<Validation> {

    private String message;

    private boolean passed;

    private Requirement requirement;

    private Integer validationid;

   // private Integer studyid;


    private StudyData studyData;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn (name = "studyid", nullable = false)
    public StudyData getStudyData() {
        return studyData;
    }

    public void setStudyData(StudyData studyData) {
        this.studyData = studyData;
    }

//    private Set<StudyData> studies = new HashSet<>();


    @Override
    protected void setBusinessModelId(Long id) {
        businessModelEntity.setId(id);
    }

    @Override
    protected void businessModelToDataModel() {
        this.passed = businessModelEntity.getPassedRequirement();
        this.message = businessModelEntity.getMessage();
        this.requirement = businessModelEntity.getType();
        this.validationid = businessModelEntity.getValidationid();
        //this.studyid = businessModelEntity.getStudyid();
    }

    @Override
    public Validation dataModelToBusinessModel() {
        businessModelEntity = new Validation();
        businessModelEntity.setPassedRequirement(this.isPassed());
        businessModelEntity.setMessage(this.getMessage());
        businessModelEntity.setType(this.requirement);
        businessModelEntity.setValidationid(this.validationid);
       // businessModelEntity.setStudyid(this.studyid);
        //businessModelEntity.setStudies(StudyData.studyDataToLiteStudy(studies));
        return businessModelEntity;
    }

    protected static Set<ValidationData> businessModelToDataModel(Validations businessItems) {

        Set<ValidationData> validationsData = new HashSet<ValidationData>();

        for (Validation businessItem : businessItems.getEntries()) {

            ValidationData validationData = new ValidationData();
            validationData.setBussinesModelEntity(businessItem);

            // Add it to the collection
            validationsData.add(validationData);
        }

        return validationsData;
    }

    protected  Set<ValidationData> businessModelToDataModel(StudyData studyData, Validations businessItems) {

        Set<ValidationData> validationsData = new HashSet<ValidationData>();
        this.studyData = studyData;


        for (Validation businessItem : businessItems.getEntries()) {

            ValidationData validationData = new ValidationData();
            validationData.setBussinesModelEntity(businessItem);

            // Add it to the collection
            validationsData.add(validationData);
        }

        return validationsData;
    }

    public static Validations dataModelToBusinessModel(Set<ValidationData> validationsData) {

        Validations validations = new Validations();

        for (ValidationData validationData : validationsData) {

            Validation validation = validationData.dataModelToBusinessModel();

            // Add it to the collection
            validations.getEntries().add(validation);
        }

        return validations;


    }

    public  Validations dataModelToBusinessModel(StudyData studyData, Set<ValidationData> validationsData) {

        Validations validations = new Validations();
        this.studyData = studyData;

        for (ValidationData validationData : validationsData) {

            Validation validation = validationData.dataModelToBusinessModel();

            // Add it to the collection
            validations.getEntries().add(validation);
        }

        return validations;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }


    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Integer getValidationid() {
        return validationid;
    }

    public void setValidationid(Integer validationid) {
        this.validationid = validationid;
    }

//
//    @ManyToOne(targetEntity = StudyData.class)
//    @JoinColumn (name = "studyid")
//    public Integer getStudyid() {
//        return studyid;
//    }
//
//    public void setStudyid(Integer studyid) {
//        this.studyid = studyid;
//    }


//    @ManyToOne
//    @JoinColumn(name = "studyid")
//    public Set<StudyData> getStudies() {
//        return studies;
//    }
//
//    public void setStudies(Set<StudyData> studies) {
//        this.studies = studies;
//    }



}
