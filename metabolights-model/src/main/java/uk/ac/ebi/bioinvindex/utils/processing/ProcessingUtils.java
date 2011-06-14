package uk.ac.ebi.bioinvindex.utils.processing;


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

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Data;
import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.Protocol;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.processing.DataNode;
import uk.ac.ebi.bioinvindex.model.processing.GraphElement;
import uk.ac.ebi.bioinvindex.model.processing.MaterialNode;
import uk.ac.ebi.bioinvindex.model.processing.Node;
import uk.ac.ebi.bioinvindex.model.processing.Processing;
import uk.ac.ebi.bioinvindex.model.processing.ProtocolApplication;
import uk.ac.ebi.bioinvindex.model.term.MaterialRole;
import uk.ac.ebi.bioinvindex.model.term.ProtocolType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Utilities for the experimental pipeline/processing steps/processing nodes and alike
 * 
 * date: May 14, 2008
 * @author brandizi
 *
 */
public class ProcessingUtils
{
	private ProcessingUtils () {}

	/** 
	 * An helper that finds all the AssayResults related to the assay parameter. It is based on the experimental pipeline
	 * the assay's material belong in. 
	 */
	public static Collection<AssayResult> findAssayResultsFromAssay ( Assay assay ) 
	{
		if ( assay == null )
			throw new RuntimeException ( "findAssayResultsFromAssay(): null assay passed to the method" );
		
		Material material = assay.getMaterial (); 
		if ( material == null )
			throw new RuntimeException ( "findAssayResultsFromAssay( " + assay + "): no material associated to the assay" );
		
		Study study = assay.getStudy ();
		if ( study == null ) 
			throw new RuntimeException ( "findAssayResultsFromAssay ( " + assay + " ): no study associated to the assay" );
		final Collection<AssayResult> ars = study.getAssayResults ();
		
		
		final Collection<AssayResult> result = new HashSet<AssayResult> ();
		ProcessingVisitAction visitor = new ProcessingVisitAction () 
		{
			public boolean visit ( GraphElement graphElement ) 
			{
				if ( ! ( graphElement instanceof DataNode ) ) return true;
				Data data = ( (DataNode) graphElement ).getData ();
				for ( AssayResult ar: ars )
					if ( ar.getData () == data )
						result.add ( ar );
				return true;
			}
		}; 
	
		new ExperimentalPipelineVisitor ( visitor ).visitForward ( material.getMaterialNode () );
	  return result;
	}

	/**
	 * Finds the root nodes in a graph that this element is connected to. 
	 * 
	 * TODO: write at least one test about this!
	 * 
	 */
	public static Set<Node> findStartingNodes ( GraphElement ge )
	{
		if ( ge == null ) return Collections.emptySet ();
		return ge instanceof Node 
		  ? findStartingNodes ( (Node<?, ?>) ge )
		  : findStartingNodes ( (Processing<?, ?>) ge );
	}
	
	/**
	 * Finds the root nodes in a graph that this element is connected to. 
	 * 
	 */
	public static Set<Node> findStartingNodes ( Node node )
	{
		if ( node == null ) return Collections.emptySet ();

		Set<Node> result = new HashSet<Node> ();
		Collection<Processing> prevProcs = node.getDownstreamProcessings ();
		if ( prevProcs.isEmpty () ) {
			result.add ( node );
			return result;
		}
		
		for ( Processing proc: prevProcs )
			result.addAll ( findStartingNodes ( proc ) );

		return result;
	}

	/**
	 * Finds the root nodes in a graph that this element is connected to. 
	 * 
	 */
	public static Set<Node> findStartingNodes ( Processing proc )
	{
		if ( proc == null ) return Collections.emptySet ();

		Collection<Node> prevNodes = proc.getInputNodes ();
		if ( prevNodes.isEmpty () )
			return Collections.emptySet ();

		Set<Node> result = new HashSet<Node> ();
		for ( Node node: prevNodes )
			result.addAll ( findStartingNodes ( node ) );

		return result;
	}
	
	/**
	 * Finds the root nodes in a graph that this element is connected to. 
	 * 
	 * TODO: write at least one test about this!
	 * 
	 */
	public static Set<Node> findEndNodes ( GraphElement ge )
	{
		if ( ge == null ) return Collections.emptySet ();
		return ge instanceof Node 
		  ? findEndNodes ( (Node<?, ?>) ge )
		  : findEndNodes ( (Processing<?, ?>) ge );
	}
	
