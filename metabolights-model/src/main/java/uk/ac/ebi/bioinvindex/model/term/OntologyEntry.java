package uk.ac.ebi.bioinvindex.model.term;

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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import uk.ac.ebi.bioinvindex.model.Accessible;
import uk.ac.ebi.bioinvindex.model.Annotatable;
import uk.ac.ebi.bioinvindex.model.xref.ReferenceSource;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;

/**
 * A superclass to represent an ontology entry. It captures acc, nama, and a reference to the ontology source.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 12, 2007 TODO: An OE accession is not necessary unique! What is
 *         unique is OE's accession PLUS OE's source accession (or source).
 */
@Entity
@Table(name = "ontology_entry")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "obj_type", discriminatorType = DiscriminatorType.STRING, length = 255)
public abstract class OntologyEntry extends Annotatable {

	@Field(index = Index.TOKENIZED, store = Store.YES)
	protected String name;

	protected ReferenceSource source;

	protected String acc;

	@Column(name = "ACC", unique = false, nullable=true)
	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	protected OntologyEntry() {
		super();
	}

	protected OntologyEntry(String name, ReferenceSource source) {
		this.name = name;
		this.source = source;
	}

	public OntologyEntry(String acc, String name, ReferenceSource source) {
		this.acc = acc;
		this.name = name;
		this.source = source;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(targetEntity = ReferenceSource.class)
	@JoinColumn(name = "source", nullable = false)
	public ReferenceSource getSource() {
		return this.source;
	}

	public void setSource(ReferenceSource source) {
		this.source = source;
	}

	/**
	 * Returns true if ACC, ReferenceSource and ReferenceSource.acc are specified
	 *
	 * @return
	 */
	@Transient
	public boolean isValid() {
		return acc != null && source != null && source.getAcc() != null;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OntologyEntry)) return false;
		if (!super.equals(o)) return false;

		OntologyEntry that = (OntologyEntry) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (source != null ? !source.equals(that.source) : that.source != null) return false;

		return true;
	}

	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (source != null ? source.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "OntologyEntry{" +
				" id = '" + this.getId() + '\'' +
				", accession= '" + this.getAcc() + '\'' +
				", name='" + getName() + '\'' +
				", source=" + getSource() +
				'}';
	}
}
