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

import uk.ac.ebi.bioinvindex.model.Accessible;
import uk.ac.ebi.bioinvindex.model.Study;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * A superclass for {@link uk.ac.ebi.bioinvindex.model.processing.Processing} and {@link uk.ac.ebi.bioinvindex.model.processing.Node} classes.
 * Every graph element has a reference to {@link uk.ac.ebi.bioinvindex.model.Study} which it belongs to.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 27, 2007
 */
@MappedSuperclass
public abstract class GraphElement extends Accessible
{
	private Study study;
	private boolean isOriginal = true;
	
	protected GraphElement() {

	}

	protected GraphElement(Study study) {
		this.study = study;
	}

	protected GraphElement(Study study, boolean original) {
		this.study = study;
		isOriginal = original;
	}

	@ManyToOne( targetEntity = uk.ac.ebi.bioinvindex.model.Study.class)
	@JoinColumn(name = "STUDY_ID", nullable = false )
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@Transient
	public boolean isOriginal() {
		return isOriginal;
	}

	public void setOriginal(boolean original) {
		isOriginal = original;
	}

}
