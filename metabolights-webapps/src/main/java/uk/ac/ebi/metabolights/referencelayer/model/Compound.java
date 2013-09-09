/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 06/09/13 21:26
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;
import uk.ac.ebi.metabolights.utils.GroupingUtil;

import java.util.Collection;
import java.util.Map;

public class Compound {

	private MetaboLightsCompound mc;
	private Entity chebiEntity;
    private Map<Species,Collection<MetSpecies>> species;



    public Compound(MetaboLightsCompound mc, Entity chebiEntity){
		this.mc = mc;
		this.chebiEntity= chebiEntity;
	}

	/**
	 * @return the mc
	 */
	public MetaboLightsCompound getMc() {
		return mc;
	}

	/**
	 * @return the chebiEntity
	 */
	public Entity getChebiEntity() {
		return chebiEntity;
	}

    public Map<Species,Collection<MetSpecies>> getSpecies(){

        // Grouping util not instantiated....
        if (species == null) {
            GroupingUtil speciesGU = new GroupingUtil(mc.getMetSpecies(),"getSpecies",MetSpecies.class);

            species = speciesGU.getGroupedCol();
        }

        return species;


    }

}
