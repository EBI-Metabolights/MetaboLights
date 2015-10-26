package uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel;

import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * Created by kalai on 21/10/15.
 */
@Entity
@Table(name = "validation")
public class ValidationData extends DataModel<Validation> {

    private String message;

    private Status status = Status.GREEN;

    @Override
    protected void setBusinessModelId(Long id) {
        businessModelEntity.setId(id);
    }

    @Override
    protected void businessModelToDataModel() {
        this.status = businessModelEntity.getStatus();
        this.message = businessModelEntity.getMessage();
    }

    @Override
    public Validation dataModelToBusinessModel() {
        businessModelEntity = new Validation();
        businessModelEntity.setStatus(this.getStatus());
        businessModelEntity.setMessage(this.getMessage());
        return businessModelEntity;
    }

    protected static Set<ValidationData> businessModelToDataModel(Collection<Validation> businessUsers){

        Set<ValidationData> validationsData = new HashSet<ValidationData>();

        for (Validation businessItem : businessUsers){

            ValidationData validationData = new ValidationData();
            validationData.setBussinesModelEntity(businessItem);

            // Add it to the collection
            validationsData.add(validationData);
        }

        return validationsData;
    }

    public static List<Validation> dataModelToBusinessModel(Set<ValidationData> validationsData) {

        List<Validation> validations = new ArrayList<>();

        for (ValidationData validationData :validationsData){

            Validation validation = validationData.dataModelToBusinessModel();

            // Add it to the collection
            validations.add(validation);
        }

        return validations;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
