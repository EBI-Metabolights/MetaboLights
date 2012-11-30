package uk.ac.ebi.metabolights.referencelayer.model;

import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;

public class Compound {
	
	private MetaboLightsCompound mc;
	private Entity chebiEntity;


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
}
