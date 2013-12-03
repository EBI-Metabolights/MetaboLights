/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.importer;


import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesMembersDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesMembers;
import uk.ac.ebi.metabolights.species.core.tools.Grouper;
import uk.ac.ebi.metabolights.species.core.tools.IPNIParentSearcher;
import uk.ac.ebi.metabolights.species.core.tools.WoRMSPArentSearcher;
import uk.ac.ebi.metabolights.species.model.Taxon;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.*;


/**
 * User: conesa
 * Date: 18/06/2013
 * Time: 11:19
 */
public class SpeciesUpdater {

    public static final String NEWT_ONTOLOGY = "NEWT";
    Logger LOGGER = Logger.getLogger(SpeciesUpdater.class);
    OntologyLookUpService ols = new OntologyLookUpService();

	SpeciesDAO speciesDAO;
	SpeciesMembersDAO speciesMemberDAO;

	Grouper grouper;
	Collection<SpeciesMembers> speciesMemberses;

	public class UpdateOptions
	{
		public static final int NEWT = 0x1;
		public static final int GROUPS= 0x1<<1;
//		public static final int THREE = 0x1<<2;
//		public static final int FOUR = 0x1<<3;
//		public static final int FIVE = 0x1<<4;

		// COMBOS...
		public static final int NEWT_AND_GROUP = NEWT + GROUPS;
		public static final int ALL = NEWT + GROUPS;
	}

	private int updateOptions = UpdateOptions.ALL;

