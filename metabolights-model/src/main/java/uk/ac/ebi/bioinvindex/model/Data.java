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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import uk.ac.ebi.bioinvindex.model.processing.DataNode;
import uk.ac.ebi.bioinvindex.model.term.DataType;
import uk.ac.ebi.bioinvindex.model.term.Factor;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.bioinvindex.model.term.UnitValue;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Object representing data files produced directly by the Assay, as well as beyond the Assay e.g. high-level
 * conclusions,reports, meta-analysis, models and pathways.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 *         Date: Jul 18, 2007
 */
@Entity
@Table(name = "data")
public class Data extends Accessible {

	private String url, dataMatrixUrl;
	private String name;
	private DataNode processingNode;
	private DataType type;
	private Collection<FactorValue> factorValues = new ArrayList<FactorValue> ();

	
	protected Data() {
	}


	public Data(String url, DataType type) {
		this.url = url;
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDataMatrixUrl() {
		return dataMatrixUrl;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setDataMatrixUrl(String dataMatrixUrl) {
		this.dataMatrixUrl = dataMatrixUrl;
	}


	@ManyToOne(targetEntity = DataType.class /*, cascade = CascadeType.PERSIST */)
	@JoinColumn(name = "type")
	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}


	/**
	 * The data nodes where this data is referenced.
	 *
	 * @return
	 */
	@OneToOne ( targetEntity = DataNode.class )
	@JoinColumn(name = "NODE_ID", nullable = false)
	public DataNode getProcessingNode () {
		return processingNode;
	}
	
	/**
	 * Setup the processing node and consequently changes the data associated to the parameter.
	 *
	 * @param processingNode
	 */
	public void setProcessingNode(DataNode processingNode) 
	{
		DataNode oldNode = this.processingNode;
		this.processingNode = processingNode;
		
		if (oldNode != null && oldNode != processingNode) {
			oldNode.setData ( null );
		}

		if (processingNode == null) return;

		Data data = processingNode.getData ();
		if (data != this) processingNode.setData ( this );
	}

	@OneToMany(targetEntity = FactorValue.class )
	@JoinTable ( name = "data2fv" )
	@JoinColumn ( name = "data_id", nullable = false )
	public Collection<FactorValue> getFactorValues() {
		return factorValues;
	}

	protected void setFactorValues(Collection<FactorValue> values) {
		this.factorValues = values;
	}

	public void addFactorValue(FactorValue value) {
		factorValues.add ( value );
	}

	public boolean removeFactorValue(FactorValue value) {
		return factorValues.remove(value);
	}
	
	

	@Override
	public String toString() 
	{
		StringBuilder result = new StringBuilder ();
		
		result.append ( "Data { id =  " ).append ( this.getId() ).append ( " name = \"" ).append ( this.getName() ).
			append ( "\" url = \"" ).append ( getUrl() ).append ( "\" dataMatrixUrl = \"" ).append ( getDataMatrixUrl() ).append ( "\" }" );
		
	  result.append ( "\n  Factor Values: [ "  );
	  String separator = "";
	  for ( FactorValue fv: this.getFactorValues () ) {
	  	Factor fvType = fv.getType ();
	  	String characteristicName = fvType.getValue ();
	  	result.append ( separator );
	  	result.append ( characteristicName != null && characteristicName.length () != 0 ? "\"" + characteristicName + "\"" : "?" );
	  	result.append ( " = \"" ).append ( fv.getValue () );
	  	UnitValue unit = fv.getUnit ();
	  	if ( unit != null ) result.append ( unit.getValue () );
	  	result.append ( "\"" );
	  	separator = ", ";
	  }
	  result.append ( "]\n}" );
	  
	  return result.toString ();
	}

}
