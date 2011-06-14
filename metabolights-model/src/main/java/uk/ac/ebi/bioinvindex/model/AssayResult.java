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

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.CharacteristicValue;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.bioinvindex.model.term.Property;
import uk.ac.ebi.bioinvindex.model.term.PropertyRole;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import uk.ac.ebi.bioinvindex.model.term.UnitValue;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.OrganismValuesBridge;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.PropertyValuesBridge;
import uk.ac.ebi.bioinvindex.utils.processing.CharacteristicValueVisitor;
import uk.ac.ebi.bioinvindex.utils.processing.ExperimentalPipelineVisitor;
import uk.ac.ebi.bioinvindex.utils.processing.FactorValueVisitor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Objects of this class put together a link to a data and all property/protocol parameter values of corresponding
 * materials/protocols which this data was obtained from.
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Oct 2, 2008
 */

@Entity
@Table(name = "ASSAYRESULT")
public class AssayResult extends Identifiable {

	private Data data;

	@ContainedIn
	private Study study;

	@Fields({
		@Field(index = Index.TOKENIZED, store = Store.YES,
			bridge = @FieldBridge(impl = PropertyValuesBridge.class)),
		@Field(index = Index.UN_TOKENIZED, store = Store.YES,
			bridge = @FieldBridge(impl = OrganismValuesBridge.class))
	})
	private Collection<PropertyValue> cascadedValues = new HashSet<PropertyValue>();

	private Collection<Assay> assays = new HashSet<Assay>();
	
	protected AssayResult() {

	}

	public AssayResult(Data data, Study study) {
		this.data = data;
		this.study = study;
		study.addAssayResult ( this );
	}

	@OneToOne(targetEntity = Data.class)
	@JoinColumn(name = "DATA_ID", nullable = true)
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ManyToOne(targetEntity = Study.class)
	@JoinColumn(name = "STUDY_ID", nullable = false)
	public Study getStudy() {
		return study;
	}


	protected void setStudy(Study study) {
		this.study = study;
	}


	@ManyToMany(targetEntity = PropertyValue.class, fetch = FetchType.LAZY)
	@JoinTable(
			name = "AssayResult2PropertyValue",
			joinColumns = {@JoinColumn(name = "AR_ID")},
			inverseJoinColumns = @JoinColumn(name = "PV_ID")
	)
	public Collection<PropertyValue> getCascadedPropertyValues() {
		return this.cascadedValues;
	}

	public void addCascadedPropertyValue(PropertyValue value) {
		this.cascadedValues.add(value);
	}

	protected void setCascadedPropertyValues(Collection<PropertyValue> values) {
		this.cascadedValues = values;
	}

	public boolean removeCascadedPropertyValue(PropertyValue value) {
		return cascadedValues.remove(value);
	}

	@Transient
	public Collection<FactorValue> getFactorValues() {
		Collection<FactorValue> answer = new ArrayList<FactorValue>();

		for (PropertyValue value : cascadedValues) {
			if (value instanceof FactorValue) {
				answer.add((FactorValue) value);
			}
		}
		return answer;
	}

	@ManyToMany(targetEntity = Assay.class, fetch = FetchType.LAZY)
	@JoinTable(
			name = "AssayResult2Assay",
			joinColumns = {@JoinColumn(name = "AR_ID")},
			inverseJoinColumns = @JoinColumn(name = "assay_ID")
	)
	public Collection<Assay> getAssays() {
		return assays;
	}

	public boolean addAssay(Assay assay) {
		return this.assays.add(assay);
	}

	protected void setAssays(Collection<Assay> assays) {
		this.assays = assays;
	}

	public boolean removeAssay ( Assay assay ) {
		return this.assays.remove ( assay );
	}
	
	
	/**
	 * Goes through the pipeline associated to this AssayResult and extracts all the {@link CharacteristicValue} associated to
	 * the materials in it.
	 * <p/>
	 * It is used to setup the cascaded properties during ISATAB submission.
	 *
	 * TODO: for the moment relies on the correct assignment of accessions to nodes (used in .equals() and .hashCode())
	 * 
	 */
	public Collection<CharacteristicValue> findPipelineCharacteristicValues() {
		CharacteristicValueVisitor visitor = new CharacteristicValueVisitor();
		new ExperimentalPipelineVisitor ( visitor ).visitBackward ( this.getData ().getProcessingNode() );
		return visitor.getPipelineCharacteristicValues();
	}

	/**
	 * Goes through the pipeline associated to this AssayResult and extracts all the {@link FactorValue} associated to
	 * the materials in it.
	 * <p/>
	 * It is used to setup the cascaded properties during ISATAB submission.
	 *
	 * TODO: for the moment relies on the correct assignment of accessions to nodes (used in .equals() and .hashCode())
	 * 
	 */
	public Collection<FactorValue> findPipelineFactorValues() {
		FactorValueVisitor visitor = new FactorValueVisitor ();
		new ExperimentalPipelineVisitor ( visitor ).visitBackward ( this.getData ().getProcessingNode() );
		return visitor.getPipelineFVs ();
	}


	/**
	 * Returns the same collection passed as parameter, but with the duplicates removed. Two property values are
	 * considered dupes, when they have the same strings in the values, the unit, the type's string value.
	 * <p/>
	 * Note: this method is not much advanced, for example we don't address complex cases like 
	 * [1200 joule] = [1.2 kJ] = [1.2 N*m] 
	 *
	 * @return the filtered characteristic values, with the values sorted according to type and value
	 */
	public static <PV extends PropertyValue<?>> Collection<PV> filterRepeatedPropertyValues ( Collection<PV> propValues )
	{
		// Let's do it by setting the map keys with the equality criterion
		//
		SortedMap<String, PV> result = new TreeMap<String, PV>();

		for (PV value : propValues) {
			StringBuilder key = new StringBuilder();

			Property<?> type = value.getType();
			if (type != null)
				key.append(StringUtils.trimToEmpty(type.getValue()).toLowerCase());

			PropertyRole role = type.getRole();
			if (role != null) switch (role) {
				case FACTOR:
					key.append("FACTOR");
					break;
				case PROPERTY:
					key.append("PROPERTY");
					break;
				default:
					throw new RuntimeException("Cannot deal with the property type: " + role);
			}

			key.append(StringUtils.trimToEmpty(value.getValue()).toLowerCase());

			UnitValue unit = value.getUnit();
			if (unit != null)
				key.append(StringUtils.trimToEmpty(unit.getValue()));

			result.put(key.toString(), value);
		}

		return result.values();
	}



	@Override
	public String toString ()
	{
		StringBuilder result = new StringBuilder ( "AssayResult { " );
		if ( study != null ) {
			result.append ( "Study { id = " ).append ( study.getId () ).append ( ", acc = " ).append ( study.getAcc () );
			result.append ( " } " );
		}
		result.append ( " Data = " + data );
		result.append ( " Cascaded Properties = " + cascadedValues );
		result.append ( " }" );
		return result.toString ();
	}

}
