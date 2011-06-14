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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uk.ac.ebi.bioinvindex.model.Accessible;
import uk.ac.ebi.bioinvindex.model.Protocol;
import uk.ac.ebi.bioinvindex.model.term.Parameter;
import uk.ac.ebi.bioinvindex.model.term.ParameterValue;
import uk.ac.ebi.bioinvindex.model.term.UnitValue;

/**
 * Object representing one processing event. It captures information about an applied {@link
 * uk.ac.ebi.bioinvindex.model.Protocol} including the parameters’ value set ({@link
 * uk.ac.ebi.bioinvindex.model.term.ParameterValue}) in the specific application.
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Jan 2, 2008
 */
@Entity
@Table(name = "PROTOCOLAPPLICATION")
public class ProtocolApplication extends Accessible 
{

	private int order = -1;

	private Protocol protocol;

	private Collection<ParameterValue> parameterValues = new ArrayList<ParameterValue>();

	/**
	 * <p>This comment is used to tell that two (or more) equivalent protocol applications in a row are
	 * actually the same application. "Equivalent" means the same protocol and the same parameter/values. 
	 * A case like this:</p>
	 * 
	 * <table border = "1">
	 *   <tr><td>sample1</td><td>proto1</td><td>sample3</td></tr>
	 *   <tr><td>sample2</td><td>proto1</td><td>sample4</td></tr>
	 * </table>
	 * 
	 * <p>is usually interpreted as two applications of the same protocol proto1, each with one input and one output. 
	 * This is what the biologist means most of the time. We allow to provide the alternative meaning of proto1, having
	 * two inputs and two outputs and hence applied once only (eg: proto1 = mix sample1 and sample2 in a container and 
	 * then pick two new samples from it). This alternative interpretation is assumed when 
	 * Comment[{@link #UNIQUE_PAPP_COMMENT}] = yes.</p>
	 * 
	 * <p>Moreover, a case where two equivalent protocol applications have the same outputs, by default, is interpreted as 
	 * the same application, independently on the row positions:</p>
	 * 
	 * <table border = "1">
	 *   <tr><td>sample1</td><td>proto1</td><td>sample3</td></tr>
	 *   <tr><td>sample2</td><td>proto1</td><td>sample3</td></tr>
	 * </table>
	 * 
	 * <p>This default interpretation can be changed with Comment[{@link #UNIQUE_PAPP_COMMENT}] = no.</p>
	 * 
	 * <p>
	 * <b>Notes</b>
	 * <ul>
	 * <li>In both cases above, you <i>must</i> always specify a protocol REF. sample1=>sample2 is a processing with unspecified
	 * protocol and, because of that, it is always considered as an application distinct from anything else.</li>
	 * 
	 * <li>In order for the comment to take effect, it must have the same value for all applications in success, 
	 * e.g.: sample1 => proto1 => proto2 => sample2, you must have either two "yes" or two "no" to obtain correct results.
	 * Otherwise, the situation is treated as if the {@link #UNIQUE_PAPP_COMMENT} was never specified and it falls back to
	 * the default behaviors.
	 * </li>
	 * </ul>
	 * </p>
	 */
	public static final String UNIQUE_PAPP_COMMENT = "comment:Unique Protocol Application"; 
	/**
	 * These values set for {@link #UNIQUE_PAPP_COMMENT} are all translated to true (case insensitive), all the others
	 * are considered as equivalent to false.
	 * 
	 */
	public static final String[] UNIQUE_PAPP_COMMENT_TRUE_VALUES = new String [] { "yes", "y", "true", "1", "t" };
	
	public ProtocolApplication() {
	}

	public ProtocolApplication(Protocol protocol) {
		this.protocol = protocol;
	}

	@ManyToOne
	public Protocol getProtocol() {
		return protocol;
	}

	protected void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@OneToMany(targetEntity = ParameterValue.class, mappedBy = "protocolApplication" /*,  cascade = CascadeType.ALL */)
	public Collection<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	public void addParameterValue(ParameterValue value) {
		parameterValues.add(value);
		value.setProtocolApplication(this);
	}

	public boolean removeParameterValue(ParameterValue value) {
		return parameterValues.remove(value);
	}

	protected void setParameterValues(Collection<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}

	@Column(name = "position")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	
	@Transient
	public Collection<ParameterValue> getParameterValuesByType ( String paramTypeName ) 
	{
		Collection<ParameterValue> result = new HashSet<ParameterValue> ();
		
		for ( ParameterValue pvalue: getParameterValues () ) 
		{
			Parameter ptype = pvalue.getType ();
			if ( ptype == null ) continue;
			if ( paramTypeName.equalsIgnoreCase ( ptype.getValue () ) ) 
				result.add ( pvalue );
		} 
		return result;
	}

	public ParameterValue getParameterValueByType ( String paramTypeName ) 
	{
		Collection<ParameterValue> pvalues = getParameterValuesByType ( paramTypeName );
		return pvalues.size () == 0 ? null : pvalues.iterator ().next ();
	}
	
	public boolean equals(Object o) 
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProtocolApplication papp = (ProtocolApplication) o;

		if ( this.order != papp.order ) return false;
		// If the protocol is not defined we must assume they're different, we know some protocols were applied 
		// but we don't know which ones.
		if ( protocol == null || papp.protocol == null || !protocol.equals ( papp.protocol ) ) return false;
		
		if ( parameterValues != null 
			? papp.parameterValues == null || !parameterValues.equals ( papp.parameterValues )	
			: papp.parameterValues != null ) 
			return false;
		
		return true;
	}

	public int hashCode() 
	{
		int result = super.hashCode();
		result = 31 * result + order;  
		result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
		result = 31 * result + (parameterValues != null ? parameterValues.hashCode () : 0);
		return result;
	}

	
	

	public String toString() {
		StringBuilder result = new StringBuilder("ProtocolApplication { id =  " + this.getId());
		Protocol protocol = this.getProtocol();
		result.append("\n  protocol = " + protocol);
		result.append("\n  first parameters: [ ");
		String separator = "";
		int count = 0;
		for (ParameterValue paramValue : this.getParameterValues()) {
			result.append(separator);
			Parameter paramType = paramValue.getType();
			String paramName = paramType.getValue();
			result.append(paramName != null && paramName.length() != 0 ? "\"" + paramName + "\"" : "?");
			result.append(" = \"" + paramValue.getValue());
			UnitValue unit = paramValue.getUnit();
			if (unit != null) result.append(unit.getValue());
			result.append("\"");
			separator = ", ";
			if (++count > 10) break; // cannot be too long
		}
		result.append("] }");
		return result.toString();
	}
	
}
