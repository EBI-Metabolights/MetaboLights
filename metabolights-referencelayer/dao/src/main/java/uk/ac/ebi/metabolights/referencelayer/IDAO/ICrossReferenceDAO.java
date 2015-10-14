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

import uk.ac.ebi.metabolights.referencelayer.model.CrossReference;


/**
 * Reader for {@link uk.ac.ebi.metabolights.referencelayer.model.CrossReference} objects from MetaboLights reference layer.
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
