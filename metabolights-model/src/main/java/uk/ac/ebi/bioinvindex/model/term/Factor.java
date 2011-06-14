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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The name of the Factors used in the Study and/or Assays files. Factors should correspond to the independent variable
 * manipulated by experimentalists and intended to affect biological systems in such a way that an assay is devised to
 * measure the responses of the biological system to the perturbation by following a response variable (also known as
 * dependent variable).
 *
 * TODO: We currently *don't* have Characteristics or Parameters that are factors too. The model was initially designed
 * to support such a use case, but we don't need that for the moment. 
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jan 4, 2008
 */

// TODO: setRole() should raise an exception.
@Entity
@DiscriminatorValue("Factor")
public class Factor extends Property<FactorValue> {

	protected Factor() {
	}

	public Factor(String value, int order) {
		super(value, PropertyRole.FACTOR, order);
	}

	public Factor(int order) {
		super(PropertyRole.FACTOR, order);
	}

}
