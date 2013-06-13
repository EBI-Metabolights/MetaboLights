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
    void savePathwayAttribute(Attribute attribute, Pathway pathway) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;


}
