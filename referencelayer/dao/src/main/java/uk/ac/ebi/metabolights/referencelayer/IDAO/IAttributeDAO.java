package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.Attribute;
import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Spectra;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.Attribute} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IAttributeDAO {


    Collection<Attribute> findBySpectraId(Long SpectraId) throws DAOException;

    /**
     * Updates the attribute.
     * @param attribute
     * @param spectra, spectra qualified by the attribute.
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void saveSpectraAttribute(Attribute attribute, Spectra spectra) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;


}
