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
