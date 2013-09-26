/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 25/09/13 15:44
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

public class MzTabSmallMolecule {

    private MzTabUtils utils = new MzTabUtils();

    public SmallMolecule convertToMzTab(MetaboliteAssignmentLine metLine) throws MzTabParsingException {
        SmallMolecule molecule = new SmallMolecule();  //To store the new mzTab rows

        try {

            String inchi = null, smiles = null, charge = null, reliability = null,
                    abundance_sub = null, abundance_stdev_sub = null, abundance_std_error_sub = null;

            molecule.setIdentifier(utils.stringToList(metLine.getDatabaseIdentifier()));
            molecule.setDescription(metLine.getDescription());
            molecule.setChemicalFormula(metLine.getChemicalFormula());
            molecule.setCharge(utils.stringToInt(metLine.getCharge()));
            molecule.setTaxid(utils.stringToInt(metLine.getTaxid()));
            molecule.setSpecies(metLine.getSpecies()); //TODO, convert from ontology term
            molecule.setDatabase(metLine.getDatabase());
            molecule.setDatabaseVersion(metLine.getDatabaseVersion());
            molecule.setMassToCharge(utils.stringToDouble(metLine.getMassToCharge()).get(0));   //only one entry anyway
            molecule.setRetentionTime(utils.stringToDouble(metLine.getRetentionTime()));
            molecule.setReliability(utils.convertMSItoPSIreliability(metLine.getReliability()));
            molecule.setUri(utils.stringToUri(metLine.getUri()));
            molecule.setSearchEngine(utils.stringToParamList(metLine.getSearchEngine()));
            molecule.setSearchEngineScore(utils.stringToParamList(metLine.getSearchEngineScore()));

            //The molecule expects one of InChI or SMILES, not both!
            inchi = metLine.getInchi();
            smiles = metLine.getSmiles();

            if (inchi != null || inchi.isEmpty()){
                molecule.setSmiles(utils.stringToList(""));
                molecule.setInchiKey(utils.stringToList(inchi));
            } else {
                //SMILES
                molecule.setSmiles(utils.stringToList(smiles));
                molecule.setInchiKey(utils.stringToList(""));
            }

            //Abundance
            molecule.setAbundance(1,
                    utils.StrintToDouble(metLine.getSmallmoleculeAbundanceSub()),
                    utils.StrintToDouble(metLine.getSmallmoleculeAbundanceStdevSub()),
                    utils.StrintToDouble(metLine.getSmallmoleculeAbundanceStdErrorSub()));


            //TODO,
            // private List<SpecRef> specRef;
            // private List<Modification> modifications;

        } catch (MzTabParsingException e) {
            e.printStackTrace();
        }

        return molecule;

    }


}
