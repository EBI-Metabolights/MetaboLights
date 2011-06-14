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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import uk.ac.ebi.bioinvindex.model.xref.ReferenceSource;
import uk.ac.ebi.bioinvindex.model.xref.Xref;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A superclass for classes with may have references to external data sources (e.g. DB, ontology)
 *
 * @author brandizi
 */
@MappedSuperclass
public abstract class HasReferences extends Accessible {

	private Collection<Xref> refs = new ArrayList<Xref>();

	@Transient
	public Collection<Xref> getXrefs () {
		return this.refs;
	}

	public void addXref ( Xref ref ) {
		this.refs.add (  ref );
  }

	protected void setXrefs ( Collection<Xref> refs ) {
    this.refs = refs;
	}

	public boolean removeXref ( Xref xref ) {
		return this.refs.remove(xref);
	}

	/**
	 * @return the xrefs having a source with a given accession. An empty list if none available.
	 */
	@SuppressWarnings("unchecked")
	public List<Xref> getXrefs ( final String sourceAcc ) 
	{
		return (List<Xref>) CollectionUtils.select ( refs, new Predicate () {
			public boolean evaluate ( Object xref ) {
				ReferenceSource source = ((Xref) xref).getSource ();
				if ( source == null ) return false;
				if ( sourceAcc == null ) return source.getAcc () == null;
				return sourceAcc.equals ( source.getAcc () );
			}
		} 
		);
	}
	
	/** @return the first element in {@link #getXrefs(String)} or null if it doesn't exist.
	 */
	public Xref getSingleXref ( final String sourceAcc ) {
		List<Xref> xrefs = getXrefs ( sourceAcc );
		return xrefs.size () == 0 ? null : xrefs.get ( 0 );
	} 


	/**
	 * It's like {@link #getXrefs(String)}, but uses a containment criterion
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Xref> getXrefsContaining ( final String sourceAcc ) 
	{
		return (List<Xref>) CollectionUtils.select ( refs, new Predicate () {
			public boolean evaluate ( Object xref ) {
				ReferenceSource source = ((Xref) xref).getSource ();
				if ( source == null ) return false;
				if ( sourceAcc == null ) return source.getAcc () == null;
				return StringUtils.containsIgnoreCase ( source.getAcc (), sourceAcc );
			}
		} 
		);
	}

	/** @return the first element in {@link #getXrefs(String)} or null if it doesn't exist.
	 */
	public Xref getSingleXrefContaining ( final String sourceAcc ) {
		List<Xref> xrefs = getXrefsContaining ( sourceAcc );
		return xrefs.size () == 0 ? null : xrefs.get ( 0 );
	} 


}
