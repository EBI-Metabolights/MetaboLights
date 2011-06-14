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

import uk.ac.ebi.bioinvindex.model.term.PublicationStatus;
import uk.ac.ebi.bioinvindex.model.xref.Xref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;


/**
 * Object representing the publications associated to an Investigation or Study.
 *
 * @author brandizi
 */
@Entity
@Table(name = "publication")
@Indexed(index = "bii")
public class Publication extends Annotatable {

	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String title;

	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String authorList;

	private String pmid, doi;
	
	@ContainedIn
	private Study study;
	private Investigation investigation;
	private PublicationStatus status;

	protected Publication() {
	}


	public Publication(String title, String authorList) {
		this.setTitle(title);
		this.setAuthorList(authorList);
	}
	
	
	public String getPmid () {
		return pmid;
	}


	public void setPmid ( String pmid ) {
		this.pmid = pmid;
	}


	public String getDoi () {
		return doi;
	}

	public void setDoi ( String doi ) {
		this.doi = doi;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column( length=1998 )
	public String getAuthorList() {
		return this.authorList;
	}

	public void setAuthorList(String authorList) {
		this.authorList = authorList;
	}

	@ManyToOne(targetEntity = uk.ac.ebi.bioinvindex.model.Study.class
			/*, fetch = FetchType.EAGER*/)
	@JoinColumn(name = "study_id", nullable = true)
	public Study getStudy() {
		return this.study;
	}

	/**
	 * Sets the study this publication is about. Symmetrically removes the publication from previously set
	 * study and add the publication to the new study.
	 */
	protected void setStudy(Study study) {

		this.study = study;

//		try {
//
//			if (this.study == study) return;
//
//			if (this.study != null && this.study.getPublications().contains(this))
//				this.study.removePublication(this);
//
//			this.study = study;
//			if (study != null && !study.getPublications().contains(this))
//				study.addPublication(this);
//		}
//		catch (LazyInitializationException ex) {
//			// Hibernate is working for us, let's not disturb it
//			this.study = study;
//		}
	}


	@ManyToOne(targetEntity = uk.ac.ebi.bioinvindex.model.Investigation.class)
	@JoinColumn(name = "investigation_id", nullable = true)
	public Investigation getInvestigation() {
		return investigation;
	}

	/**
	 * Sets the investigation this publication is about. Symmetrically remove the publication from previously set
	 * investigation and add the publication to the new investigation.
	 */
	public void setInvestigation(Investigation investigation) {
//		try {
//			if ( this.investigation == investigation ) return;
//
//			if ( this.investigation != null && this.investigation.getPublications ().contains ( this ) )
//				this.investigation.removePublication ( this );
//
//			this.investigation = investigation;
//			if ( investigation != null && !investigation.getPublications ().contains ( this ) )
//				investigation.addPublication ( this );
//		}
//		catch ( LazyInitializationException ex ) {
//			// Hibernate is working for us, let's not disturb it
//			this.investigation = investigation;
//		}

		this.investigation = investigation;
	}


//	public void addXref(PublicationXrefImpl ref) {
//		super.addXref(ref);
//	}
	
	@ManyToOne(targetEntity = PublicationStatus.class)
	public PublicationStatus getStatus() {
		return this.status;
	}

	public void setStatus(PublicationStatus status) {
		this.status = status;
	}

	public String toString() {
		return "Publication Title:\n" + this.getTitle() + "\n(" + this.getAuthorList() + ")\n\n";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Publication that = (Publication) o;

		if (authorList != null ? !authorList.equals(that.authorList) : that.authorList != null) return false;
		if (doi != null ? !doi.equals(that.doi) : that.doi != null) return false;
		if (pmid != null ? !pmid.equals(that.pmid) : that.pmid != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;

		if (investigation != null && investigation.getAcc() != null && that.investigation != null && that.investigation.getAcc() != null) {
			if (!investigation.equals(that.investigation.getAcc())) return false;
		}

		if (study != null && study.getAcc() != null && that.study != null && that.study.getAcc() != null) {
			if (study.getAcc().equals(that.study.getAcc())) return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (authorList != null ? authorList.hashCode() : 0);
		result = 31 * result + (pmid != null ? pmid.hashCode() : 0);
		result = 31 * result + (doi != null ? doi.hashCode() : 0);
		result = 31 * result + (study != null && study.getAcc() != null ? study.getAcc().hashCode() : 0);
		result = 31 * result + (investigation != null && investigation.getAcc() != null ? investigation.getAcc().hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
