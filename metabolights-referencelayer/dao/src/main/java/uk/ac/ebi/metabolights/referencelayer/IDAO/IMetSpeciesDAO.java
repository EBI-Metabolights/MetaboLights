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

import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Species;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.model.MetSpecies} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IMetSpeciesDAO {


    Collection<MetSpecies> findByMetId(Long MetId) throws DAOException;

    /**
     * Updates the Species.
     * @param metSpecies
     * @throws DAOException
     */
    void save(MetSpecies metSpecies, MetaboLightsCompound compound) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void close() throws DAOException;


}
