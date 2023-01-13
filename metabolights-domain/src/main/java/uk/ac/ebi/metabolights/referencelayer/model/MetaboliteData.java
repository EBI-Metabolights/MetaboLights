package uk.ac.ebi.metabolights.referencelayer.model;

import java.util.Collection;

/**
 * User: felix
 * Date: 10/01/23
 * Time: 07:26
 */


public class MetaboliteData {

	public MetaboliteData(){

	}
	public Collection<MetaboliteAssignmentDetails> rows;

	public Collection<MetaboliteAssignmentDetails> getRows() {
		return rows;
	}

	public void setRows(Collection<MetaboliteAssignmentDetails> rows) {
		this.rows = rows;
	}
}