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

import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Pathway;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IMetPathwayDAO {


    Collection<Pathway> findByMetId(Long MetId) throws DAOException;
    Pathway findByPathwayId(Long pathwayId) throws DAOException;

    /**
     * Updates the Pathway.
     * @param pathway
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void save(Pathway pathway, MetaboLightsCompound compound) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;


}
