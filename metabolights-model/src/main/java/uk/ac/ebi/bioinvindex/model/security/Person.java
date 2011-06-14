package uk.ac.ebi.bioinvindex.model.security;

/*
 * __________
 * CREDITS
 * __________
 *
 * Team page: http://isatab.sf.net/
 * - Marco Brandizi (software engineer: ISAvalidator, ISAconverter, BII data management utility, BII model)
 * - Eamonn Maguire (software engineer: ISAcreator, ISAcreator configurator, ISAvalidator, ISAconverter,  BII data management utility, BII web)
 * - Nataliya Sklyar (software engineer: BII web application, BII model,  BII data management utility)
 * - Philippe Rocca-Serra (technical coordinator: user requirements and standards compliance for ISA software, ISA-tab format specification, BII model, ISAcreator wizard, ontology)
 * - Susanna-Assunta Sansone (coordinator: ISA infrastructure design, standards compliance, ISA-tab format specification, BII model, funds raising)
 *
 * Contributors:
 * - Manon Delahaye (ISA team trainee:  BII web services)
 * - Richard Evans (ISA team trainee: rISAtab)
 *
 *
 * ______________________
 * Contacts and Feedback:
 * ______________________
 *
 * Project overview: http://isatab.sourceforge.net/
 *
 * To follow general discussion: isatab-devel@list.sourceforge.net
 * To contact the developers: isatools@googlegroups.com
 *
 * To report bugs: http://sourceforge.net/tracker/?group_id=215183&atid=1032649
 * To request enhancements:  http://sourceforge.net/tracker/?group_id=215183&atid=1032652
 *
 *
 * __________
 * License:
 * __________
 *
 * This work is licenced under the Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License. To view a copy of this licence, visit http://creativecommons.org/licenses/by-sa/2.0/uk/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California 94105, USA.
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in part by the
 * EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * @author nataliyasklyar
 * Date: Nov 27, 2008
 * Time: 3:50:50 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@DiscriminatorValue( "Person" ) 
public class Person extends User
{
	private String firstName;
	private String lastName;
	private String affiliation;

	public Person () {
		super ();
	}

	public Person ( 
		String userName, String password, String firstName, String lastName, String email, String affiliation, 
		String address, Date joinDate, UserRole role ) 
	{
		super ( userName, password, email, address, joinDate, role );
		this.firstName = firstName;
		this.lastName = lastName;
		this.affiliation = affiliation;
	}
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	@Override
	public String toString () {
		return "Person { affiliation: \'" + affiliation + "\', firstName: \'" + firstName + "\', lastName: \'" + lastName
				+ "\', getAffiliation(): \'" + getAffiliation () + "\', getFirstName(): \'" + getFirstName () + "\', getLastName(): \'"
				+ getLastName () + "\', getAddress(): \'" + getAddress () + "\', getEmail(): \'" + getEmail () + "\', getJoinDate(): \'"
				+ getJoinDate () + "\', getPassword(): \'" + getPassword () + "\', getRole(): \'" + getRole () + "\', getUserName(): \'"
				+ getUserName () + "\', getId(): \'" + getId () + "\', getSubmissionTs(): \'" + getSubmissionTs () + " }";
	}

	
	
}
