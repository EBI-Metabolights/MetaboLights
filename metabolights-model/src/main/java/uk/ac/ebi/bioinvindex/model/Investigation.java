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

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import uk.ac.ebi.bioinvindex.model.xref.Xref;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * High level object to group multiple Study.
 * @author brandizi
 *
 */
@Entity
@Table (name = "INVESTIGATION")
@Indexed (index = "bii")
public class Investigation extends HasReferences {

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String title;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String description;

	private Date submissionDate, releaseDate;

	@ContainedIn
	private Collection<Study> studies = new ArrayList<Study>();

	// TODO: Shouldn't it have this? (MB) @IndexedEmbedded(prefix = "contact_")
	private Collection<Contact> contacts = new ArrayList<Contact>();

	// TODO: Shouldn't it have this? (MB) @IndexedEmbedded(prefix = "publication_")
	private Collection<Publication> publications = new ArrayList<Publication>();


	protected Investigation () {
		super ();
	}

	public Investigation (String title) {
	  super ();
	  this.title = title;
  }


	public String getTitle () {
  	return title;
  }

	public void setTitle (String title) {
  	this.title = title;
  }

	@Lob
	public String getDescription () {
  	return description;
  }

	public void setDescription (String description) {
  	this.description = description;
  }

	@Temporal(TemporalType.DATE)
	public Date getSubmissionDate () {
  	return submissionDate;
  }

	public void setSubmissionDate (Date submissionDate) {
  	this.submissionDate = submissionDate;
  }

	@Temporal(TemporalType.DATE)
	public Date getReleaseDate () {
  	return releaseDate;
  }

	public void setReleaseDate (Date releaseDate) {
  	this.releaseDate = releaseDate;
  }


	@OneToMany(targetEntity = Contact.class, mappedBy = "investigation" )
	public Collection<Contact> getContacts() {
		return contacts;
	}

	protected void setContacts(Collection<Contact> contacts) {
		this.contacts = contacts;
	}

	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	public boolean removeContact ( Contact contact ) {
		return contacts.remove ( contact );
	}
	
	
	@OneToMany( targetEntity = Publication.class,	mappedBy = "investigation" /*, cascade = CascadeType.ALL */	)
	public Collection<Publication> getPublications() {
		return this.publications;
	}


	protected void setPublications(Collection<Publication> publications) {
		this.publications = publications;
	}

	/** Symmetrically fixes the investigation in the publication */
	public void addPublication ( Publication publication )
	{
		if ( publication == null )
			throw new IllegalArgumentException ( "publication cannot be null!" );

		if ( publication.getInvestigation () != this )
			publication.setInvestigation ( this );
		publications.add ( publication );
	}

	/** Symmetrically fixes the investigation in the publication */
	public boolean removePublication ( Publication publication )
	{
		if ( publication == null ) return false;
		if ( publication.getInvestigation () != this ) return false;
		this.publications.remove ( publication );
	  publication.setInvestigation ( null );
	  return true;
	}


	@ManyToMany( mappedBy = "investigations", targetEntity = Study.class )
	public Collection<Study> getStudies () {
		return studies;
  }

	protected void setStudies (Collection<Study> studies) {
  	this.studies = studies;
  }

	public void addStudy ( Study study )
	{
		this.studies.add ( study );
		if ( !study.getInvestigations ().contains ( this ))
			study.addInvestigation ( this );
	}

	public void removeStudy ( Study study )
	{
		this.studies.remove ( study );
		if ( study.getInvestigations ().contains ( this ))
			study.removeInvestigation ( this );
	}

	@Override
	@Transient
	@Field(name = "acc", index = Index.UN_TOKENIZED, store = Store.YES)
	public String getAcc() {
		return super.getAcc();
	}
	
	@Override
	public String toString()
	{
		String studies = "";
		for ( Study study: getStudies () ) {
			studies += String.format (
				"Study{ id = '%s', accession = '%s', title = '%s' }\n",
				study.getId (), study.getAcc (), study.getTitle ()
			);
		}

		return String.format (
			"Investigation{ id = %s, accession = '%s', title = '%s', description = '%s', submitted = %s, released = %s, " +
			"contacts = '%s', publications = %s, Xrefs = %s\n\n Studies: {\n %s \n  }\n}\n\n",
			getId(), getAcc (), getTitle(), StringUtils.substring ( getDescription (), 0, 20), 
			getSubmissionDate (), getReleaseDate (), getContacts (), getPublications (), 
			getXrefs (), studies
		);

	}


	@OneToMany
	@JoinTable(
		name = "investigation2xref",
		joinColumns = {@JoinColumn(name = "investigation_id", nullable = false)},
		inverseJoinColumns = @JoinColumn(name = "xref_id", nullable = false)
	)
	@Override
	public Collection<Xref> getXrefs () {
		return super.getXrefs ();
	}


}