    public SpeciesUpdater(Connection connection){

        try {
            speciesDAO = new SpeciesDAO(connection);
			speciesMemberDAO = new SpeciesMembersDAO(connection);


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

	public int getUpdateOptions() {
		return updateOptions;
	}

	public void setUpdateOptions(int updateOptions) {
		this.updateOptions = updateOptions;
	}

	public void UpdateSpeciesInformation(){

        // Get all the species from MetaboLights...
        try {
            Set<Species> speciesList = speciesDAO.findWithoutSpeciesMember();

			UpdateSpeciesInformation(speciesList);


		} catch (DAOException e) {
            LOGGER.error("Can't update species information");
        }



    }

	public Species UpdateSpeciesInformation(String name ){

		// Get all the species from MetaboLights...
		try {
			Species specie = speciesDAO.findBySpeciesName(name);

			return UpdateSpeciesInformation(specie);

		} catch (DAOException e) {
			LOGGER.warn("Can't get species by its name: " + name, e);
			return null;
		}

	}

	public Species UpdateSpeciesInformation(Species specie) {

		Set<Species> specieses =  new HashSet<Species>(Arrays.asList(new Species[]{specie}));
		UpdateSpeciesInformation(specieses);

		return specie;
	}


	private void UpdateSpeciesInformation(Set<Species> speciesList) {
		// For each species..
		for (Species sp : speciesList){

			// Update species information
			UpdateSpecieInformation((Species) sp);

		}
	}

	public void UpdateSpecieInformation(long speciesId){

        try {
            Species sp = speciesDAO.findBySpeciesId(speciesId);

            if (sp != null){

                UpdateSpecieInformation((Species) sp);

            } else {
                LOGGER.error("Can't find species by id " + speciesId);
            }

        } catch (DAOException e) {
            LOGGER.error("Error trying to find species by id " + speciesId + ":\n" + e.getMessage());
        }


    }

	private void UpdateSpecieInformation(Species sp){


		try {

			// Try first NEWT approach, since GROUP depends on NEWT info.
			// If newt option active...
			if ((updateOptions & UpdateOptions.NEWT) == UpdateOptions.NEWT){
				UpdateSpecieWithNewt(sp);
			}

			// Now update groups
			if ((updateOptions & UpdateOptions.GROUPS) == UpdateOptions.GROUPS){
				UpdateSpecieGroup(sp);
			}


			speciesDAO.save(sp);


		} catch (DAOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

	private void UpdateSpecieGroup(Species sp) {

		// If it has a speciesMember id we will not touch it.
		if (sp.getSpeciesMemberId() !=0) {
			LOGGER.info ("Specie " + sp.getSpecies() + "(ID:" +sp.getId() + ") has an SpeciesMember asociated, we will not update it.");
			return;
		}

		// If there is no taxon we can't do anything...
		if (sp.getTaxon() == null || sp.getTaxon().isEmpty()) {
			LOGGER.info ("Specie " + sp.getSpecies() + "(ID:" +sp.getId() + ") has no taxon, we can't fint a group without it.");
			return;
		}

		setUpGroupUpdate();


		Taxon taxon = new Taxon(sp.getTaxon(),sp.getSpecies(), sp.getDescription(),null);

		Taxon group = grouper.getGroupFromTaxon(taxon);

		// Get the Species member with that group id...
		SpeciesMembers spm = getSpeciesMemberFromTaxon(group);

		if (spm != null){
			sp.setSpeciesMemberId(spm.getId());
		} else {

			sp.setSpeciesMemberId(0);

			LOGGER.warn("SpeciesMember not found for " + sp.getTaxon() + " - " + sp.getId());
		}

	}

	private SpeciesMembers getSpeciesMemberFromTaxon(Taxon speciesMember){

		if (speciesMember == null) return null;

		for (SpeciesMembers spm : speciesMemberses){

			if (spm.getTaxon().equals(speciesMember.getId())){
				return spm;
			}
		}

		return null;

	}

	private void setUpGroupUpdate(){

		if (grouper == null){

			LOGGER.info("Setting up Grouper");

			// Instantiate a new Grouper
			grouper = new Grouper();

			// Get taxons for group...
			try {

				// Add the WoRMS parent Searcher.
				grouper.getParentSearchers().add(new WoRMSPArentSearcher());

				// Add the IPNI parent Searcher.
				grouper.getParentSearchers().add(new IPNIParentSearcher());


				speciesMemberses = speciesMemberDAO.getAll();

				List<Taxon> taxons = convertSpeciesMemebersToTaxonList();

				grouper.setTaxonGroups(taxons);


			} catch (DAOException e) {
				LOGGER.error("Can't get species member list", e);

			} catch (AxisFault axisFault){
				LOGGER.error("Can't get instantiate WoRM Client" , axisFault );
			}


		}

	}

	private List<Taxon> convertSpeciesMemebersToTaxonList() {

		ArrayList<Taxon> taxons = new ArrayList<Taxon>();

		LOGGER.debug("Converting SpeciesMemebers List to a Taxon list");

		for (SpeciesMembers spm :speciesMemberses){

			Taxon taxon = convertSpeciesMember2Taxon(spm);

			taxons.add(taxon);

			LOGGER.debug("SpeciesMember " + spm.getTaxon() + " converted to Taxon.");

		}

		return taxons;
	}

	private Taxon convertSpeciesMember2Taxon(SpeciesMembers spm) {
		return new Taxon(spm.getTaxon(), spm.getTaxon(), "", "");
	}


	private void UpdateSpecieWithNewt(Species sp)  {


		// Look up for the species information in OLS.
		try {

			//If we got the taxon we will update the description
			if (sp.getTaxon() != null && !sp.getTaxon().isEmpty()){

					LOGGER.info ("Specie " + sp.getSpecies() + "(ID:" +sp.getId() + ") has taxon, we will update the description using the taxon.");
					String name = ols.getTermName (sp.getTaxon(), NEWT_ONTOLOGY);

					// If returned value is the same as taxon....it hasn't worked!. Nothing found.
					if(name.equals(sp.getTaxon())){

						LOGGER.info ("Nothing found in OLS for " + sp.getTaxon() + "(ID:" +sp.getId() + ").");

					} else {
						LOGGER.debug ("Updating species name of " + sp.getTaxon() + "(ID:" +sp.getId() + ") with " + name + " (was " + sp.getSpecies() + ").");
						sp.setSpecies(name);
					}


			} else if (sp.getSpecies() != null && !sp.getSpecies().isEmpty()){

				Map<String,String> terms = ols.getTermsByName(sp.getSpecies(), NEWT_ONTOLOGY);

				// If there's anything
				if (terms.size() !=0){

					Map.Entry entry = terms.entrySet().iterator().next();

					String taxon = NEWT_ONTOLOGY + ":" + entry.getKey();
					sp.setTaxon(taxon);

					LOGGER.debug ("Specie " + sp.getSpecies() + "(ID:" +sp.getId() + ") taxon updated with " + taxon);

				} else {
					LOGGER.info ("Nothing found for " + sp.getSpecies() + " in " + NEWT_ONTOLOGY + ".");
				}
			} else {
				LOGGER.warn("Nothing to update. No species name, no taxon...weird! Species id:" + sp.getId());
			}

        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	public Grouper getGrouper() {
		return grouper;
	}

	public void setGrouper(Grouper grouper) {
		this.grouper = grouper;
	}


}
