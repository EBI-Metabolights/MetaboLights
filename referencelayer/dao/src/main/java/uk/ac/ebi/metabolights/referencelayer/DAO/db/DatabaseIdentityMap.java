package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 18/04/2013
 * Time: 09:40
 */
public class DatabaseIdentityMap  {

    private static DatabaseIdentityMap instance = new DatabaseIdentityMap();

    private Map<Long,Database> map = new HashMap<Long,Database>();

    private DatabaseIdentityMap(){};

    public static Database getDatabase(Long id){
        return instance.map.get(id);
    }
    public static void addDatabase(Database database){
        instance.map.put(database.getId(),database);
    }
    public static void removeDatabase(Database db){
        instance.map.remove(db.getId());
    }

}
