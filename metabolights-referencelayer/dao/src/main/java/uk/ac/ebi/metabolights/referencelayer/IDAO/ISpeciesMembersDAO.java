/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 13/11/13 14:19
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.IDAO;

import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesMembers;

import java.util.Collection;
import java.util.List;

public interface ISpeciesMembersDAO {

    public SpeciesMembers getById(Long id) throws DAOException;
    public SpeciesMembers getByTaxon(String taxon) throws DAOException;
    public Collection<SpeciesMembers> getAllBySpeciesGroup(Long speciesGroupId) throws DAOException;


	public void save(SpeciesMembers speciesMembers) throws DAOException;
    public void delete(SpeciesMembers speciesMembers) throws DAOException;
	/**
	 * Closes any open system resources allocated to this reader.
	 * @throws DAOException
	 */
	void close() throws DAOException;

}
