/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 14:11
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.model.MZTabColumnFactory;
import uk.ac.ebi.pride.jmztab.model.Metadata;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

public class CreateMzTabSmallMolecule {

    private MzTabUtils utils = new MzTabUtils();

    public SmallMolecule convertToMzTab(MetaboliteAssignmentLine metLine, Metadata metadata, MZTabColumnFactory factory ) {

        SmallMolecule molecule = new SmallMolecule(factory, metadata);   //To store the new mzTab rows

        try {

            //Due to version changes there will only be one of these values present
            String dbIdent = metLine.getIdentifier();
            if (dbIdent == null || dbIdent.isEmpty() || dbIdent.equals(""))
                dbIdent = metLine.getDatabaseIdentifier();
            molecule.setIdentifier(dbIdent);

            molecule.setChemicalFormula(utils.makeSureNotEmpty(metLine.getChemicalFormula()));
            molecule.setSmiles(utils.makeSureNotEmpty(metLine.getSmiles()));
            molecule.setInchiKey(utils.inchiToinchiKey(metLine.getInchi()));

            String description = metLine.getMetaboliteIdentification();
            if (description == null | description.isEmpty() || description.equals(""))
                description = metLine.getDescription();
            molecule.setDescription(description);

            molecule.setExpMassToCharge(utils.stringToDouble(metLine.getMassToCharge()));
            molecule.setCalcMassToCharge(utils.stringToDouble(metLine.getMassToCharge()));
            molecule.setCharge(utils.convertPosNegToInt(metLine.getCharge()));
            molecule.setRetentionTime(utils.makeSureNotEmpty(metLine.getRetentionTime()));
            molecule.setTaxid(utils.makeSureNotEmpty(metLine.getTaxid()));
            molecule.setSpecies(utils.makeSureNotEmpty(metLine.getSpecies())); //TODO, convert from ontology term
            molecule.setDatabase(utils.makeSureNotEmpty(metLine.getDatabase()));
            molecule.setDatabaseVersion(utils.makeSureNotEmpty(metLine.getDatabaseVersion()));
            molecule.setSpectraRef(utils.makeSureNotEmpty(""));      //TODO, believe this will have to exist to pass validation
            molecule.setSearchEngine(utils.makeSureNotEmpty(metLine.getSearchEngine()));
            molecule.setBestSearchEngineScore(utils.makeSureNotEmpty(metLine.getSearchEngineScore()));
            molecule.setModifications(utils.makeSureNotEmpty(metLine.getModifications()));
            //molecule.setReliability(metLine.getReliability());                 //TODO, not a standard header
            //molecule.setURI(metLine.getUri());  //TODO, not a standard header

        } catch (Exception e) {
            e.printStackTrace();
        }

        return molecule;

    }


}
