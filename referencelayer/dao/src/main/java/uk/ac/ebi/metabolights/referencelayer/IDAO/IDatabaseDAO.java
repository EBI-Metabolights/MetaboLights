package uk.ac.ebi.metabolights.referencelayer.IDAO;

import java.util.Collection;

import uk.ac.ebi.metabolights.referencelayer.domain.Database;

/**
 * Reader for {@link Database} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IDatabaseDAO {


    Database findByDatabaseId(Long databaseId) throws DAOException;
    
    Collection<Database> findAll() throws DAOException;
    
    
    /**
     * Updates the Database.
     * @param database
     * @throws DAOException
     */
    void save(Database database) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;
}
