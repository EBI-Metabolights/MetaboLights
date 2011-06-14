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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import uk.ac.ebi.bioinvindex.model.Data;
import uk.ac.ebi.bioinvindex.model.Study;

/**
 * Node with contains a {@link uk.ac.ebi.bioinvindex.model.Data} object.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 30, 2007
 */
@Entity
@DiscriminatorValue("DataNode")
public class DataNode extends Node<DataProcessing, Processing> {

	private Data data;
	private Set<String> assayFiles = null;


	protected DataNode() {
		super();
	}

	public DataNode(Study study) {
		super(study);
	}

	public DataNode(Study study, boolean original) {
		super(study, original);
	}

	@OneToOne ( targetEntity = uk.ac.ebi.bioinvindex.model.Data.class, mappedBy = "processingNode" )
	public Data getData() {
		return this.data;
	}

	/**
	 * Setup the data associated to this node and consequently changes the node associated to the parameter.
	 *
	 * @param data
	 */
	public void setData(Data data) 
	{
		Data oldData = this.data;
		this.data = data;
		
		if (oldData != null && oldData != data) {
			oldData.setProcessingNode ( null );
		}

		if (data == null) return;

		DataNode node = data.getProcessingNode();
		if (node != this) data.setProcessingNode ( this );
	}

	@Transient
	@Override
	public String getSampleFileId () {
		return this.data == null ? null : this.data.getSingleAnnotationValue ( "sampleFileId" );
	}

	@Transient
	@Override
	public Set<String> getAssayFileIds () 
	{
		if ( assayFiles != null ) return assayFiles;
		assayFiles = new HashSet<String> ();
		if ( this.data == null ) {
			assayFiles = Collections.emptySet ();
			return assayFiles;
		}

		String afile = this.data.getSingleAnnotationValue ( "assayFileId" );
		if ( afile == null ) {
			assayFiles = Collections.emptySet ();
			return assayFiles;
		}
		assayFiles = Collections.singleton ( afile );
		return assayFiles;
	}
	
	
	@Override
	public String toString () 
	{
		return 
		"DataNode { id: " + getId() + ", acc: " + getAcc () 
		+ ", data: " + getData () + " }";
	}

}
