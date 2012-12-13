package uk.ac.ebi.metabolights.referencelayer.IDAO;

import java.util.Collection;

import uk.ac.ebi.metabolights.referencelayer.domain.Database;
import uk.ac.ebi.metabolights.referencelayer.domain.RefEntry;

/**
 * Reader for {@link RefEntry} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IRefEntryDAO {


    RefEntry findByRefEntryId(Long refEntryId) throws DAOException;
    
    Collection<RefEntry> findChildrenOf(long parentId) throws DAOException;
    
    
    /**
     * Updates the RefEntry.
     * @param RefEntry
     * @throws DAOException
     */
    void save(RefEntry refEntry) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws MapperException
     */
    void close() throws DAOException;
}
