package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.util.Collection;

/**
 * Reader for {@link Species} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface ISpeciesDAO {


    Species findBySpeciesId(Long SpeciesId) throws DAOException;
    Species findBySpeciesTaxon(String taxon) throws DAOException;
    Species findBySpeciesName(String name) throws DAOException;
    
    Collection<Species> findAll() throws DAOException;
    
    
    /**
     * Updates the Species.
     * @param Species
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
     */
    void save(Species Species) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;
}
