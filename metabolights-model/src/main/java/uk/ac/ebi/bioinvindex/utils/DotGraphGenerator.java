package uk.ac.ebi.bioinvindex.utils;

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

import org.apache.commons.io.FileUtils;

import uk.ac.ebi.bioinvindex.model.Data;
import uk.ac.ebi.bioinvindex.model.Identifiable;
import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.processing.DataAcquisition;
import uk.ac.ebi.bioinvindex.model.processing.DataNode;
import uk.ac.ebi.bioinvindex.model.processing.DataProcessing;
import uk.ac.ebi.bioinvindex.model.processing.MaterialNode;
import uk.ac.ebi.bioinvindex.model.processing.MaterialProcessing;
import uk.ac.ebi.bioinvindex.model.processing.Node;
import uk.ac.ebi.bioinvindex.model.processing.Processing;
import uk.ac.ebi.bioinvindex.model.processing.ProtocolApplication;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * An helper used to generate graphs for the experimental pipeline. String in DOT format are produced from a collection
 * of {@link Processing} objects. Results can be used with {@link http://www.graphviz.org/ GraphViz}.
 * While this version is developer-oriented and  intended for tests, an end-user visualization tool would be very similar.
 *
 * date: Mar 18, 2008
 * @author brandizi
 *
 */
public class DotGraphGenerator
{
	private Collection<Identifiable> objects;
	private long tempIdCounter = 1;

	/**
	 * I will graph all the instances of {@link #objects} which are {@link Processing} (and nodes, materials, etc.).
	 * WARNING: I need to assign a temporary IDs to the objecs, via {@link Identifiable#setId(Long)}, BE AWARE that
	 * the objects are changed if their ID is null. Otherwise ENSURE the IDs returned by {@link Identifiable#getId()}
	 * are distinct.
	 *
	 */
	public DotGraphGenerator ( Collection<Identifiable> objects )
	{
		this.objects = objects;
	}


	/**
	 * Changes the object ID if it is null with a temporary value, which is unique within a single execution of the
	 * Graph generator.
	 *
	 */
	private void setTempId ( Identifiable object ) {
		if ( object.getId () == null )
			object.setId ( new Long ( tempIdCounter++ ) );
	}

	/**
	 * Creates the DOT string corresponding to the graph of {@link Processing} objects in the collection I manage
	 *
	 */
	public StringBuilder createGraph ()
	{

		StringBuilder dotCode = new StringBuilder ();
		dotCode.append ( "strict digraph ExperimentalPipeline\n" );
		dotCode.append ( "{\n" );
		dotCode.append ( "  rankdir = LR; compound=true;\n\n" );

		for ( Identifiable object: objects )
		{
			if ( object == null ) continue;

			setTempId ( object );
			if ( object instanceof MaterialProcessing )
				dotCode.append ( graphProcessing ( (MaterialProcessing) object ) );
			else if ( object instanceof DataAcquisition )
				dotCode.append ( graphProcessing ( (DataAcquisition) object ) );
			else if ( object instanceof DataProcessing )
				dotCode.append ( graphProcessing ( (DataProcessing) object ) );
		}

		dotCode.append ( "}\n" );
		return dotCode;
	}

	/**
	 * Creates the DOT string corresponding to the graph of {@link Processing} objects in the collection I manage
	 * and save all to a file.
	 *
	 */
	public StringBuilder createGraph ( String fileName ) throws IOException
	{
		StringBuilder dotCode = createGraph ();
		FileUtils.writeStringToFile ( new File ( fileName ), dotCode.toString () );
		return dotCode;
	}


	/** Works on a single processing step */
	private StringBuilder graphProcessing ( Processing<?, ?> processing )
	{
		StringBuilder dotCode = new StringBuilder();

		// The node for the processing step
		//
		String procIdPrefix = "", procNodeColor = "";
		if ( processing instanceof MaterialProcessing ) {
			procIdPrefix = "MN_"; procNodeColor = "lightseagreen";
		}
		else if ( processing instanceof DataAcquisition ) {
			procIdPrefix = "DAN_"; procNodeColor = "lightslateblue";
		}
		else if ( processing instanceof DataProcessing ) {
			procIdPrefix = "DPN_"; procNodeColor = "lightblue";
		}
		String procId = procIdPrefix + processing.getId ();
		String procLabel = processing.getAcc ();
		for ( ProtocolApplication protoApp: processing.getProtocolApplications () ) {
			procLabel += "\\n" + protoApp.getProtocol ().getName ();
		}
		dotCode.append ( "  " + procId + "[label = \"" + procLabel + "\"," +
			" shape = triangle, orientation = -90, style = filled, color = " + procNodeColor + "];\n" 
		);		

		// for each input: input -> processing
		for ( Node<?, ?> node: processing.getInputNodes () ) {
			String nodeId = getNodeId ( node );
			dotCode.append ( "    " + nodeId + " -> " + procId + ";\n" );
			dotCode.append ( graphNode ( node ) );
		}

		// for each out: processing -> out
		for (  Node<?, ?> node: processing.getOutputNodes () ) {
			String nodeId = getNodeId ( node );
			dotCode.append ( "    " + procId + " -> " + nodeId + ";\n" );
			dotCode.append ( graphNode ( node ) );
		}

		return dotCode;
	}
	
	/** IN/OUT Node prefix to be used with IDs and labels */
	private String getNodePrefx ( Node<?, ?> node ) {
		if ( node instanceof MaterialNode ) return "MN_";
		if ( node instanceof DataNode) return "DN_";
		return "";
	}
	
	/** IN/OUT Node ID */
	private String getNodeId ( Node<?, ?> node ) {
		setTempId ( node );
		return getNodePrefx ( node ) + node.getId ();
	}

	/** IN/OUT Node label */
	private String getNodeLabel ( Node<?, ?> node ) {
		return getNodePrefx ( node ) + node.getAcc ();
	}

	/** Forwards to the specific node */
	private StringBuilder graphNode ( Node<?, ?> node )
	{
		if ( node instanceof MaterialNode ) return graphMaterialNode ( (MaterialNode) node );
		if ( node instanceof DataNode ) return graphDataNode ( (DataNode) node );
		throw new RuntimeException ( "Don't know what to do with node of type " + node.getClass ().getName () );
	}

	

	/** Works on a single IN/OUT node */
	private StringBuilder graphMaterialNode ( MaterialNode node )
	{
		StringBuilder dotCode = new StringBuilder ();

		String nodeId = getNodeId ( node );
		String nodeLabel = getNodeLabel ( node );
		Material material = node.getMaterial ();
		if ( material == null )
			dotCode.append ( "    " + nodeId + "[label = \""+ nodeLabel + "\", shape = ellipse, style = filled, color = red ];\n" );
		else {
			setTempId ( material );
			String materialId = "M_" + material.getId ();
			String materialLabel = material.getAcc ();
			dotCode.append ( "    subgraph cluster_" + nodeId + " { rank = same; color = white; { " + materialId + "; " + nodeId + " }" );
			dotCode.append ( "      " + materialId + "[label = \"" + materialLabel + "\", shape=box, style = filled, color = gold];\n" );
			dotCode.append ( "      " + nodeId + " -> " + materialId + ";\n" );
			dotCode.append ( "      " + nodeId + "[label = \""+ nodeLabel + "\", shape = ellipse, style = filled, color = ivory ];\n" );
			dotCode.append ( "    }\n" );
		}
		return dotCode;
	}
	
	/** Works on a single IN/OUT node */

	/** Works on a single node */
	private StringBuilder graphDataNode ( DataNode node )
	{
		StringBuilder dotCode = new StringBuilder ();

		String nodeId = getNodeId ( node );
		String nodeLabel = getNodeLabel ( node );

		Data data = node.getData ();
		if ( data == null )
			dotCode.append ( "    " + nodeId + "[label = \"" + nodeLabel + "\", shape = ellipse, style = filled, color = red ];\n" );
		else {
			setTempId ( data );
			String dataId = "D_" + data.getId ();
			String dataLabel = data.getAcc ();
			dotCode.append ( "    subgraph cluster_" + nodeId + " { rank = same; color = white; { " + dataId + "; " + nodeId + " }" );
			dotCode.append ( "      " + dataId + "[label = \"" + dataLabel + "\", shape=box, style = filled, color = yellow];\n" );
			dotCode.append ( "      " + nodeId + " -> " + dataId + ";\n" );
			dotCode.append ( "      " + nodeId + "[label = \"" + nodeLabel + "\", shape = ellipse, style = filled, color = ivory ];\n" );
			dotCode.append ( "    }\n" );
		}
		return dotCode;
	}


}
