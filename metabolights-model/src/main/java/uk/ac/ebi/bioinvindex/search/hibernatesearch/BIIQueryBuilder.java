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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.misc.ChainedFilter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermsFilter;
import org.hibernate.annotations.common.AssertionFailure;

import uk.ac.ebi.bioinvindex.model.Identifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Feb 28, 2008
 */
public class BIIQueryBuilder<T extends Identifiable> {


	private Analyzer analyzer = new StandardAnalyzer();

	public Query buildQuery(String searchPattern) throws ParseException {

		String[] productFields =
				{"title",
						"description",
						"design_value",
						"objective",
						"acc",

						"assay_token_end_point_name",
						"assay_token_technology_name",
						"assay_token_platform",

						"property_value",
						"factor_value",

						"contact_firstname",
						"contact_lastname",
						"contact_affiliation",

						"protocol_acc",
						"protocol_description",
						"protocol_name",
						"protocol_parameter_name",

						"publication_authorList",
						"publication_title",

						"investigation_acc",
						"investigation_description",
						"investigation_title"

				};

		PerFieldAnalyzerWrapper aWrapper =
				new PerFieldAnalyzerWrapper(analyzer);
		aWrapper.addAnalyzer("acc", new KeywordAnalyzer());
		aWrapper.addAnalyzer("investigation_acc", new KeywordAnalyzer());
		aWrapper.addAnalyzer("protocol_acc", new KeywordAnalyzer());

		QueryParser parser = new MultiFieldQueryParser(productFields, aWrapper);
		parser.setAllowLeadingWildcard(true);
		parser.setLowercaseExpandedTerms(false);
		org.apache.lucene.search.Query luceneQuery = parser.parse(searchPattern);

		System.out.println("luceneQuery = " + luceneQuery);
		return luceneQuery;
	}

	public Filter buildFilter(BIIFilterQuery<T> filterQuery) {

		if (filterQuery.getFilters().size() == 0) {
			throw new AssertionFailure("Chainedfilter has no filters to chain for");
		}

		List<Filter> filters = new ArrayList<Filter>();

		ChainedFilter chainedFilter = null;
		for (FilterField field : filterQuery.getFilters().keySet()) {
			List<String> values = filterQuery.getFilterValues(field);
			if (values == null) continue;


			for (String value : values) {
				//This was for TOKENIZED fields
//				StringReader reader = new StringReader(value);
//				TokenStream tokenStream = analyzer.tokenStream(null, reader);
//
//				try {
//					while (true) {
//						Token token = tokenStream.next();
//						if (token == null) break;
//
//						Term term = new Term(field.getName(), token.termText());
//						TermsFilter filter = new TermsFilter();
//						filter.addTerm(term);
//						filters.add(filter);
//					}

				//This was for UN_TOKENIZED fields
				Term term = new Term(field.getName(), value);
				TermsFilter filter = new TermsFilter();
				filter.addTerm(term);
				filters.add(filter);

//				} catch (IOException e) {
//					//ToDo: add exception handling
//					e.printStackTrace();
//				}

			}

		}

		chainedFilter = new ChainedFilter(filters.toArray(new Filter[]{}), ChainedFilter.AND);

		CachingWrapperFilter resultFilter = new CachingWrapperFilter(chainedFilter);

		return resultFilter;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

}
