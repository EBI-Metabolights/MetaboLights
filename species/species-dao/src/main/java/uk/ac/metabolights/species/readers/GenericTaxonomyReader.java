package uk.ac.metabolights.species.readers;

import uk.ac.ebi.metabolights.species.model.Taxon;
import uk.ac.ebi.metabolights.species.model.Taxonomy;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: conesa
 * Date: 29/10/2013
 * Time: 16:52
 */
public class GenericTaxonomyReader extends TaxonomyReader {


	String fieldSeparator = "\t";

	private enum TaxonomyColumns{
		ID ,
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
	File taxonomyFile;

	public void GenericTaxonomyReader(String taxonomyPath, String id, String version, String description) throws ConfigurationException {
		taxonomy = new Taxonomy(description,id,version);

		taxonomyFile = new File(taxonomyPath);

		// Check file exist
		if (!taxonomyFile.exists()){
			throw new ConfigurationException("Taxonomy file does not exists. " + taxonomyPath);
		}

	}

	@Override
	protected Taxonomy instantiateTaxonomy() {

		// Should be instantiated in the constructor
		return taxonomy;

	}

	@Override
	public void loadTaxonomy() throws ConfigurationException {

		// Open the taxonomy file
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(taxonomyFile));

			while ((sCurrentLine = br.readLine()) != null) {

				Taxon currentTaxon = lineToTaxon(sCurrentLine);

				taxonRead(currentTaxon);

			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {

			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}


	}

	private Taxon lineToTaxon (String line){

		String[] values = line.split(fieldSeparator);

		String id, name, commonName, parentId;

		id = values[TaxonomyColumns.ID.ordinal()];
		name = values[TaxonomyColumns.NAME.ordinal()];
		commonName = values[TaxonomyColumns.COMMON_NAME.ordinal()];
		parentId = values[TaxonomyColumns.PARENT_ID.ordinal()];

		Taxon taxon = new Taxon(id,  name,  commonName,  parentId);


	}

	public String getFieldSeparator() {
		return fieldSeparator;
	}

	public void setFieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
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

	public File getTaxonomyFile() {
		return taxonomyFile;
	}

	public void setTaxonomyFile(File taxonomyFile) {
		this.taxonomyFile = taxonomyFile;
	}

}
