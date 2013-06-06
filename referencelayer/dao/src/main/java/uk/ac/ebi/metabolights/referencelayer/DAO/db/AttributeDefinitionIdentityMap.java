package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import uk.ac.ebi.metabolights.referencelayer.domain.AttributeDefinition;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 18/04/2013
 * Time: 09:40
 */
public class AttributeDefinitionIdentityMap {

    private static AttributeDefinitionIdentityMap instance = new AttributeDefinitionIdentityMap();

    private Map<Long,AttributeDefinition> map = new HashMap<Long, AttributeDefinition>();

    private AttributeDefinitionIdentityMap(){};

    public static AttributeDefinition getAttributeDefinition(Long id){
        return instance.map.get(id);
    }
    public static void addAttributeDefinition(AttributeDefinition attributeDefinition){
        instance.map.put(attributeDefinition.getId(), attributeDefinition);
    }
    public static void removeAttributeDefinition(AttributeDefinition attributeDefinition){
        instance.map.remove(attributeDefinition.getId());
    }

}
