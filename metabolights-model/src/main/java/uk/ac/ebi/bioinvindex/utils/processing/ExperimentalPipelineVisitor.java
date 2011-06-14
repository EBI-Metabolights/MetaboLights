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

import java.util.HashSet;
import java.util.Set;

import uk.ac.ebi.bioinvindex.model.processing.GraphElement;
import uk.ac.ebi.bioinvindex.model.processing.Node;
import uk.ac.ebi.bioinvindex.model.processing.Processing;

/**
 * Allows to visit an experimental graph starting from a node and toward downstream or upstream direction.
 * Visits are arranged with the command pattern, so that an action (command) is invoked at each visit.
 * The class keeps track of the visited nodes, no node is visited twice. You have a method to reset the 
 * visited node memory (without need to recreate another instance), so that you can decide when the graph
 * or parts of it have to be revisited.
 * 
 * TODO: synchronization.
 *   
 * @author brandizi
 * <b>date</b>: Apr 28, 2009
 *
 */
public class ExperimentalPipelineVisitor
{
	private Set<Node<?, ?>> visitedNodes = new HashSet<Node<?, ?>> ();
	private Set<Processing<?, ?>> visitedProcs = new HashSet<Processing<?, ?>> ();
	private ProcessingVisitAction action;
	
	public ExperimentalPipelineVisitor ( ProcessingVisitAction action ) {
		this.action = action;
	}
	
	public ExperimentalPipelineVisitor () {
	}
	
	/** 
	  * walks through the downstream and upstream graphs which depart and arrive at the parameter, execute the action parameter.
	  * Returns false and interrupt the visit if at some point the visit() method in the action returns false.
	  * 
	  * <p><b>PLEASE NOTE</b>: This is not the same as calling {@link #visitBackward(Node)} + {@link #visitForward(Node)}, 
	  * because here all the graph that is reachable from the initial node is traversed, i.e.: all the inputs and outputs
	  * of a processing are visited, while the outputs(inputs) only are visited by the forward(backward) visit.
	  *  
	  */
	public boolean visit ( Node<?, ?> node ) 
	{
		if ( visitedNodes.contains ( node ) ) return true;
		visitedNodes.add ( node );
		
		if ( action == null ) throw new IllegalStateException ( 
			"Internal error: cannot visit an experimental graph without a visitor" 
		);
		if ( !action.visit ( node ) ) return false;
		
		for ( Processing<?, ?> downProc: node.getDownstreamProcessings () ) 
			if ( !visit ( downProc ) ) return false;
		for ( Processing<?, ?> upProc: node.getUpstreamProcessings () ) 
			if ( !visit ( upProc ) ) return false;
		return true;
	}

	/**
	 * 
	 * TODO: backward/forward visit methods for the processing. 
	 * 
	 * @see #visit(Node).
	 */
	public boolean visit ( Processing<?, ?> proc )
	{
		if ( visitedProcs.contains ( proc ) ) return true;
		visitedProcs.add ( proc );

		if ( action == null ) throw new IllegalStateException ( 
			"Internal error: cannot visit an experimental graph without a visitor" 
		);
		if ( !action.visit ( proc ) ) return false;
		
		for ( Node<?,?> node: proc.getInputNodes () )
			if ( !visit ( node ) ) return false;
		for ( Node<?,?> node: proc.getOutputNodes () )
			if ( !visit ( node ) ) return false;
		return true;
	}
	
	

	/** 
	 * Walks through the upstream (right) graphs which depart from and arrive to the parameter, executes the action 
	 * parameter for every node visited (either a {@link Processing} or a {@link Node}.
	 * 
	 * Each node is visited only once, unless {@link #reset()} is called. 
	 * 
	 * Returns false and interrupt the visit if at some point the visit() method in the action returns false.
	 * 
	 * @param skipFirst when true, the current node is not passed to the visiting action, we only go forward with 
	 * its right processings. This is useful to call a backward visit after a forward visit (which is different than
	 * {@link #visit(Node)}).
	 * 
	 * @see #visit(Node)
	 * 
	 *  
	 */
	public boolean visitForward ( Node<?, ?> node, boolean skipFirst ) 
	{
		if ( !skipFirst )
		{
			if ( visitedNodes.contains ( node ) ) return true;
			visitedNodes.add ( node );
			
			if ( action == null ) throw new IllegalStateException ( 
				"Internal error: cannot visit an experimental graph without a visitor" 
			);
			
			if ( !action.visit ( node ) ) return false;
		}

		for ( Processing<?, ?> proc: node.getUpstreamProcessings () ) 
			if ( !visitForward ( proc ) ) return false;
		return true;
	}

