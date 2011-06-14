package uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge;

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

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import uk.ac.ebi.bioinvindex.model.Protocol;
import uk.ac.ebi.bioinvindex.model.term.Parameter;
import uk.ac.ebi.bioinvindex.model.term.ProtocolType;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.AssayInfoDelimiters;

import java.util.Collection;

/**
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Feb 22, 2008
 */
public class ProtocolBridge implements FieldBridge, AssayInfoDelimiters {


	public void set(String s, Object o, Document document, LuceneOptions luceneOptions) {
		if (o == null) return;

		Collection<Protocol> protocols = (Collection<Protocol>) o;

		for (Protocol protocol : protocols) {

			Field field = new Field(s + "acc", protocol.getAcc(), luceneOptions.getStore(), luceneOptions.getIndex());
			document.add(field);

			field = new Field(s + "name", protocol.getName(), luceneOptions.getStore(), luceneOptions.getIndex());
			document.add(field);

			String description = protocol.getDescription();
			if (description != null) {
				field = new Field(s + "description", description, luceneOptions.getStore(), luceneOptions.getIndex());
				document.add(field);
			}

			ProtocolType type = protocol.getType();
			if (type != null) {
				field = new Field(s + "type_acc", type.getAcc(), luceneOptions.getStore(), luceneOptions.getIndex());
				document.add(field);

				field = new Field(s + "type_name", protocol.getType().getName(), luceneOptions.getStore(), luceneOptions.getIndex());
				document.add(field);
			}

			for (Parameter parameter : protocol.getParameters()) {
				field = new Field(s + "parameter_name", parameter.getValue(), luceneOptions.getStore(), luceneOptions.getIndex());
				document.add(field);
			}

		}
	}
}