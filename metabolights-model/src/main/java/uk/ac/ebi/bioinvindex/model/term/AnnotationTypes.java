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

/**
 * @author Nataliya Sklyar Date: Mar 2, 2009
 */
public enum AnnotationTypes {

	META_DATA_FILES_RELATIVE_PATH("BII:METADATA"),

	RAW_DATA_FILE_PATH("RAW_DATA_FILE_PATH"),
	RAW_DATA_FILE_LINK("RAW_DATA_FILE_LINK"),
	PROCESSED_DATA_FILE_PATH("PROCESSED_DATA_FILE_PATH"),
	PROCESSED_DATA_FILE_LINK("PROCESSED_DATA_FILE_LINK"),
	GENERIC_DATA_FILE_PATH("GENERIC_DATA_FILE_PATH"),
	GENERIC_DATA_FILE_LINK("GENERIC_DATA_FILE_LINK"),
	
	WEB_ENTRY_URL("WEB_ENTRY_URL"),

	ISATAB_LOCATION_PATH("ISATAB_LOCATION_PATH"),
	ISATAB_LOCATION_LINK("ISATAB_LOCATION_LINK");
	
	private String name;

	AnnotationTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public AnnotationType getType() {
		return new AnnotationType(this.name);
	}

	/** 
	 * Types referring to raw data, processed data, generic data
	 */
	public static final AnnotationTypes[] DATA_PATH_ANNOTATIONS = new AnnotationTypes [] {
		RAW_DATA_FILE_PATH, PROCESSED_DATA_FILE_PATH, GENERIC_DATA_FILE_PATH
	};

}
