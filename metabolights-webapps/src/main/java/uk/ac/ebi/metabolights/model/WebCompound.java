package uk.ac.ebi.metabolights.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.utils.GroupingUtil;

import java.util.Collection;
import java.util.Map;

/**
 * User: conesa
 * Date: 16/07/15
 * Time: 11:03
 */
public class WebCompound extends Compound {

	private Map<Species,Collection<MetSpecies>> species;


	private static final Logger logger = LoggerFactory.getLogger(WebCompound.class);

	public WebCompound (Compound compound){

		this.setMc(compound.getMc());
		this.setChebiEntity(compound.getChebiEntity());

	}

	public Map<Species,Collection<MetSpecies>> getSpecies(){

		// Grouping util not instantiated....
		if (this.species == null) {
			GroupingUtil speciesGU = new GroupingUtil(this.getMc().getMetSpecies(),"getSpecies",MetSpecies.class);

			species = speciesGU.getGroupedCol();
		}

		return species;


	}

}