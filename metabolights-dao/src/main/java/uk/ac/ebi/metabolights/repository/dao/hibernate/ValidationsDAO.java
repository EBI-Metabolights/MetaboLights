package uk.ac.ebi.metabolights.repository.dao.hibernate;

import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.ValidationData;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

/**
 * Created by kalai on 29/10/15.
 */
public class ValidationsDAO extends DAO<Validation, ValidationData> {
    @Override
    protected void init() {

    }

    @Override
    protected Class<ValidationData> getDataModelClass() {
        return ValidationData.class;
    }

    @Override
    protected void preSave(ValidationData datamodel) throws DAOException {

    }
}
