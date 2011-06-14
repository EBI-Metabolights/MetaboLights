package uk.ac.ebi.bioinvindex.model.xref;

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

import uk.ac.ebi.bioinvindex.model.Identifiable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * The class links assay type (measurementType and technologyType) to ReferenceSource. The ReferenceSource object
 * contains a list of annotations which specify locations for different data types. Values for AnnotationType are taken
 * from {@link uk.ac.ebi.bioinvindex.model.term.AnnotationTypes} ennumeration.

 * @author: Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: May 5, 2009
 */

@Entity
@Table(name = "AssayType2REFERENCE_SOURCE")
public class AssayTypeDataLocation extends Identifiable {

	//ToDo: use Measurement and AssayTechnology objects instead
	private String measurementType;
	private String technologyType;

	private ReferenceSource referenceSource;

	protected AssayTypeDataLocation() {
	}

	public AssayTypeDataLocation(String measurementType, String technologyType, ReferenceSource referenceSource) {
		this.measurementType = measurementType;
		this.technologyType = technologyType;
		this.referenceSource = referenceSource;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public String getTechnologyType() {
		return technologyType;
	}

	/**
	 * TODO: This is to be reviewed and possibly turned into OneToOne. If you have two different entries
	 * (ie: two {@link AssayTypeDataLocation}), by associating the same source to them, you'll merge
	 * both entry annotations (eg: raw data path) into the same source, resulting in a mess that 
	 * doesn't make sense. At the moment this situation is always avoided by the {@link DataSourceLoader}, 
	 * by assigning unique accessions to the sources linked here. This means that de-facto we're keeping
	 * the relation as one-to-one.  
	 */
	@ManyToOne( targetEntity = ReferenceSource.class )
	@JoinColumn(name = "source")
	public ReferenceSource getReferenceSource() {
		return referenceSource;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	public void setTechnologyType(String technologyType) {
		this.technologyType = technologyType;
	}

	public void setReferenceSource(ReferenceSource referenceSource) {
		this.referenceSource = referenceSource;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AssayTypeDataLocation that = (AssayTypeDataLocation) o;

		if (!measurementType.equals(that.measurementType)) return false;
		if (!referenceSource.equals(that.referenceSource)) return false;
		if (technologyType == null && that.technologyType != null || !technologyType.equals(that.technologyType)) 
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = measurementType.hashCode();
		if ( technologyType != null )
			result = 31 * result + technologyType.hashCode();
		result = 31 * result + referenceSource.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "AssayTypeDataLocation{" +
				"measurementType='" + measurementType + '\'' +
				", technologyType='" + technologyType + '\'' +
				", referenceSource=" + referenceSource +
				'}';
	}
}
