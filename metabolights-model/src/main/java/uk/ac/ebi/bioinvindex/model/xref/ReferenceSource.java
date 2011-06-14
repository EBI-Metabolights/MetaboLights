package uk.ac.ebi.bioinvindex.model.xref;

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

import uk.ac.ebi.bioinvindex.model.Accessible;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Sep 19, 2007
 */
@Entity
@Table(name = "REFERENCE_SOURCE")
public class ReferenceSource extends Accessible {

	public final static String ISATAB_METADATA = "ISATAB_METADATA";
	private String name;
	private String url;
	private String description;

	// TODO: maybe this goes to some versioning management stuff?
	private String version;

	protected ReferenceSource() {
	}

	public ReferenceSource(String name) {
		this.name = name;
	}

	public ReferenceSource ( String acc, String name) {
		this.acc = acc;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof ReferenceSource)) return false;
		if (!super.equals(o)) return false;

		ReferenceSource that = (ReferenceSource) o;

		// See the note in super.equals()
		final String description = getDescription (), name = getName (), url = getUrl ();
		if (description != null ? !description.equals(that.getDescription ()) : that.getDescription () != null) return false;
		if (name != null ? !name.equals(that.getName ()) : that.getName () != null) return false;
		if (url != null ? !url.equals(that.getUrl ()) : that.getUrl () != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		final String description = getDescription (), name = getName (), url = getUrl ();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	public String toString() {
		return "ReferenceSource{" +
				"id='" + getId() + "' " +
				"acc='" + acc + "' " +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				", version='" + version + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