	/**
	 * Finds the root nodes in a graph that this element is connected to. 
	 * 
	 */
	public static Set<Node> findEndNodes ( Node node )
	{
		if ( node == null ) return Collections.emptySet ();
		Set<Node> result = new HashSet<Node> ();
		
		Collection<Processing> rprocs = node.getUpstreamProcessings ();
		if ( rprocs.isEmpty () ) {
			result.add ( node );
			return result;
		}
		
		for ( Processing proc: rprocs )
			result.addAll ( findEndNodes ( proc ) );

		return result;
	}

	/**
	 * Finds the root nodes in a graph that this element is connected to. 
	 * 
	 */
	public static Set<Node> findEndNodes ( Processing proc )
	{
		if ( proc == null ) return Collections.emptySet ();

		Collection<Node> rnodes = proc.getOutputNodes ();
		if ( rnodes.isEmpty () ) {
			return Collections.emptySet ();
		}
		Set<Node> result = new HashSet<Node> ();

		for ( Node node: rnodes )
			result.addAll ( findEndNodes ( node ) );

		return result;
	}

	
	/**
	 * Finds the biomaterials that contains a given string in the type. When some material is found, does not further the
	 * search downstream if shallowSearch is true. Does not work if you haven't assigned the type to 
	 * the materials. When shallowSearch is true takes only the steps from which the end node
	 * directly derives. Results are returned in breadth-first fashion. 
	 * 
	 */
	public static Collection<MaterialNode> findBackwardMaterialNodes 
		( final Node<?, ?> endNode, final String typeStr, final boolean shallowSearch ) 
	{
  	Collection<MaterialNode> result = new HashSet<MaterialNode> ();
		
  	Collection<Processing<?,?>> downStreamProcessings = 
  		(Collection<Processing<?,?>>) endNode.getDownstreamProcessings ();
  	
		for ( Processing<?, ?> downProc: downStreamProcessings ) 
		{
			for ( Node<?, ?> inNode: downProc.getInputNodes () ) 
			{ 
				if ( !( inNode instanceof MaterialNode ) ) continue;
				MaterialNode mnode = (MaterialNode) inNode;
		  	// Add if the current node is of the requested type
				Material inMaterial = mnode.getMaterial ();
				MaterialRole inType = inMaterial.getType ();
				if ( inType != null && StringUtils.containsIgnoreCase ( inType.getAcc (), typeStr ) )
					result.add ( mnode );
			}
		}
		
		if ( shallowSearch ) return result; 

		// It's better to return results in Breath-first fashion
		for ( Processing<?, ?> downProc: downStreamProcessings ) 
			for ( Node<?, ?> inNode: downProc.getInputNodes () ) 
				result.addAll ( findBackwardMaterialNodes ( inNode, typeStr, shallowSearch ) );

		return result;
	}


	/**
	 * Finds all the processing steps applying a given protocol
	 * 
	 * @param pname protocol name must contain this (AND search, ignored if null)
	 * @param ptype protocol type name must contain this
	 * @param shallowSearch when true takes only the steps from which the end node
	 * directly derives. 
	 * @return
	 */
	public static Collection<Processing<?,?>> findBackwardProcessings 
		( final Node<?, ?> endNode, final String ptype, final String pname, final boolean shallowSearch )
	{
  	Collection<Processing<?,?>> result = new HashSet<Processing<?,?>> ();
		
  	Collection<Processing<?,?>> downStreamProcessings = 
  		(Collection<Processing<?,?>>) endNode.getDownstreamProcessings ();
  	
		for ( Processing<?,?> downProc: downStreamProcessings ) 
		{
			for ( ProtocolApplication papp: downProc.getProtocolApplications () ) 
			{
				Protocol proto = papp.getProtocol ();
				String piname = null, pitypeStr = null; 

				if ( proto != null ) {
					piname = proto.getName ();
					ProtocolType pitype = proto.getType ();
					if ( pitype != null ) pitypeStr = pitype.getName ();
				}
				
				if ( ( pname == null || StringUtils.containsIgnoreCase ( piname, pname ) ) 
					&& ( ptype == null || StringUtils.containsIgnoreCase ( pitypeStr, ptype ) ) ) 
				{
					result.add ( downProc );
					break;
				}
			}
		}
		
		if ( shallowSearch ) return result; 

		// We better return results in Breath-first fashion
		for ( Processing<?,?> downProc: downStreamProcessings ) 
			for ( Node<?, ?> inNode: downProc.getInputNodes () ) 
				result.addAll ( findBackwardProcessings ( inNode, ptype, pname, shallowSearch ) );

		return result;
	}

