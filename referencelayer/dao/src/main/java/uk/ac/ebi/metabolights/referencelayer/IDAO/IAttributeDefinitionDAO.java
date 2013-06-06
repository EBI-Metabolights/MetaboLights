package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.AttributeDefinition;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.AttributeDefinition} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IAttributeDefinitionDAO {


    AttributeDefinition findByAttributeDefinitionId(Long attributeDefinitionId) throws DAOException;

    Collection<AttributeDefinition> findAll() throws DAOException;


    /**
     * Updates the AttributeDefinition.
     * @param attributeDefinition
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void save(AttributeDefinition attributeDefinition) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void close() throws DAOException;
}
