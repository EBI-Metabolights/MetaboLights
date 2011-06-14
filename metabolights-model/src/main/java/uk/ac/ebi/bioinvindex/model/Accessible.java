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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * An accessible object is any {@link Identifiable} object that also has an
 * assigned accession number.  An accession number is intended as a database
 * identifier that can be assigned to objects for external reuse, such as
 * identifiers in the interface, that are distinct from the internal database
 * identifier.  Any objects in the model that should be reusable, or otherwise
 * identifiable, by their accession number should override this class.
 * <p/>
 * author: Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 11, 2007
 */

@MappedSuperclass
public abstract class Accessible extends Annotatable {

	protected String acc;

	protected Accessible() {
		super();
	}

	@Column(name = "ACC", unique = true, nullable=false)
	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}


	/**
	 * Annotations are excluded from equals and hashCode methods
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof Accessible)) return false;

		Accessible that = (Accessible) o;

		// PLEASE NOTE: I have at least one test case (OntologyEntryEJB3DAOTest) where it happens that 
		// a ReferenceSource is wrapped by some Hibernate subclass, probably used for lazy loading.
		// Unfortunately this wrapper make it happen at some point that 1) that.acc == null and 2) at the same time
		// that.getAcc() returns the correct value that is in the DB.
		// 
		// TODO: Probably all the variables should be private and accessed only via accessors.
		// 
		final String acc = getAcc ();
		if ( acc != null ? !acc.equals( that.getAcc() ) : that.getAcc () != null ) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final String acc = getAcc();
		int result = 31 * (acc != null ? acc.hashCode() : 0);
		return result;
	}

	@Override
	public String toString () 
	{
		return "Accessible { id: " + getId() + ", acc: " + getAcc () + " }";
	}
	
}
