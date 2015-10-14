package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * User: conesa
 * Date: 08/10/15
 * Time: 13:19
 */
public interface IValidationProcess {

	public String getAbout();
	public Collection<Validation> getValidations(Study studyToValidate);
}
