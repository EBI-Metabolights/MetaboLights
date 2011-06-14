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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import uk.ac.ebi.bioinvindex.model.Annotatable;
import uk.ac.ebi.utils.regex.RegEx;

import java.util.List;

/**
 * A type for {@link uk.ac.ebi.bioinvindex.model.Annotation} object.
 * 
 * <dl><dt>Date:</dt><dd>Jan 4, 2008</dd></dl>
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * 
 *
 */
@Entity
@Table(name = "annotation_type")

public class AnnotationType extends FreeTextTerm
{
	/** Useful for searching comment annotations, via {@link Annotatable#getAnnotationValuesByRe(RegEx)} */
	public final static int COMMENT_PREFX_LEN = "comment:".length ();
	/** Useful for searching comment annotations, via {@link Annotatable#getAnnotationValuesByRe(RegEx)} */
	public final static RegEx COMMENT_RE = new RegEx ( "comment\\:.*" );

	protected AnnotationType() {
	}

	public AnnotationType(String value) {
		super(value);
	}

	@ManyToMany( targetEntity = OntologyTerm.class )
	@JoinTable(
		name = "AnnotationType2OntologyEntry",
		joinColumns = {@JoinColumn(name = "AT_ID")},
		inverseJoinColumns = @JoinColumn(name = "OE_ID")
	)
	public List<OntologyTerm> getOntologyTerms() {
		return super.getOntologyTerms();
	}

}
