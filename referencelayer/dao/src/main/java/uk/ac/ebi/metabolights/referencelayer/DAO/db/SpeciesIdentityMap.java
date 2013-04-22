package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 18/04/2013
 * Time: 09:40
 */
public class SpeciesIdentityMap {

    private static SpeciesIdentityMap instance = new SpeciesIdentityMap();

    private Map<Long,Species> map = new HashMap<Long,Species>();

    private SpeciesIdentityMap(){};

    public static Species getSpecies(Long id){
        return instance.map.get(id);
    }
    public static void addSpecies(Species Species){
        instance.map.put(Species.getId(),Species);
    }
    public static void removeSpecies(Species db){
        instance.map.remove(db.getId());
    }

}
