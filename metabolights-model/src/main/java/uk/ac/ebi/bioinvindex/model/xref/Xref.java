package uk.ac.ebi.bioinvindex.model.xref;

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 11, 200
 * 
 * TODO: it's not an Accessible but has accession, to be reviewed.
 *
 */
@Entity
@Table ( name = "xref" )
public class Xref extends Identifiable
{
	private String acc;
	private ReferenceSource source;

	protected Xref () {
		super ();
	}

	public Xref ( String acc ) {
		this();
		this.acc = acc;
	}

	public Xref ( String acc, ReferenceSource source ) {
		this ( acc );
		this.source = source;
	}
	
	@Column (name = "ACC" /* TODO: remove, two different sources can have same accession, unique = true */)
	public String getAcc () {
		return this.acc;
	}

	public void setAcc (String acc) {
		this.acc = acc;
	}


//	private HR owner;
//
//	@Transient
//	public HR getOwner() {
//		return owner;
//	}
//
//  public void setOwner(HR owner) {
//		this.owner = owner;
//	}


	@ManyToOne ( targetEntity = ReferenceSource.class /*, cascade=CascadeType.ALL */ )
	public ReferenceSource getSource () {
		return this.source;
	}

	public void setSource (ReferenceSource source) {
		this.source = source;
	}

	public String toString () {
		return String.format ( "{Xref: #%s, acc:%s}" , getId (), getAcc () );
	}

}
