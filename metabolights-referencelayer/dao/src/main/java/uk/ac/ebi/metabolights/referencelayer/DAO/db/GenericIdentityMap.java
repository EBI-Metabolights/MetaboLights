/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 23/04/13 09:42
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import uk.ac.ebi.metabolights.referencelayer.domain.Identifier;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 18/04/2013
 * Time: 09:40
 */
public class GenericIdentityMap<E extends Identifier> {

    private Map<Long,E> map;

    public GenericIdentityMap(){
		map = new HashMap<Long,E>();
	};

    public E getEntity(Long id){
        return (E) map.get(id);
    }
    public void addEntity( E entity ){
        map.put(entity.getId(),entity);
    }
    public void removeEntity(E db){
        map.remove(db.getId());
    }

}
