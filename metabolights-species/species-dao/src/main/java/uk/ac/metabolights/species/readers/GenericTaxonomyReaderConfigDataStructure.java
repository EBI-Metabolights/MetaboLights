/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 12/11/13 11:00
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.metabolights.species.readers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * User: conesa
 * Date: 31/10/2013
 * Time: 11:39
 */
public class GenericTaxonomyReaderConfigDataStructure {

	static Logger logger = LogManager.getLogger(GenericTaxonomyReaderConfigDataStructure.class);

	private String taxonomyId;
	private String version;
	private String description;
	private String fieldSeparator = "\t";
	private int numberRowsToSkip = 1;

	public enum TaxonomyColumns{
		TAXON_ID,
		NAME,
		COMMON_NAME,
		PARENT_ID
	}
	// Column positions in the file to get taxon data.
	// By default 0,1,2,3
	// 0 --> Id
	// 1 --> name
	// 2 --> common name
	// 3 --> parent id
	int[] columnPositions = new int[]{0,1,2,3};
	/*
	Receives an array of Strings with configuration values:

	MANDATORY:
	[0] --> taxonomyId
	[1] --> value
	[2] --> taxonomy description

	OPTIONAL (null to keep default):
	[3] --> column position string (default: "0,1,2,3". 4 Integers separated by "," .)
	[4] --> fieldSeparator ( default: "\t", tab)
	[5] --> numberRowsToSkip ( default: 1. Should cast into an int).

	 */
	public GenericTaxonomyReaderConfigDataStructure(String[] values){

		logger.info("Configuring GenericTaxonomyReaderConfigDataStructure");

		// Taxonomy related values
		taxonomyId = values[0];
		version = values[1];
		description = values[2];

		// File parsing parameters (Optionals...)
		if (values.length>3 && values[3] != null) setColumnPositions(values[3]);
		if (values.length>4 && values[4] != null) fieldSeparator = values[4];
		if (values.length>5 && values[5] != null) numberRowsToSkip = Integer.parseInt(values[5]);
	}


	public String getTaxonomyId() {
		return taxonomyId;
	}

	public void setTaxonomyId(String taxonomyId) {
		this.taxonomyId = taxonomyId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberRowsToSkip() {
		return numberRowsToSkip;
	}

	public void setNumberRowsToSkip(int numberRowsToSkip) {
		this.numberRowsToSkip = numberRowsToSkip;
	}

	public int[] getColumnPositions() {
		return columnPositions;
	}

	public void setColumnPositions(int[] columnPositions) {
		this.columnPositions = columnPositions;
	}

	public void setColumnPositions(String columnPositionsS, String positionsSeparator) {

		String[] positionsS = columnPositionsS.split(positionsSeparator);

		columnPositions = new int[positionsS.length];

		for (int i=0; i < positionsS.length; i++) {
			columnPositions[i] = Integer.parseInt(positionsS[i]);
		}

	}

	public void setColumnPositions(String columnPositionsS) {

		setColumnPositions(columnPositionsS,",");
	}
	public String getFieldSeparator() {
		return fieldSeparator;
	}

	public void setFieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}
}