	/**
	 * Finds all protocol applications needed to produce a certain end node. Filters by protocol type or protocol name
	 * (partial matching supported). When shallowSearch is true takes only the steps from which the end node
	 * directly derives. 
	 * 
	 */
	public static Collection<ProtocolApplication> findBackwardProtocolApplications 
		( final Node<?, ?> endNode, final String ptype, final String pname, final boolean shallowSearch )
	{
		Collection<ProtocolApplication> result = new LinkedList<ProtocolApplication> ();
		
		for ( Processing<?, ?> proc: findBackwardProcessings ( endNode, ptype, pname, shallowSearch ) )
			for ( ProtocolApplication papp: proc.getProtocolApplications () ) 
			{
				Protocol proto = papp.getProtocol ();
				String piname = null, pitypeStr = null; 

				if ( proto != null ) {
					piname = proto.getName ();
					ProtocolType pitype = proto.getType ();
					if ( pitype != null ) pitypeStr = pitype.getName ();
				}
				
				if ( ( pname == null || StringUtils.containsIgnoreCase ( piname, pname ) ) 
					&&  ( ptype == null || StringUtils.containsIgnoreCase ( pitypeStr, ptype ) ) ) 
					result.add ( papp );
		}
		return result;
	
	}

	/**
	 * Find all the protocols applied to produce a certain end node. Filters by protocol type or protocol name
	 * (partial matching supported). When shallowSearch is true takes only the steps from which the end node
	 * directly derives. 
	 *  
	 */
	public static Collection<Protocol> findBackwardProtocols 
		( final Node<?, ?> endNode, final String ptype, final String pname, final boolean shallowSearch )
	{
		Collection<Protocol> result = new HashSet<Protocol> ();
		for ( ProtocolApplication papp: findBackwardProtocolApplications ( endNode, ptype, pname, shallowSearch ) )
			result.add ( papp.getProtocol () );
		return result;
	}
	
	/**
	 * Tells all those samples nodes the endNode derives from that are the last in a ISATAB sample file.
	 */
	public static Collection<MaterialNode> findSampleFileLastNodes ( Node endNode, boolean includeSource )
	{
		MaterialNode mnode = null; 
		Collection<MaterialNode> result = new HashSet<MaterialNode> ();
		
		if ( endNode instanceof MaterialNode ) {
			mnode = (MaterialNode) endNode;
			if ( mnode.getMaterial ().getSingleAnnotationValue ( "sampleFileId" ) != null ) {
				result.add ( mnode );
				return result;
			}
		}
		
		Collection<Processing> procs = endNode.getDownstreamProcessings ();
		if ( procs.isEmpty () )
		{
			if ( mnode != null && includeSource )
				// If we want the sources included anyway, let's go with it
				result.add ( (MaterialNode) endNode );
			return result;
		}
		for ( Processing input: procs )
			result.addAll ( findSampleFileLastNodes ( input, includeSource ) );
		
		return result;
	}

	public static Collection<MaterialNode> findSampleFileLastNodes ( Processing proc, boolean includeSource )
	{
		Collection<MaterialNode> result = new HashSet<MaterialNode> ();
		for ( Node inputNode: (Collection<Node>) proc.getInputNodes () )
			result.addAll ( findSampleFileLastNodes ( inputNode, includeSource ) );
		return result;
	}

	
//	/**
//	 * Tells all those samples nodes the endNode derives from that are the last in a ISATAB sample file.
//	 * It start searching from a left node, the startNode.
//	 * 
//	 */
//	public static Collection<MaterialNode> findSampleFileLastNodesFromLeft ( Node startNode )
//	{
//		MaterialNode mnode = null; 
//		Collection<MaterialNode> result = new HashSet<MaterialNode> ();
//		
//		if ( startNode instanceof MaterialNode )
//		{
//			mnode = (MaterialNode) startNode;
//			if ( mnode.getMaterial ().getSingleAnnotationValue ( "sampleFileId" ) != null ) 
//			{
//				boolean isResult = true;
//				for ( Processing rproc: (Collection<Processing>) mnode.getUpstreamProcessings () )
//				{
//					for ( Node rnode: (Collection<Node>) rproc.getOutputNodes () ) {
//						if ( rnode instanceof MaterialNode 
//								 && ((MaterialNode) rnode).getSingleAnnotationValue ( "sampleFileId" ) != null ) 
//						{
//							// More materials follow
//							isResult = false;
//							result.addAll ( findSampleFileLastNodesFromLeft ( rnode ) );
//						}
//					}
//				}
//				if ( isResult )
//					result.add ( mnode );
//				return result;
//			}
//		}
//		
//		return result;
//	}

}
