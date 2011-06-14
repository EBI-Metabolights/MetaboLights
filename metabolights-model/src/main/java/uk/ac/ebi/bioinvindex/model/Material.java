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

import uk.ac.ebi.bioinvindex.model.processing.DataNode;
import uk.ac.ebi.bioinvindex.model.processing.MaterialNode;
import uk.ac.ebi.bioinvindex.model.term.Characteristic;
import uk.ac.ebi.bioinvindex.model.term.Factor;
import uk.ac.ebi.bioinvindex.model.term.PropertyRole;
import uk.ac.ebi.bioinvindex.model.term.CharacteristicValue;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.bioinvindex.model.term.MaterialRole;
import uk.ac.ebi.bioinvindex.model.term.UnitValue;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Object representing the experimental material in all its forms, e.g. animal, cell line, excised gel spot, protein.
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 12, 2007
 */
@Entity
@Table(name = "material")
public class Material extends Accessible {

	private Collection<CharacteristicValue> characteristicValues = new ArrayList<CharacteristicValue>();
	private Collection<FactorValue> factorValues = new ArrayList<FactorValue> ();
	private MaterialNode processingNode;


	private String name;

	private MaterialRole type;

	public Material(String name, MaterialRole type) {
		this.name = name;
		this.type = type;
	}

	protected Material() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(targetEntity = CharacteristicValue.class, mappedBy = "material" /*, cascade = CascadeType.ALL */)
	public Collection<CharacteristicValue> getCharacteristicValues() {
		return characteristicValues;
	}

	protected void setCharacteristicValues(Collection<CharacteristicValue> values) {
		this.characteristicValues = values;
	}

	public void addCharacteristicValue(CharacteristicValue value) {
		// This MUST be before, until we fix the hash code computation
		value.setMaterial(this);
		characteristicValues.add(value);
	}

	public boolean removeCharacteristicValue(CharacteristicValue value) {
		return characteristicValues.remove(value);
	}

	@OneToMany(targetEntity = FactorValue.class )
	@JoinTable ( name = "material2fv" )
	@JoinColumn ( name = "material_id", nullable = false )
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
	
	
	@ManyToOne(targetEntity = MaterialRole.class)
	@JoinColumn(name = "type", nullable = false)
	public MaterialRole getType() {
		return type;
	}

	public void setType(MaterialRole type) {
		this.type = type;
	}


	/**
	 * The processing nodes where this material is involved.
	 *
	 */
	@OneToOne(targetEntity = MaterialNode.class )
	@JoinColumn(name = "NODE_ID", nullable = false )
	public MaterialNode getMaterialNode() {
		return processingNode;
	}

	/**
	 * Setup the processing node and consequently changes the material associated to the parameter.
	 * @param processingNode
	 */
	
	public void setMaterialNode ( MaterialNode processingNode )
	{
		MaterialNode oldNode = this.processingNode;
		this.processingNode = processingNode;
		
		if (oldNode != null && oldNode != processingNode) {
			oldNode.setMaterial ( null );
		}

		if (processingNode == null) return;

		Material material = processingNode.getMaterial ();
		if (material != this) processingNode.setMaterial ( this );
	}



	@Override
	public String toString ()
	{
		StringBuilder result = new StringBuilder ( "Material { id = " );
		result.append ( this.getId() ).append ( " name = \"" ).append ( this.getName () ).append ( "\"" );
	  result.append ( "\n  characteristics: [ "  );
	  String separator = "";
	  for ( CharacteristicValue characteristic: this.getCharacteristicValues () ) {
	  	result.append ( separator );
	  	Characteristic characteristicType = characteristic.getType ();
	  	String characteristicName = characteristicType.getValue ();
	  	result.append ( characteristicName != null && characteristicName.length () != 0 ? "\"" + characteristicName + "\"" : "?" );
	  	result.append ( " = \"" ).append ( characteristic.getValue () );
	  	UnitValue unit = characteristic.getUnit ();
	  	if ( unit != null ) result.append ( unit.getValue () );
	  	result.append ( "\"" );
	  	separator = ", ";
	  }
	  result.append ( "]\n}" );
	  
	  result.append ( "\n  Factor Values: [ "  );
	  separator = "";
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
