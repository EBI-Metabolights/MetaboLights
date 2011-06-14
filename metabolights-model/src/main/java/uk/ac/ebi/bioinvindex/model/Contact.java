/*
 * Contact.java
 *
 * Created on August 27, 2007, 11:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.ac.ebi.bioinvindex.model;

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

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import uk.ac.ebi.bioinvindex.model.term.ContactRole;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Object representing the contact details for person or organization responsible for the Investigation or Study.
 * @author brandizi
 */
@Entity
@Table(name = "contact")
@Indexed(index = "bii")
public class Contact extends Annotatable
{

	@ContainedIn
	private Study study;

	private Investigation investigation;

	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String firstName;

	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String lastName;
	private String midInitials;
	private String email;
	private String phone;
	private String fax;
	private String address;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String affiliation;
	private String url;
	private List<ContactRole> roles = new ArrayList<ContactRole> ();

    protected Contact() {
    }


    public Contact(String firstName, String midInitials, String lastName, String email) {
        // TODO: Ignore nulls?
        this.setFirstName(firstName);
        this.setMidInitials(midInitials);
        this.setLastName(lastName);
        this.setEmail(email);
    }


    @ManyToOne(targetEntity = Study.class)
    @JoinColumn(name = "study_id", nullable = true)
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }


    @ManyToOne(targetEntity = Investigation.class)
    @JoinColumn(name = "investigation_id", nullable = true)
    public Investigation getInvestigation() {
        return investigation;
    }

    public void setInvestigation (Investigation investigation) {
        this.investigation = investigation;
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

    public String getMidInitials() {
        return midInitials;
    }

    public void setMidInitials(String midInitials) {
        this.midInitials = midInitials;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getAddress () {
			return address;
		}

		public void setAddress ( String address ) {
			this.address = address;
		}

		public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
    /**
     * @deprecated Many roles are now available, see {@link #getRoles()}.
     * @return
     */
    @Deprecated
    @Transient
    public ContactRole getRole() {
        return roles.size () > 0 ? roles.get ( 0 ) : null;
    }

    /**
     * This remove all previous roles and set the role so that {@link #getRoles()} will return 
     * a singleton collection. 
     * 
     * @param contactRole
     */
    @Deprecated
    public void setRole (ContactRole contactRole) {
      roles.clear ();
      addRole ( contactRole );
    }

  	@ManyToMany( targetEntity = ContactRole.class )
  	@JoinTable( 
  		name = "role2contact", 
  		joinColumns = @JoinColumn ( name = "contact_id" ), 
  		inverseJoinColumns = @JoinColumn ( name = "role_id" )
  	)
    public Collection<ContactRole> getRoles () {
    	return Collections.unmodifiableCollection ( roles );
    } 

    protected void setRoles ( List<ContactRole> roles ) {
    	this.roles = roles;
    }
    
    
    public boolean removeRole ( ContactRole role )
    {
    	boolean rval = false;
    	for ( ContactRole rolei: new ArrayList<ContactRole> ( roles ) ) {
    		if ( rolei.equals ( role ) ) rval |= roles.remove ( rolei );
    	}
    	return rval;
    }

    public void addRole ( ContactRole role ) {
    	roles.add ( role );
    }

    
		@Transient
		/** Gets Name+Middle+Surname */
		public String getFullName () {
			StringBuilder sb = new StringBuilder();
			sb.append(firstName);
			if (midInitials != null && midInitials.length() > 0) {
				sb.append(" ");
				sb.append(midInitials);
			}
			sb.append(" ");
			sb.append(lastName);

			return sb.toString();



//			String midStr = StringUtils.trimToEmpty ( getMidInitials () );
//			if ( midStr.length () != 0 ) midStr = " " + midStr;
//
//			return String.format ( "%s%s %s",
//				trim ( this.getFirstName () ), midStr, trim ( this.getLastName () ) );
		}

    public String toString() {
        String roleS = "", sep = "";
        if ( roles != null ) for ( ContactRole role: roles ) {
        	roleS += String.format ( "%s{ '%s' ( '%s' ) }", sep, role.getAcc(), role.getName() );
        	sep = ", ";
        }
        String owner = ""; 
        if ( investigation != null )
        	owner = String.format ( "Investigation: { #%d, '%s', '%s' }", 
        		investigation.getId (), investigation.getAcc (), investigation.getTitle () 
        	);
        else if ( study != null )
        	owner = String.format ( "Study: { #%d, '%s', '%s' }", 
        		study.getId (), study.getAcc (), study.getTitle () 
        	);
        return String.format(
					"Contact{ #%d, '%s' ('%s') '%s' <%s>\n  Roles: %s\n  Phone: '%s', Fax: '%s'\n  Affiliation: '%s', " +
					"URL: <%s>, owner: %s }",
          getId (), getFirstName(), getMidInitials(), getLastName(), getEmail(), roleS, getPhone(), getFax(), 
          getAffiliation(), getUrl(), owner
        );
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Contact contact = (Contact) o;

		// If they are attached to different physical studies or investigations they are different!
		if (investigation != null && investigation.getAcc() != null && contact.investigation != null && contact.investigation.getAcc() != null) {
			if ( !investigation.equals(contact.investigation.getAcc()) ) return false;
		}

		if (study != null && study.getAcc() != null && contact.study != null && contact.study.getAcc() != null) {
			if ( study.getAcc().equals(contact.study.getAcc()) ) return false;
		}

		if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
		if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
		if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
		if (midInitials != null ? !midInitials.equals(contact.midInitials) : contact.midInitials != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (midInitials != null ? midInitials.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (study != null && study.getAcc() != null ? study.getAcc().hashCode() : 0);
		result = 31 * result + (investigation != null && investigation.getAcc() != null ? investigation.getAcc().hashCode() : 0);
		return result;
	}

	//    /** Resets it to the default object identity */
//  	@Override
//    public boolean equals(Object o) {
//  		return this == o;
//  	}
//
//  	/** Resets it to the default {@link Object#hashCode()} */
//  	@Override
//  	public int hashCode() {
//  		return hashCode;
//  	}


}
