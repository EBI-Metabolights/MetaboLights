package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Spectra;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.Spectra} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface IMetSpectraDAO {


    Collection<Spectra> findByMetId(Long SpecId) throws DAOException;
    Spectra findBySpectraId(Long spectraId) throws DAOException;

    /**
     * Updates Spectra
     * @param spectra
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void save(Spectra spectra, MetaboLightsCompound compound) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;


}
