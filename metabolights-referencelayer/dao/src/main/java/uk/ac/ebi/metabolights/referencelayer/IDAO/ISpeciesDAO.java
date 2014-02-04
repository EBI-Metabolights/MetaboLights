/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 31/01/14 09:31
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Reader for {@link Species} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface ISpeciesDAO {


    Species findBySpeciesId(Long SpeciesId) throws DAOException;
    Species findBySpeciesTaxon(String taxon) throws DAOException;
    Species findBySpeciesName(String name) throws DAOException;
    List<String> getAllNamesForAutoComplete() throws DAOException, SQLException;

    Collection<Species> findAll() throws DAOException;
	Collection<Species> findByGroupId(long groupId) throws DAOException;


    /**
     * Updates the Species.
     * @param Species
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.Exception
     */
    void save(Species Species) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;
}
