/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Email address (corresponding to a web form).<br>
 * Note that validation messages in messagesXXX.properties are depending on the
 * attribute names here, so keep that in mind if you are to change a attribute name.<br>
 * Example of such a property:<br>
 * NotEmpty.emailReminder.emailAddress=Email address must not be empty.
 * 
 * @author markr
 * 
 */
public class EmailAddress {

	public EmailAddress(){	}

	@NotNull
	@Email
    @NotEmpty
  //@Size(min=5,max =255)  
	private String emailAddress;

	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

}