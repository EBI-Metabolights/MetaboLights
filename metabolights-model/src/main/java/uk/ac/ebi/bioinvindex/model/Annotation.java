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

import uk.ac.ebi.bioinvindex.model.term.AnnotationType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *  A textual description of object. Can be used to store Comments from ISA-tab file or e.g. curation notes.
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Dec 20, 2007
 */
@Entity
@Table(name = "annotation")
public class Annotation extends Identifiable 
{
	private AnnotationType type;

	private String text;

	public Annotation() {
	}

	public Annotation(AnnotationType type, String text) {
		this.type = type;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Please see also notes in {@link AnnotationType}. 
	 *   
	 */
	@ManyToOne(targetEntity = AnnotationType.class)
	@JoinColumn(name = "TYPE_ID", nullable = false)
	public AnnotationType getType() {
		return type;
	}

	// TODO: DO NOT USE THIS FOR hashCode(), until we review the setters.
	public void setType(AnnotationType type) {
		this.type = type;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Annotation that = (Annotation) o;

		if (text != null ? !text.equals(that.text) : that.text != null) return false;

		if (type == null && that.type == null) {
			return true;
		} else if (type != null && that.type != null) {
			if (type.getValue() != null ? !type.getValue().equals(that.type.getValue()) : that.type.getValue() != null) return false;
		} else {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
		if ( type !=null ) {
			String types = type.getValue ();
			if ( types != null ) result = types.hashCode (); 
		}
		result = 31 * result + (text == null ? 0 : text.hashCode() );
		return result;
	}

	@Override
	public String toString () {
		return "Annotation { #" + getId () + ", text: " + this.getText () + ", type: " + this.getType () + " }";
	}

}
