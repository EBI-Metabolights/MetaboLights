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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import uk.ac.ebi.bioinvindex.model.Identifiable;

/**
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Nov 27, 2008
 */

@Entity
@Table(name = "user_detail")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "obj_type", discriminatorType = DiscriminatorType.STRING, length = 255)
public abstract class User extends Identifiable implements Serializable {


	private String email;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String userName;
	private String password;

	private String address;

	private Date joinDate;

	private UserRole role = UserRole.SUBMITTER;

	public User () {
		super ();
	}
	
	public User ( String userName, String password,  String email, String address, Date joinDate, UserRole role ) 
	{
		super ();
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.joinDate = joinDate;
		this.role = role;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "username", unique = true, nullable = false )
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		// You must use the getters, otherwise lazy loaading in Hibernate will make this fail.
		
		if (getEmail () != null ? !getEmail ().equals(user.getEmail ()) : user.getEmail () != null) return false;
		if (getPassword () != null ? !getPassword ().equals(user.getPassword ()) : user.getPassword () != null) return false;
		if (!getUserName ().equals(user.getUserName ())) return false;

		return true;
	}

	@Override
	public int hashCode() 
	{
		// You must use the getters, otherwise lazy loaading in Hibernate will make this fail.

		int result = getEmail () != null ? email.hashCode() : 0;
		result = 31 * result + getUserName ().hashCode();
		result = 31 * result + (getPassword () != null ? password.hashCode() : 0);
		return result;
	}

	@Override
	public String toString () {
		return "User [getAddress()=" + getAddress () + ", getEmail()=" + getEmail () + ", getJoinDate()=" + getJoinDate ()
				+ ", getPassword()=" + getPassword () + ", getRole()=" + getRole () + ", getUserName()=" + getUserName () + "]";
	}
}
