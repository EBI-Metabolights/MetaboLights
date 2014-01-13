/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.util.Collection;

/**
 * Reader for {@link Database} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IDatabaseDAO {


    Database findByDatabaseId(Long databaseId) throws DAOException;
    Database findByDatabaseName(String databaseName) throws DAOException;

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
