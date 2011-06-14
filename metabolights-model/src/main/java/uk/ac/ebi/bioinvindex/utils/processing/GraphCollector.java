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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ebi.bioinvindex.model.processing.GraphElement;
import uk.ac.ebi.bioinvindex.model.processing.Node;
import uk.ac.ebi.bioinvindex.model.processing.Processing;

/**
 * A {@link ProcessingVisitAction} to be used with {@link ExperimentalPipelineVisitor} that allows to collect
 * the nodes in a pipeline as a collections, rather than a graph. 
 * 
 * @author brandizi
 * <b>date</b>: Feb 3, 2010
 *
 */
@SuppressWarnings("unchecked")
public class GraphCollector implements ProcessingVisitAction
{
	private Map<String, Node> nodes = new HashMap<String, Node> ();
	private Collection<Edge> edges = new HashSet<Edge> ();
	
	/**
	 * An alternative representation of a {@link Processing} object. Basically the difference is 
	 * that equals() and hashCode() are based on the objects identity and they don't change when 
	 * internal properties of the Processing change. This is useful for persistence/unloading operations
	 * where, you need to unlink the processings from its inputs/outputs, without screwing their links from 
	 * keep-track collections. 
	 * 
	 * @author brandizi
	 * <b>date</b>: Feb 3, 2010
	 *
	 */
	public static class Edge
	{
		private final List<Node> inputs, outputs;
		private final Processing processing;
		private final int hashCode; 
			
		public Edge ( Processing processing )
		{
			inputs = new ArrayList<Node> ( processing.getInputNodes () );
			outputs = new ArrayList<Node> ( processing.getOutputNodes () );

			this.processing = processing;
			this.hashCode = super.hashCode () * 31 + processing.hashCode ();
		}
		public List<Node> getInputs () {
			return inputs;
		}
		public List<Node> getOutputs () {
			return outputs;
		}
		public Processing getProcessing () {
			return processing;
		}
		
		@Override
		public boolean equals ( Object obj ) 
		{
			if ( obj == null ) return false; 
			if ( this == obj ) return true;
			
			if ( ! ( obj instanceof Edge ) ) return false;
			Edge that = (Edge) obj;
			if ( processing != that.processing ) return false;
			return true;
		}
		
		@Override
		public int hashCode () {
			return hashCode;
		}
		
	}
	
	/**
	 * Justs store the ge into internal nodes or edges.
	 * 
	 */
	public boolean visit ( GraphElement ge ) 
	{
		if ( ge instanceof Node ) { 
			Node node = (Node) ge;
			nodes.put ( node.getAcc (), node );
		}
		else if ( ge instanceof Processing ) edges.add ( new Edge ( (Processing) ge ) );
		return true;
	}

	/**
	 * All the collected Material/Data nodes that are available after {@link #visit(GraphElement)}. 
	 *  
	 */
	public Collection<Node> getNodes () {
		return nodes.values ();
	}

	/**
	 * All the collected {@link Edge} (i.e.: {@link Processing}) that are available after {@link #visit(GraphElement)}. 
	 */
	public Collection<Edge> getEdges () {
		return edges;
	}
	
}
