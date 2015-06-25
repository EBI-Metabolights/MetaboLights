/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 13/11/13 13:26
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.model.SpeciesGroup;

import java.io.IOException;
import java.util.Collection;

public interface ISpeciesGroupDAO {

    public SpeciesGroup getById(Long id) throws DAOException;
    public SpeciesGroup getByName(String speciesName) throws DAOException;
    public Collection<SpeciesGroup> getAll() throws DAOException, IOException;
    public void save(SpeciesGroup speciesGroup) throws DAOException;
    public void delete(SpeciesGroup speciesGroup) throws DAOException;
}
