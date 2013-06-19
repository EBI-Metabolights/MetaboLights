package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.CrossReference;


/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.domain.CrossReference} objects from MetaboLights reference layer.
 * @author Pablo Conesa
 */

public interface ICrossReferenceDAO {


    CrossReference findByCrossReferenceId(Long crossReferenceId) throws DAOException;

    CrossReference findByCrossReferenceAccession(String accession) throws DAOException;



    /**
     * Updates the CrossReference.
     * @param crossReference
     * @throws DAOException
     */
    void save(CrossReference crossReference) throws DAOException;


    /**
     * Closes any open system resources allocated to this reader.
     * @throws DAOException
     */
    void close() throws DAOException;

}
