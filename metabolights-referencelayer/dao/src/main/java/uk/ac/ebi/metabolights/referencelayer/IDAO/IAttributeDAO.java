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

import uk.ac.ebi.metabolights.referencelayer.domain.*;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.Attribute} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IAttributeDAO {


    Collection<Attribute> findBySpectraId(Long spectraId) throws DAOException;

    Collection<Attribute> findByPathWayId(Long pathwayId) throws DAOException;

    /**
     * Updates the attribute.
     * @param attribute
     * @param spectra, spectra qualified by the attribute.
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void saveSpectraAttribute(Attribute attribute, Spectra spectra) throws DAOException;

    /**
     * Updates the attribute.
     * @param attribute
     * @param pathway, Pathway qualified by the attribute.
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void savePathwayAttribute(Attribute attribute, Identifier pathway) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;


}
