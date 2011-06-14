package uk.ac.ebi.bioinvindex.mibbi;

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

import uk.ac.ebi.bioinvindex.model.Identifiable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Nataliya Sklyar
 * Date: Feb 24, 2009
 */
@Entity
@Table(name = "miproject")
public class MIProject extends Identifiable {

	private static String URL_PREFIX = "http://www.mibbi.org/index.php/Projects/";

	private String acronym;

	private String fullName;

	private String url;


	protected MIProject() {
	}

	public MIProject(String acronym, String fullName) {
		this.acronym = acronym;
		this.fullName = fullName;
		url = URL_PREFIX + acronym;
	}

	public String getAcronym() {
		return acronym;
	}

	protected void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getFullName() {
		return fullName;
	}

	protected void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUrl() {
		if (url == null) {
			url = URL_PREFIX + acronym;
		}
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MIProject miProject = (MIProject) o;

		if (!fullName.equals(miProject.fullName)) return false;
		if (!acronym.equals(miProject.acronym)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = acronym.hashCode();
		result = 31 * result + fullName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "MIProject{" +
				"acronym='" + acronym + '\'' +
				", fullName='" + fullName + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
