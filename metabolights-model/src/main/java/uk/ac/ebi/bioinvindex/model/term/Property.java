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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * A super class for name of property in "property name/property value" pair of classes (for example, {@link Characteristic}/{@link CharacteristicValue} )
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jul 13, 2007
 */
@Entity
@Table(name = "PROPERTY")
@DiscriminatorColumn(name = "obj_type", discriminatorType = DiscriminatorType.STRING, length = 255)

public abstract class Property<PV extends PropertyValue<?>>
		extends FreeTextTerm {

//	private String value;

	private PropertyRole role;

	private int order;

	private Collection<PV> propertyValues = new HashSet<PV>();

	protected Property() {
	}

	protected Property(String value, PropertyRole role, int order) {
		super(value);
		this.role = role;
		this.order = order;
	}

	protected Property(PropertyRole role, int order) {
		this.role = role;
		this.order = order;
	}


	public PropertyRole getRole() {
		return role;
	}

	public void setRole(PropertyRole role) {
		this.role = role;
	}


	@Column(name = "position")
	public int getOrder() {
		return order;
	}

	@ManyToMany(targetEntity = OntologyTerm.class)
	@JoinTable(
			name = "Property2OE",
			joinColumns = {@JoinColumn(name = "PROPERTY_ID")},
			inverseJoinColumns = @JoinColumn(name = "OE_ID")
	)
	public List<OntologyTerm> getOntologyTerms() {
		return super.getOntologyTerms();
	}

	// Submission tool needs this
	public void setOrder(int order) {
		this.order = order;
	}

	@OneToMany(targetEntity = uk.ac.ebi.bioinvindex.model.term.PropertyValue.class,
			mappedBy = "type")
	@Fetch(FetchMode.SUBSELECT)
	public Collection<PV> getPropertyValues() {
//		return Collections.unmodifiableCollection ( propertyValues );
		return propertyValues;
	}

	public boolean addPropertyValue(PV value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot add a null value to the type" + this.getValue());
		}

		boolean result = false;

		if (value.getType() != this) {
			((PropertyValue<Property<PV>>) value).setType(this);
			result = true;
		}

		return this.propertyValues.add(value) || result;
	}


	protected void setPropertyValues(Collection<PV> propertyValues) {
		this.propertyValues = propertyValues;
	}


	public boolean removePropertyValue(PV value) {
		if (value == null) return false;
		if (value.getType() != this) return false;
		propertyValues.remove(value);
		value.setType(null);
		return true;
	}

	@Override
	public String toString() {
		return "Property{ " +
				"ID = " + getId() +
				" Value = " + getValue() +
				" OntoTerms = " + getOntologyTerms() +
				" Role = " + role +
				" }";
	}


	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Property<?> that = (Property<?>) o;

		if (this.order != that.order) return false;
		if (role == null ? that.role != null : !role.equals(that.role)) return false;

		return true;
	}

	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + order;
		result = 31 * result + (role == null ? 0 : role.hashCode());
		return result;
	}


}
