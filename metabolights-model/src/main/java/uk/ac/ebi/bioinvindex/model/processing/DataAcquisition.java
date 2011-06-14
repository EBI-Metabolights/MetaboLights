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

import uk.ac.ebi.bioinvindex.model.Data;
import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.Study;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Object representing the test run on the {@link uk.ac.ebi.bioinvindex.model.Material} that produces {@link
 * uk.ac.ebi.bioinvindex.model.Data}. Takes {@link uk.ac.ebi.bioinvindex.model.processing.MaterialNode}
 * as input nodes and {@link uk.ac.ebi.bioinvindex.model.processing.DataNode} as output.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 30, 2007
 */
@Entity
@DiscriminatorValue("DataAcquisition")
public class DataAcquisition extends Processing<MaterialNode, DataNode> {

	public DataAcquisition() {
		super();
	}

	public DataAcquisition(Study study) {
		super(study);
	}

	public DataAcquisition(Study study, boolean original) {
		super(study, original);
	}


	public String toStringVerbose() {
		StringBuilder result = new StringBuilder("DataProcessing { " + this.getId() + "\n");

		result.append("  Inputs:\n");
		for (MaterialNode input : this.getInputNodes()) {
			Material material = input.getMaterial();
			result.append("    " + material);
		}

		result.append("\n  Outputs:\n");
		for (DataNode output : this.getOutputNodes()) {
			Data data = output.getData();
			result.append("    " + data);
		}

		result.append("\n  Protocols:\n");
		for (ProtocolApplication protocolApp : this.getProtocolApplications()) {
			result.append("    " + protocolApp + "\n");
		}
		result.append("\n");


		return result.toString();
	}
}