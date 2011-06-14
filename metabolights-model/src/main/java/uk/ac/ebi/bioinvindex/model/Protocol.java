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

import uk.ac.ebi.bioinvindex.model.term.Parameter;
import uk.ac.ebi.bioinvindex.model.term.ProtocolComponent;
import uk.ac.ebi.bioinvindex.model.term.ProtocolType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Object representing wet/dry laboratory operating procedures.
 * 
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Nov 20, 2007
 */
@Entity
@Table(name = "PROTOCOL")
public class Protocol extends Accessible 
{

	private String name;
	private ProtocolType type;
	private String description;
	private String uri;
	private String version;
	private Collection<Parameter> parameters = new ArrayList<Parameter>();
	private Collection<ProtocolComponent> components = new ArrayList<ProtocolComponent> ();
	
	
	protected Protocol() {
		super();
	}

	public Protocol(String name, ProtocolType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne ( targetEntity = ProtocolType.class )
	@JoinColumn ( name = "type", nullable = true )
	public ProtocolType getType () {
		return type;
	}

	public void setType(ProtocolType type) {
		this.type = type;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUri () {
		return uri;
	}

	public void setUri ( String uri ) {
		this.uri = uri;
	}

	public String getVersion () {
		return version;
	}

	public void setVersion ( String version ) {
		this.version = version;
	}

	@OneToMany( targetEntity = Parameter.class )
  @JoinColumn ( name = "PROTOCOL_ID", nullable = true )			
// TODO: Remove
//	@JoinTable(
//			name = "Protocol2Property",
//			joinColumns = {@JoinColumn(name = "PROTOCOL_ID")},
//			inverseJoinColumns = @JoinColumn(name = "PROPERTY_ID")
//	)
	public Collection<Parameter> getParameters () {
		return Collections.unmodifiableCollection ( parameters );
	}

	public void addParameter ( Parameter parameter ) {
		this.parameters.add ( parameter );
	}

	protected void setParameters ( Collection<Parameter> parameters ) {
		this.parameters = parameters;
	}

	public boolean removeParameter ( Parameter parameter ) {
		return this.parameters.remove ( parameter );
	}
	
	public Parameter findParameterByName ( String name ) 
	{
		for ( Parameter param: getParameters () ) {
			if ( param != null && name.equals ( param.getValue () ) ) return param;
		}
		return null;
	} 
	

	@OneToMany( targetEntity = ProtocolComponent.class )
  @JoinColumn ( name = "protocol_id", nullable = true )			
	public Collection<ProtocolComponent> getComponents () {
		return Collections.unmodifiableCollection ( components );
	}

	protected void setComponents ( Collection<ProtocolComponent> components ) {
		this.components = components;
	}

	public void addComponent ( ProtocolComponent component ) {
		components.add ( component );
	}
	
	
	public boolean equals(Object o) 
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Protocol protocol = (Protocol) o;

		if (description != null ? !description.equals(protocol.description) : protocol.description != null) return false;
		if (!name.equals(protocol.name)) return false;
		if (parameters != null ? !parameters.equals(protocol.parameters) : protocol.parameters != null) return false;
		if (components != null ? !components.equals(protocol.components) : protocol.components != null) return false;
		if (type != null ? !type.equals(protocol.type) : protocol.type != null) return false;

		return true;
	}

	public int hashCode() 
	{
		int result = super.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
		result = 31 * result + (components != null ? components.hashCode() : 0);
		return result;
	}

	public String toString() 
	{
		return "Protocol{" +
			"id=" + getId () + 
			", acc='" + getAcc() + '\'' +
			", name='" + name + "'" +
			", type=" + type +
			", uri='" + uri + "'" +
			", version='" + version + "'" +
			", description='" + StringUtils.substring ( description, 0, 20 ) + '\'' +
			", parameters=" + parameters +
			", components=" + components +
			'}';
	}

}
