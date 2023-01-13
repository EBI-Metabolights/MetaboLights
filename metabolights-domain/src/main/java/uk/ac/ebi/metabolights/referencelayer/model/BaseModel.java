package uk.ac.ebi.metabolights.referencelayer.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Organism;

import java.util.Collection;

/**
 * User: felix
 * Date: 10/01/23
 * Time: 07:26
 */


public class BaseModel {

	private static final Logger logger = LoggerFactory.getLogger(BaseModel.class);

	private Long id = null;
	private Collection<Organism> organism;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Organism> getOrganism() {
		return organism;
	}

	public void setOrganism(Collection<Organism> organism) {
		this.organism = organism;
	}
}