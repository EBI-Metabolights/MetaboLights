/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 23/04/13 09:42
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;

import java.util.Set;

/**
 * Reader for {@link MetaboLightsCompound} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IMetaboLightsCompoundDAO {


    MetaboLightsCompound findByCompoundName(String name) throws DAOException;

    MetaboLightsCompound findByCompoundAccession(String accession) throws DAOException;

    MetaboLightsCompound findByCompoundId(Long compoundId) throws DAOException;

    Set<MetaboLightsCompound> getAllCompounds() throws DAOException;

    boolean doesCompoundExists(Long compoundId) throws DAOException;

    /**
     * Updates the MetaboLightsCompound.
     * @param compound
     * @throws DAOException
     */
    void save(MetaboLightsCompound compound) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;
}
