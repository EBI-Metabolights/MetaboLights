package uk.ac.ebi.metabolights.referencelayer.IDAO;

import java.util.Collection;

import uk.ac.ebi.metabolights.referencelayer.domain.Database;

/**
 * Reader for {@link MetaboLightsCompound} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IDatabaseDAO {


    Database findByDatabaseId(Long databaseId) throws DAOException;
    
    Collection<Database> findAll() throws DAOException;
    
    
    /**
     * Updates the Database.
     * @param Database
     * @throws DAOException
     */
    void save(Database database) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws MapperException
     */
    void close() throws DAOException;
}
