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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import uk.ac.ebi.bioinvindex.model.Annotatable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This is a super class for all "Terms" (for example, characteristic/factor value, protocol parameter) which might have 
 * a free text value as well as be annotated with ontology terms. 
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 12, 2007
 */

@MappedSuperclass
public abstract class FreeTextTerm extends Annotatable {

	@Field(index = Index.TOKENIZED, store = Store.YES)
	protected String value;

	protected List<OntologyTerm> ontologyTerms = new ArrayList<OntologyTerm>();

	protected FreeTextTerm() {
	}

//	protected FreeTextTerm(String acc, String value) {
//		this.setAcc(acc);
//		this.setValue(value);
//	}


	protected FreeTextTerm(String value) {
		this.value = value;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * It is transient because the responsibility to define this mapping inevitably must be left to the sub-classes
	 */
	@Transient
	public List<OntologyTerm> getOntologyTerms() {
		return Collections.unmodifiableList (ontologyTerms);
	}

	/**
	 * @return the first term returned by {@link #getOntologyTerms()}
	 * 
	 */
	@Transient
	public OntologyTerm getSingleOntologyTerm () {
		Iterator<OntologyTerm> itr = ontologyTerms.iterator ();
		return itr.hasNext () ? itr.next () : null;
	}

	
	protected void setOntologyTerms(List<OntologyTerm> ontologyTerms) {
		this.ontologyTerms = ontologyTerms;
	}

	public void addOntologyTerm(OntologyTerm term) {
		if (!ontologyTerms.contains(term)) {
			this.ontologyTerms.add(term);
		}
	}

	public boolean removeOntologyTerm(OntologyTerm term) {
		return ontologyTerms.remove(term);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		FreeTextTerm term = (FreeTextTerm) o;

		if (value != null ? !value.equals(term.value) : term.value != null) return false;
		if (ontologyTerms != null ? !ontologyTerms.equals(term.ontologyTerms) : term.ontologyTerms != null) return false;

		return true;
	}

	public int hashCode() {
		int result = super.hashCode(); 
		result = 31 * result + (value != null ? value.hashCode() : 0); 
		result = 31 * result + (ontologyTerms != null ? ontologyTerms.hashCode() : 0); 
		return result;
	}


	public String toString() {
		return "FreeTextTerm{" +
				"id=" + this.getId() +
				", name='" + value + '\'' +
				", ontologyTerms=" + ontologyTerms +
				'}';
	}
}
