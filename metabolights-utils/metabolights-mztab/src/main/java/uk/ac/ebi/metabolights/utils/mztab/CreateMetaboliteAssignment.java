/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 14:43
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.model.MZTabFile;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateMetaboliteAssignment {

    MzTabReader mzTabReader = new MzTabReader();
    MzTabUtils utils = new MzTabUtils();

    public MetaboliteAssignment createMetaboliteAssignment(String mzTabFileName, String mafFileName, String accessionNumber) {

        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();
        metaboliteAssignment.setMetaboliteAssignmentFileName(mafFileName);
        try {
            MZTabFile mzTab = mzTabReader.readMzTab(mzTabFileName);
            Collection<SmallMolecule> molecules = mzTab.getSmallMolecules();
            List<MetaboliteAssignmentLine> metaboliteAssignmentLines = new ArrayList<MetaboliteAssignmentLine>();

            for (SmallMolecule molecule : molecules){
                MetaboliteAssignmentLine assignmentLine = new MetaboliteAssignmentLine();
                //assignmentLine.setUnitId(molecule.getUnitId());
                assignmentLine.setChemicalFormula(molecule.getChemicalFormula());

                //If InChiKey is set, smiles will be empty (per spec) this will cause NPE if you call the getter
                //String inchi = "", smiles = "";
                //try { inchi = utils.listEntriesToString(molecule.getInchiKey()); } catch (Exception e){}
                //try { smiles = utils.listEntriesToString(molecule.getSmiles());  } catch (Exception e){}

                assignmentLine.setSmiles(molecule.getSmiles());
                assignmentLine.setInchi(molecule.getInchiKey());

                assignmentLine.setDescription(molecule.getDescription());
                assignmentLine.setMetaboliteIdentification(molecule.getDescription());
                //assignmentLine.setChemicalShift();      //TODO, NMR field
                //assignmentLine.setMultiplicity();       //TODO, NRM field
                assignmentLine.setMassToCharge(molecule.getExpMassToCharge().toString());
                //assignmentLine.setFragmentation();      //TODO, not in current version of mzTab
                assignmentLine.setModifications(utils.modificationsToString(molecule.getModifications()));
                assignmentLine.setCharge(molecule.getCharge().toString());
                assignmentLine.setRetentionTime(utils.doubleListToString(molecule.getRetentionTime()));
                assignmentLine.setTaxid(molecule.getTaxid().toString());
                assignmentLine.setSpecies(molecule.getSpecies());
                assignmentLine.setDatabaseIdentifier(utils.listEntriesToString(molecule.getIdentifier()));
                assignmentLine.setDatabase(molecule.getDatabase());
                assignmentLine.setReliability(molecule.getReliability().toString());
                //assignmentLine.setUri(utils.uriToString(molecule.getURI()));
                //assignmentLine.setSearchEngine(utils.paramListToString(molecule.getSearchEngine()));
                //assignmentLine.setSearchEngineScore(utils.paramListToString(molecule.getSearchEngineScore()));
                //assignmentLine.setSmallmoleculeAbundanceSub(utils.doubleToString(molecule.getAbundance(1)));               //TODO, Gets the first entry only
                //assignmentLine.setSmallmoleculeAbundanceStdevSub(utils.doubleToString(molecule.getAbundanceStdDev(1)));    //TODO, Gets the first entry only
                //assignmentLine.setSmallmoleculeAbundanceStdErrorSub(utils.doubleToString(molecule.getAbundanceStdErr(1))); //TODO, Gets the first entry only

                //TODO, set the sample columns
                //assignmentLine.setSampleMeasurements();


                //Add a row
                metaboliteAssignmentLines.add(assignmentLine);

            }

            metaboliteAssignment.setMetaboliteAssignmentLines(metaboliteAssignmentLines);

        }  catch (Exception e){
            e.printStackTrace();
        }

        return metaboliteAssignment;
    }


}
