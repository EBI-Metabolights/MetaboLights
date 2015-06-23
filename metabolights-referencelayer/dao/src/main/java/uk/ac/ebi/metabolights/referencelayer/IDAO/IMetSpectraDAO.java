/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 05/06/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Spectra;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.model.Spectra} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IMetSpectraDAO {


    Collection<Spectra> findByMetId(Long SpecId) throws DAOException;
    Spectra findBySpectraId(Long spectraId) throws DAOException;

    /**
     * Updates Spectra
     * @param spectra
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.Exception
     */
    void save(Spectra spectra, MetaboLightsCompound compound) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;


}
