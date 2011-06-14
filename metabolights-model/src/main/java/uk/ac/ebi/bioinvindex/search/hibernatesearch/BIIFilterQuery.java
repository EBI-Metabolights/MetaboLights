package uk.ac.ebi.bioinvindex.search.hibernatesearch;

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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Feb 28, 2008
 */
public abstract class BIIFilterQuery<T extends Identifiable> {

	private String searchText;
	private Map<FilterField, List<String>> filters = new EnumMap<FilterField, List<String>>(FilterField.class);

	public BIIFilterQuery() {
		initStudyFilter();
	}

	public BIIFilterQuery(String searchText) {
		this.searchText = searchText;
		initStudyFilter();
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Map<FilterField, List<String>> getFilters() {
		return filters;
	}

	public void addFilterValue(FilterField field, String value) {
		List<String> list = filters.get(field);
		if (list == null) {
			list = new ArrayList<String>();
			filters.put(field, list);
		}

		list.add(value);
	}

	public List<String> getFilterValues(FilterField field) {
		return filters.get(field);
	}

	private void initStudyFilter() {
//			Type type1 = getClass()
//					.getGenericSuperclass();
//			Type type = ((ParameterizedType) type1).getActualTypeArguments()[0];
//			Class<T> clazz = (Class<T>) type;

		addFilterValue(FilterField.CLASS_NAME, getDocumentClass().getName());
	}

	protected abstract Class getDocumentClass();

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BIIFilterQuery)) return false;

		BIIFilterQuery that = (BIIFilterQuery) o;

		if (!filters.equals(that.filters)) return false;
		if (searchText != null ? !searchText.equals(that.searchText) : that.searchText != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (searchText != null ? searchText.hashCode() : 0);
		result = 31 * result + filters.hashCode();
		return result;
	}

	public String toString() {
		return "BIIFilterQuery{" +
				"searchText='" + searchText + '\'' +
				", filters=" + filters +
				'}';
	}
}
