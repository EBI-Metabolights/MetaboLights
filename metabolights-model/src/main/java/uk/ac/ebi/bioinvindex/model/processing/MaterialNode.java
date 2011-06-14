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

import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.Study;


/**
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 30, 2007
 */
@Entity
@DiscriminatorValue( "MaterialNode" )
public class MaterialNode extends Node 
{

	private Material material;
	private Set<String> assayFileIds = null;

	protected MaterialNode() {
		super();
	}

	public MaterialNode(Study study) {
		super(study);
	}

	public MaterialNode(Study study, boolean original) {
		super(study, original);
	}

	@OneToOne( targetEntity = Material.class, mappedBy = "materialNode" )
	public Material getMaterial() {
		return this.material;
	}
	
	/**
	 * Setup the material associated to this node and consequently changes the node associated to the parameter.
	 * @param material
	 */
	public void setMaterial ( Material material ) 
	{
		Material oldMaterial = this.material;
		this.material = material;
		
		if (oldMaterial != null && oldMaterial != material) {
			oldMaterial.setMaterialNode ( null );
		}

		if (material == null) return;

		MaterialNode node = material.getMaterialNode ();
		if (node != this) material.setMaterialNode ( this );
	}

	@Transient
	@Override
	public String getSampleFileId () {
		return this.material == null ? null : this.material.getSingleAnnotationValue ( "sampleFileId" );
	}

	@Transient
	@Override
	public Set<String> getAssayFileIds () 
	{
		if ( assayFileIds != null ) return assayFileIds;
		if ( this.material == null ) {
			assayFileIds = Collections.emptySet ();
			return assayFileIds;
		}
		assayFileIds = new HashSet<String> ();
		assayFileIds.addAll ( this.material.getAnnotationValues ( "assayFileId" ) );
		return assayFileIds;
	}
	
	
	@Override
	public String toString () 
	{
		return 
		  "MaterialNode { id: " + getId() + ", acc: " + getAcc ()
		  + ", material: " + getMaterial () + " }";
	}

}
