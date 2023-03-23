package uk.ac.ebi.metabolights.search.service;

/**
 * User: conesa
 * Date: 15/06/15
 * Time: 16:35
 */
public class Booster {


	private String fieldName;
	private float boost;

	public Booster(){};

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}
}