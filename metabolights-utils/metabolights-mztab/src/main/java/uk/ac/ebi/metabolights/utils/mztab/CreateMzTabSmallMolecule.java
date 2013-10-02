/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 02/10/13 14:17
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.Modification;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.util.ArrayList;
import java.util.List;

public class CreateMzTabSmallMolecule {

    private MzTabUtils utils = new MzTabUtils();

    public SmallMolecule convertToMzTab(MetaboliteAssignmentLine metLine) throws MzTabParsingException {
        SmallMolecule molecule = new SmallMolecule();  //To store the new mzTab rows

        try {

            String inchi = null, smiles = null, modificaiton = null, smallAbuSub = null, smallAbuDevSub = null, smallAbuDevSubErr = null;

            molecule.setIdentifier(utils.stringToList(metLine.getDatabaseIdentifier()));
            molecule.setDescription(metLine.getDescription());
            molecule.setChemicalFormula(metLine.getChemicalFormula());
            molecule.setCharge(utils.convertPosNegToInt(metLine.getCharge()));
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

            if (inchi != null && !inchi.isEmpty()){
                molecule.setSmiles(utils.stringToList(""));
                molecule.setInchiKey(utils.stringToList(utils.inchiToinchiKey(inchi)));
            } else {
                //SMILES
                molecule.setSmiles(utils.stringToList(smiles));
                molecule.setInchiKey(utils.stringToList(""));
            }

            smallAbuSub = metLine.getSmallmoleculeAbundanceSub();
            smallAbuDevSub = metLine.getSmallmoleculeAbundanceStdevSub();
            smallAbuDevSubErr = metLine.getSmallmoleculeAbundanceStdErrorSub();

            //Abundance, the ML plugin only supports 1 set of abundance data so we use the value 1
            //if (utils.notNullOrEmpty(smallAbuSub) && utils.notNullOrEmpty(smallAbuDevSub) && utils.notNullOrEmpty(smallAbuDevSubErr)) {
            //TODO, check why this is not working as the spec allows this being empty
                molecule.setAbundance(1,
                        utils.StrintToDouble(smallAbuSub),
                        utils.StrintToDouble(smallAbuDevSub),
                        utils.StrintToDouble(smallAbuDevSubErr));
            //}


            //TODO,
            // private List<SpecRef> specRef;

            //Modification list
            modificaiton = metLine.getModifications();
            if (utils.notNullOrEmpty(modificaiton)){
                try {
                    Modification modification = new Modification("CHEMMOD:"+modificaiton);
                    List<Modification> modifications = new ArrayList<Modification>();
                    modifications.add(modification);
                    molecule.setModifications(modifications);
                } catch (Exception e) {
                    //TODO, should we notify the user or simply ignore?
                }

            }

        } catch (MzTabParsingException e) {
            e.printStackTrace();
        }

        return molecule;

    }


}
