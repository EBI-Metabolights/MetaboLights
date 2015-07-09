/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/9/13 4:37 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;
import uk.ac.ebi.metabolights.utils.GroupingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Compound {

	private MetaboLightsCompound mc;
	private Entity chebiEntity;
	@JsonIgnore
    private HashMap<Species,ArrayList<MetSpecies>> species;



	public Compound(){}
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

    public Map<Species,ArrayList<MetSpecies>> getSpecies(){

        // Grouping util not instantiated....
        if (species == null) {
            GroupingUtil speciesGU = new GroupingUtil(mc.getMetSpecies(),"getSpecies",MetSpecies.class);

            species = speciesGU.getGroupedCol();
        }

        return species;

    }

}
