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

import uk.ac.ebi.bioinvindex.model.Study;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class representing a generic Input and Output of {@link Processing}.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 27, 2007
 */
@Entity
@Table(name = "NODE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "obj_type", discriminatorType = DiscriminatorType.STRING, length = 255)
public abstract class Node
		<UpstreamProcessing extends Processing, DownstreamProcessing extends Processing>
		extends GraphElement {

	protected Collection<UpstreamProcessing> upstreamProcessings = new HashSet<UpstreamProcessing>();

	protected Collection<DownstreamProcessing> downstreamProcessings = new HashSet<DownstreamProcessing>();



	protected Node() {
		super();
	}

	protected Node(Study study) {
		super(study);
	}

	protected Node(Study study, boolean original) {
		super(study, original);
	}

	/**
	 * Returns a Collection of {@link uk.ac.ebi.bioinvindex.model.processing.Processing} objects, which use this node as an input node.
	 * @return
	 */
	@ManyToMany ( targetEntity = Processing.class, mappedBy = "inputNodes" )
	public Collection<UpstreamProcessing> getUpstreamProcessings() {
		return Collections.unmodifiableCollection ( upstreamProcessings );
	}

	/**
	 * Returns a Collection of {@link uk.ac.ebi.bioinvindex.model.processing.Processing} objects, which use this node as an output node.
	 * @return
	 */
	@ManyToMany ( targetEntity = Processing.class, mappedBy = "outputNodes" )
	public Collection<DownstreamProcessing> getDownstreamProcessings() {
		return Collections.unmodifiableCollection ( downstreamProcessings );
	}

	public void addUpstreamProcessing(UpstreamProcessing upstreamProcessing) 
	{
		if (upstreamProcessing == null)
			throw new IllegalArgumentException("upstreamProcessing cannot be null");
		this.upstreamProcessings.add(upstreamProcessing);
		if (!upstreamProcessing.getInputNodes().contains(this)) {
			upstreamProcessing.addInputNode(this);
		}
	}
	
	public void removeUpstreamProcessing ( UpstreamProcessing upstreamProcessing ) {
		if ( upstreamProcessing == null)
			return;
		this.upstreamProcessings.remove (upstreamProcessing);
		if (upstreamProcessing.getInputNodes().contains(this)) {
			upstreamProcessing.removeInputNode ( this );
		}
	}

	public void addDownstreamProcessing(DownstreamProcessing downstreamProcessing) 
	{
		if (downstreamProcessing == null)
			throw new IllegalArgumentException("downstreamProcessing cannot be null");
		this.downstreamProcessings.add(downstreamProcessing);
		if (!downstreamProcessing.getOutputNodes().contains(this)) {
			downstreamProcessing.addOutputNode(this);
		}
	}

	public void removeDownstreamProcessing ( DownstreamProcessing downstreamProcessing ) {
		if ( downstreamProcessing == null)
			return;

		this.downstreamProcessings.remove (downstreamProcessing);
		if ( downstreamProcessing.getOutputNodes ().contains ( this ) ) {
			downstreamProcessing.removeOutputNode ( this );
		}
	}

	protected void setUpstreamProcessings ( Collection<UpstreamProcessing> upstreamProcessings ) {
		this.upstreamProcessings = upstreamProcessings;
	}

	protected void setDownstreamProcessings ( Collection<DownstreamProcessing> downstreamProcessings ) {
		this.downstreamProcessings = downstreamProcessings;
	}

	/**
	 * The ISATAB sample file the object wrapping this node comes from. This is attached as an annotation to 
	 * a material or data object and hence the specific implementations fetch this value from there.
	 * 
	 */
	@Transient
	public abstract String getSampleFileId ();
	
	/**
	 * The ISATAB assay files the object wrapping this node comes from. This is attached as an annotation to 
	 * a material or data object and hence the specific implementations fetch this value from there.
	 * It is a set, cause a sample node could belong to multiple assay files.
	 * 
	 */
	@Transient
	public abstract Set<String> getAssayFileIds ();
}
