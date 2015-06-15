package uk.ac.ebi.metabolights.search.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: conesa
 * Date: 15/06/15
 * Time: 16:35
 */
public class Booster {

	private static final Logger logger = LoggerFactory.getLogger(Booster.class);

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