	/**
	 * A wrapper with skipFirst = false
	 * 
	 */
	public boolean visitForward ( Node<?, ?> node ) {
		return visitForward ( node, false );
	} 

	/**
	 * Like {@link #visitForward(Node)}, but for processings.
	 */
	public boolean visitForward ( Processing<?, ?> proc ) 
	{
		if ( visitedProcs.contains ( proc ) ) return true;
		visitedProcs.add ( proc );
		
		if ( action == null ) throw new IllegalStateException ( 
			"Internal error: cannot visit an experimental graph without a visitor" 
		);
		if ( !action.visit ( proc ) ) return false;

		for ( Node<?, ?> out: proc.getOutputNodes () ) 
			if ( !visitForward ( out, false ) ) return false;
		return true;
	}

	

	/** 
	 * Walks through the downstream (left) graphs which depart from and arrive to the parameter, executes the action
	 * parameter for every node visited (either a {@link Processing} or a {@link Node}.
	 * 
	 * Each node is visited only once, unless {@link #reset()} is called. 
	 * 
	 * Returns false and interrupt the visit if at some point the visit() method in the action returns false.
	 * 
	 * @param skipFirst when true, the current node is not passed to the visiting action, we only go backward with 
	 * its right processings. This is useful to call a forward visit after a backward visit (which is different than
	 * {@link #visit(Node)}).
	 * 
	 * @see #visit(Node)
	 *
	 */
	public boolean visitBackward ( Node<?, ?> node, boolean skipFirst ) 
	{
		if ( !skipFirst )
		{
			if ( visitedNodes.contains ( node ) ) return true;
			visitedNodes.add ( node );
			
			if ( action == null ) throw new IllegalStateException ( 
				"Internal error: cannot visit an experimental graph without a visitor" 
			);
			if ( !action.visit ( node ) ) return false;
		}
		
		for ( Processing<?, ?> proc: node.getDownstreamProcessings () ) 
			if ( !visitBackward ( proc ) ) return false;
		return true;
	}

	/**
	 * A wrapper with skipFirst = false
	 * 
	 */
	public boolean visitBackward ( Node<?, ?> node ) {
		return visitBackward ( node, false );
	} 

	
	public boolean visitBackward ( Processing<?, ?> proc ) 
	{
		if ( visitedProcs.contains ( proc ) ) return true;
		visitedProcs.add ( proc );
		
		if ( action == null ) throw new IllegalStateException ( 
			"Internal error: cannot visit an experimental graph without a visitor" 
		);
		if ( !action.visit ( proc ) ) return false;

		for ( Node<?, ?> in: proc.getInputNodes () ) 
			if ( !visitBackward ( in, false ) ) return false;
		return true;
	}

	
	
	/**
	 * Tells if a certain node has been visited by this instance of the class. Will return always false soon after the
	 * invocation of {@link #reset()}. 
	 * 
	 */
	public boolean isVisited ( Node<?, ?> node ) {
		return this.visitedNodes.contains ( node );
	}

	/**
	 * Tells if a certain processing has been visited by this instance of the class. Will return always false soon after the
	 * invocation of {@link #reset()}. 
	 * 
	 */
	public boolean isVisited ( Processing<?, ?> proc ) {
		return this.visitedProcs.contains ( proc );
	}

	/**
	 * @see {@link #isVisited(Node)}, {@link #isVisited(Processing)}
	 */
	public boolean isVisited ( GraphElement ge ) 
	{
		if ( ge instanceof Node ) return isVisited ( (Node<?, ?>) ge );
		else if ( ge instanceof Processing ) return isVisited ( (Processing<?, ?>) ge );
		else throw new IllegalArgumentException ( "Parameter type " + ge.getClass ().getSimpleName () + " not supported" );
	}

	/**
	 * @return The action invoked at each visit.
	 * 
	 */
	public ProcessingVisitAction getAction () {
		return action;
	}

	/**
	 * The action invoked at each visit.
	 * 
	 */
	public void setAction ( ProcessingVisitAction action ) {
		this.action = action;
	}
	
	/**
	 * Set a new action and call {@link #reset()}, i.e.: mark the pipeline as all unvisited. 
	 * 
	 */
	public void setActionAndReset ( ProcessingVisitAction action ) {
		this.action = action;
		this.reset ();
	}
	
	
	/**
	 * Unmarks all the nodes/processings that were marked as visited during visit() calls. This allows to restart a 
	 * graph walk without need to create a new instance of this class.
	 * 
	 */
	public void reset () {
		visitedNodes.clear ();
		visitedProcs.clear ();
	}

}
