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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import uk.ac.ebi.bioinvindex.model.Study;

/**
 * Abstract class representing the series of events, unfolding through time, that occur to the {@link
 * uk.ac.ebi.bioinvindex.model.Material} and {@link uk.ac.ebi.bioinvindex.model.Data}.
 *
 * Contains references to input and output nodes.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 30, 2007
 */

@Entity
@Table(name = "PROCESSING")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "obj_type", discriminatorType = DiscriminatorType.STRING, length = 255)
public abstract class Processing<Input extends Node, Output extends Node> extends GraphElement {

	protected Collection<Input> inputNodes = new HashSet<Input>();

	protected Collection<Output> outputNodes = new HashSet<Output>();

	protected Collection<ProtocolApplication> protocolApplications = new HashSet<ProtocolApplication>();


	protected Processing() {
		super();
	}

	protected Processing(Study study) {
		super(study);
	}

	protected Processing(Study study, boolean original) {
		super(study, original);
	}

	@ManyToMany ( targetEntity = Node.class )
	@JoinTable ( 
		name = "processing_inputs", 
		joinColumns = @JoinColumn ( name = "proc_id" ),
		inverseJoinColumns = @JoinColumn ( name = "node_id" )
	)
	public Collection<Input> getInputNodes() {
		return Collections.unmodifiableCollection (this.inputNodes);
	}

	public void addInputNode(Input inputNode) {
		if (inputNode == null) {
			throw new IllegalArgumentException("inputNode cannot be null");
		}
		this.inputNodes.add(inputNode);
		if (!inputNode.getUpstreamProcessings().contains(this)) {
			inputNode.addUpstreamProcessing(this);
		}
	}

	public void removeInputNode(Input inputNode) {
		if (inputNode == null) {
			return;
		}
		this.inputNodes.remove(inputNode);
		if (inputNode.getUpstreamProcessings().contains(this)) {
			inputNode.removeUpstreamProcessing(this);
		}
	}

	@ManyToMany ( targetEntity = Node.class )
	@JoinTable ( 
		name = "processing_outputs", 
		joinColumns = @JoinColumn ( name = "proc_id" ),
		inverseJoinColumns = @JoinColumn ( name = "node_id" )
	)
	public Collection<Output> getOutputNodes() {
		return Collections.unmodifiableCollection ( this.outputNodes );
	}

	public void addOutputNode(Output outputNode) {
		if (outputNode == null) {
			throw new IllegalArgumentException("outputNode cannot be null");
		}
		this.outputNodes.add(outputNode);
		if (!outputNode.getDownstreamProcessings().contains(this)) {
			outputNode.addDownstreamProcessing(this);
		}
	}

	public void removeOutputNode(Output outputNode) {
		if (outputNode == null) {
			return;
		}
		this.outputNodes.remove(outputNode);
		if (outputNode.getDownstreamProcessings().contains(this)) {
			outputNode.removeDownstreamProcessing(this);
		}
	}


	private void setInputNodes(Collection<Input> inputNodes) {
		this.inputNodes = inputNodes;
	}

	private void setOutputNodes(Collection<Output> outputNodes) {
		this.outputNodes = outputNodes;
	}


	@OneToMany ( targetEntity = ProtocolApplication.class )
	@JoinColumn ( name = "proto_app_id" )
	public Collection<ProtocolApplication> getProtocolApplications() {
		return Collections.unmodifiableCollection ( protocolApplications);
	}

	/**
	 * If in the ISA-TAB "Protocol REF" column is present between the transition from node to the next, a {@link
	 * uk.ac.ebi.bioinvindex.model.processing.ProtocolApplication} is instantiated and is added to its instance of
	 * {@link Processing}.
	 *
	 * @param protocolApplications
	 */
	protected void setProtocolApplications(Collection<ProtocolApplication> protocolApplications) {
		this.protocolApplications = protocolApplications;
	}

	public void addProtocolApplication(ProtocolApplication protocolApplication) {
		if (protocolApplication == null) {
			throw new IllegalArgumentException("protocolApplication cannot be null");
		}
		this.protocolApplications.add(protocolApplication);
	}

	/**
	 * See {@link ProtocolApplication#UNIQUE_PAPP_COMMENT} for details. This method returns a non-null value only when the 
	 * processing has at least one application and all the applications have the same value for the comment at issue. 
	 * Null (ie: unspecified, apply the default) is returned in all other cases.
	 *  
	 */
	@Transient
	public Boolean isUniqueApplication ()
	{
		boolean prevUniqueVal = false, isFirst = true;
		for ( ProtocolApplication papp: this.protocolApplications ) 
		{
			String uniqueVal = papp.getSingleAnnotationValue ( ProtocolApplication.UNIQUE_PAPP_COMMENT );
			if ( uniqueVal == null ) return null;
			
			boolean isUnique = ArrayUtils.contains ( 
				ProtocolApplication.UNIQUE_PAPP_COMMENT_TRUE_VALUES, uniqueVal.toLowerCase () 
			);
			
			if ( !isFirst && prevUniqueVal != isUnique ) return null;
			
			prevUniqueVal = isUnique;
			isFirst = false;
		}
		return prevUniqueVal;
	}
	
	
	public String toString() {
		return "Processing { " +
			"id=" + getId() +
			", acc=" + getAcc () +
			", inputNodes=" + inputNodes +
			", outputNodes=" + outputNodes +
			", protocolApplications=" + protocolApplications +
			" }";
	}
}
