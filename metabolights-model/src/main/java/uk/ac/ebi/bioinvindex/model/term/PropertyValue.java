package uk.ac.ebi.bioinvindex.model.term;

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

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Indexed;

import uk.ac.ebi.bioinvindex.model.Investigation;

import java.util.Collection;
import java.util.List;

/**
 * A super class for a value of property in "property name/property value" pair of classes (for example, {@link Characteristic}/{@link CharacteristicValue} )
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 12, 2007
 */
@Entity
@Table(name = "property_value")
@DiscriminatorColumn(name = "obj_type", discriminatorType = DiscriminatorType.STRING, length = 255)
public abstract class PropertyValue<PT extends Property<?>>
		extends FreeTextTerm {

	protected PT type;

	protected UnitValue unit;

	protected PropertyValue() {
		super();
	}

	protected PropertyValue(PT type) {
		this.setType(type);
	}

	protected PropertyValue(String value, PT type) {
		this(type);
		this.setValue(value);
	}


	// @ManyToOne(targetEntity = Property.class, cascade = CascadeType.ALL)
	@ManyToOne(targetEntity = Property.class)
	@JoinColumn(name = "PROPERTY_ID", nullable = false)
	public PT getType() {
		return this.type;
	}

	public void setType(PT type) {
		this.type = type;
//		try
//		{
//			if ( this.type == type ) return;
//			if ( this.type != null && this.type.getPropertyValues ().contains ( this ) )
//				( (Property<PropertyValue<PT>>) this.type ).removePropertyValue ( this );
//			this.type = type;
//			if ( type != null && !type.getPropertyValues ().contains ( this ) )
//				( (Property<PropertyValue<PT>>) type ).addPropertyValue ( this );
//		}
//		catch ( LazyInitializationException ex ) {
//			// Hibernate is working for us, let's not disturb
//			this.type = type;
//		}
	}


	// @ManyToOne(targetEntity = UnitValue.class, cascade = CascadeType.ALL)
	@ManyToOne(targetEntity = UnitValue.class)
	@JoinColumn(name = "unit")
	public UnitValue getUnit() {
		return this.unit;
	}

	public void setUnit(UnitValue unit) {
		this.unit = unit;
	}

	@ManyToMany ( targetEntity = OntologyTerm.class )
	@PrimaryKeyJoinColumns ( { 
		@PrimaryKeyJoinColumn ( name = "PV_ID", referencedColumnName = "ID" ), 
		@PrimaryKeyJoinColumn ( name = "OE_ID", referencedColumnName = "ID" ) 
	})
	@JoinTable(
		name = "PropertyValue2OT",
		joinColumns = {@JoinColumn(name = "PV_ID")},
		inverseJoinColumns = @JoinColumn(name = "OE_ID"),
		uniqueConstraints = { @UniqueConstraint ( columnNames = { "PV_ID", "OE_ID" } )}
	)
	public List<OntologyTerm> getOntologyTerms() {
		return super.getOntologyTerms();
	}
	
	
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		PropertyValue that = (PropertyValue) o;

		if (type != null ? !type.equals(that.type) : that.type != null) return false;
		if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;

		return true;
	}

	public int hashCode() {
		int result = super.hashCode(); 
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (unit != null ? unit.hashCode() : 0); 
		return result;
	}

	@Override
	public String toString() {
		String result = "PropertyValue{ " +
				"ID = " + getId() +
				" Type = " + this.getType() +
				" Value = " + this.getValue();
		UnitValue unit = this.getUnit();
		if (unit != null) result += " Unit = " + unit;
		result += " OntoTerms = " + getOntologyTerms() + " }";
		return result;
	}


}
