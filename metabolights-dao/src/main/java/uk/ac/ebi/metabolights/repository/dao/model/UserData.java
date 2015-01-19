/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-19
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: conesa
 * Date: 19/01/15
 * Time: 14:12
 */
@Entity
@Table(name = "users")
public class UserData {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	protected String address;
	protected String email;
	protected Date joinDate;
	protected String password;
	protected int role;
	protected String userName;
	protected String affiliation;
	protected String firstName;
	protected String lastName;
	protected int status;
	protected String affiliationUrl;
	protected String apiToken;
	//private Set<StudyLite> studies = new HashSet<StudyLite>();

}
