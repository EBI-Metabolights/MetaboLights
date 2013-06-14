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
