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

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.AssayTechnology;
import uk.ac.ebi.bioinvindex.model.xref.Xref;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.AssayInfoDelimiters;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Creates a  String template: endpoint|technology|number|&&&acc1!!!url1&&&acc2!!!url2
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Feb 22, 2008
 */
public class AssayBridge implements FieldBridge, AssayInfoDelimiters {

	public void set(String s, Object o, Document document, LuceneOptions luceneOptions) {
		Map<String, AssyaTypeInfo> assayTypeToInfo = new HashMap<String, AssyaTypeInfo>();

		Collection<Assay> assays = (Collection<Assay>) o;
		for (Assay assay : assays) {

			String type = buildType(assay);

			AssyaTypeInfo info = assayTypeToInfo.get(type);
			if (info == null) {
				info = new AssyaTypeInfo();
				assayTypeToInfo.put(type, info);
			}

			for (Xref xref : assay.getXrefs()) {
				StringBuilder sb = new  StringBuilder();
				sb.append(xref.getAcc());
				if (xref.getSource() != null) {
					sb.append(ACC_URL_DELIM);
					//ToDO: use annotation which contains url regexp instead
					sb.append(xref.getSource().getUrl());
				}

				info.addAccession(sb.toString());
			}

			info.increaseCount();
		}

		for (String type : assayTypeToInfo.keySet()) {
			StringBuilder fullInfo = new StringBuilder();
			fullInfo.append(type);
			fullInfo.append(FIELDS_DELIM);
			AssayBridge.AssyaTypeInfo info = assayTypeToInfo.get(type);
			fullInfo.append(info.getCount());
			fullInfo.append(FIELDS_DELIM);
			for (String acc : info.getAccessions()) {
				fullInfo.append(ACC_DELIM);
				fullInfo.append(acc);
			}
			Field fvField = new Field(StudyBrowseField.ASSAY_INFO.getName(), fullInfo.toString(), luceneOptions.getStore(), luceneOptions.getIndex());
			document.add(fvField);

		}
	}

	private String buildType(Assay assay) {
		String type = assay.getMeasurement().getName() + "|" + assay.getTechnologyName ();
		return type;
	}

	
	private class AssyaTypeInfo {

		private int count = 0;

		private Set<String> accessions = new HashSet<String>();

		public int getCount() {
			return count;
		}

		public Set<String> getAccessions() {
			return accessions;
		}

		public void increaseCount() {
			count++;
		}

		public void addAccession(String acc) {
			accessions.add(acc);
		}
	}
}
