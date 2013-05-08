package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import uk.ac.ebi.metabolights.referencelayer.domain.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 18/04/2013
 * Time: 09:40
 */
public class CrossReferenceIdentityMap {

    private static CrossReferenceIdentityMap instance = new CrossReferenceIdentityMap();

    private Map<Long,CrossReference> map = new HashMap<Long,CrossReference>();

    private CrossReferenceIdentityMap(){};

    public static CrossReference getCrossReference(Long id){
        return instance.map.get(id);
    }
    public static void addCrossReference(CrossReference crossReference){
        instance.map.put(crossReference.getId(), crossReference);
    }
    public static void removeCrossReference(CrossReference crossReference){
        instance.map.remove(crossReference.getId());
    }

}
