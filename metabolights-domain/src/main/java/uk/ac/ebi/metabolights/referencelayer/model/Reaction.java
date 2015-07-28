package uk.ac.ebi.metabolights.referencelayer.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: conesa
 * Date: 28/07/15
 * Time: 09:51
 */
public class Reaction {

	private static final Logger logger = LoggerFactory.getLogger(Reaction.class);

	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}