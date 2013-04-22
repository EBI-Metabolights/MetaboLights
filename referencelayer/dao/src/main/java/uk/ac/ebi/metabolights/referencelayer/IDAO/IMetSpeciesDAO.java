package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.util.Collection;

/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies} objects from MetaboLights reference layer.
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
