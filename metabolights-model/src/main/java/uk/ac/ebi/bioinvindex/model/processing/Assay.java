package uk.ac.ebi.bioinvindex.model.processing;

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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import uk.ac.ebi.bioinvindex.model.HasReferences;
import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.term.AssayTechnology;
import uk.ac.ebi.bioinvindex.model.term.Measurement;
import uk.ac.ebi.bioinvindex.model.xref.Xref;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.OntologyEntryBridge;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Transient;

import java.util.Collection;


/**
 * Assays can be characterized as the smallest complete unit of experimentation; i.e. one hybridization equals one
 * assay; each technical replicate represents an additional assay; one LC-MS run equals one assay; a single clinical
 * chemistry assay is (of course) one assay; a multiplexed (^n) microarray equals n assays; and a MALDI MS chip with n
 * spots could perform up to n assays (i.e. all spots analyzed).
 * <p/>
 * Assay object is alway assosiated with a {@link uk.ac.ebi.bioinvindex.model.Material}, which correspondes to
 * i.e hybridization.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 30, 2007
 */
@Entity
@Table(name = "ASSAY")
public class Assay extends HasReferences {

	@ContainedIn
	protected Study study;

	@Fields({
			@Field(index = Index.UN_TOKENIZED, store = Store.YES, name = "technology_",
					bridge = @FieldBridge(impl = OntologyEntryBridge.class)),
			@Field(index = Index.TOKENIZED, store = Store.NO, name = "token_technology_",
					bridge = @FieldBridge(impl = OntologyEntryBridge.class))
	})
	private AssayTechnology technology;

	@Fields({
			@Field(index = Index.UN_TOKENIZED, store = Store.YES, name = "end_point_",
					bridge = @FieldBridge(impl = OntologyEntryBridge.class)),
			@Field(index = Index.TOKENIZED, store = Store.NO, name = "token_end_point_",
					bridge = @FieldBridge(impl = OntologyEntryBridge.class))
	})
	private Measurement measurement;


	@Fields({
	@Field(index = Index.UN_TOKENIZED, store = Store.YES, name = "platform"),
	@Field(index = Index.TOKENIZED, store = Store.NO, name = "token_platform")
			})
	private String assayPlatform;

	private Material material;

	private Node node;
	//ToDo: decide if we need direct link to the data
//	private Collection<Data> data = new ArrayList<Data>();

	public Assay() {
		super();
	}

	public Assay(Study study) {
		this.study = study;
		if ( study != null )
			study.addAssay(this);
	}

	
	@ManyToOne( targetEntity = AssayTechnology.class)
	// it is nullable, since we accept assays with measurement but without any particular technology specified.
	@JoinColumn(name = "technology", nullable = true)
	public AssayTechnology getTechnology() {
		return technology;
	}

	/** A facility that returns {@link #getTechnology()}.getName() if technology is not null, "" otherwise.
	 *  The name is also passed to {@link StringUtils#trimToEmpty(String)} before returning. 
	 */
	@Transient
	public String getTechnologyName () {
		return technology == null ? "" : StringUtils.trimToEmpty ( technology.getName () );
	}
	
	
	public void setTechnology(AssayTechnology technology) {
		this.technology = technology;
	}

	// TODO: we should change the old "EndPoint" name, but this would need changing all the data set files.
	@ManyToOne(
			targetEntity = Measurement.class)
	@JoinColumn(name = "endpoint", nullable = false)
	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	@Column(name = "platform")
	public String getAssayPlatform() {
		return assayPlatform;
	}

	public void setAssayPlatform(String assayPlatform) {
		this.assayPlatform = assayPlatform;
	}

	@ManyToOne(targetEntity = Study.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDY_ID", nullable = false)
	public Study getStudy() {
		return this.study;
	}

	/**
	 * Sets the study this assay is associated to.
	 */
	public void setStudy(Study study) {
		//TODO: Assay always belongs to the Study, so study cannot be null
//		if (study == null) throw new IllegalArgumentException("Study cannot be null");

//		try {
//			if (this.study == study) return;
//
//			if (this.study != null && this.study.getAssays().contains(this))
//				this.study.removeAssay(this);
//
//			this.study = study;
//			if (study != null && !study.getAssays().contains(this))
//				study.addAssay(this);
//		}
//		catch (LazyInitializationException ex) {
////			 Hibernate is working for us, let us not disturb it
//			this.study = study;
//		}

		this.study = study;
	}


	/**
	 * Returns Material assosiated with the Assay
	 *
	 * @return
	 */
	//ToDo: should be not nullable
	@ManyToOne(targetEntity = uk.ac.ebi.bioinvindex.model.Material.class)
	@JoinColumn(name = "MATERIAL_ID"/*, nullable = false */)
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Assay{ ");
		sb.append(this.getId());
		sb.append(", acc:=");
		sb.append(acc);

		sb.append(" study:=");
		if (study != null) {
			sb.append("{ id:=").append(study.getId()).append(", acc:= ").append(study.getAcc());
			sb.append(", title:=\"").append(study.getTitle()).append("\" }");
		}

		sb.append("\n technology:=");
		sb.append(technology);
		sb.append("\n measurement:=");
		sb.append(measurement);
		sb.append(" material:=");
		if (material != null) {
			sb.append(material.getName());
		}
		sb.append("\n annotations:=");
		sb.append( getAnnotations () );
		sb.append("\n}");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Assay assay = (Assay) o;

		if (measurement != null ? !measurement.equals(assay.measurement) : assay.measurement != null) return false;
		if (material != null ? !material.equals(assay.material) : assay.material != null) return false;
		if (study != null ? !study.equals(assay.study) : assay.study != null) return false;
		if (technology != null ? !technology.equals(assay.technology) : assay.technology != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (study != null ? study.hashCode() : 0);
		result = 31 * result + (technology != null ? technology.hashCode() : 0);
		result = 31 * result + (measurement != null ? measurement.hashCode() : 0);
		result = 31 * result + (material != null ? material.hashCode() : 0);
		return result;
	}

	@OneToMany
	@JoinTable(
			name = "assay2xref",
			joinColumns = {@JoinColumn(name = "assay_id", nullable = false)},
			inverseJoinColumns = @JoinColumn(name = "xref_id", nullable = false)
	)
	@Fetch(FetchMode.SUBSELECT)
	@Override
	public Collection<Xref> getXrefs() {
		return super.getXrefs();
	}

}
