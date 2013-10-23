/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 08/07/13 08:56
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

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
    AttributeDefinition findByAttributeDefinitionName(String attributeDefinitionName) throws DAOException;

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
