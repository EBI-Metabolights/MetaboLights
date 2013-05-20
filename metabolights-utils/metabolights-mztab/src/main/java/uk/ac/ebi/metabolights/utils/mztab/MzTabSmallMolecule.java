package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

/**
 * Copyright (C) 2013 EMBL - European Bioinformatics Institute
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 11/04/2013
 * Time: 08:58
 */
public class MzTabSmallMolecule {

    private MzTabUtils utils = new MzTabUtils();

    public static String technologyMS  = "MS";
    public static String technologyNMR = "NMR";

    public SmallMolecule mafToMzTab(String mafLine[], String technology) throws MzTabParsingException {

        SmallMolecule molecule = new SmallMolecule();  //To store the new mzTab rows

        /*

            Columns:

                    0                   1                   2       3       4                           5               6                                       7       8           9           10                  11              12      13              14                                  15                          16                                  17
            NMR:    database_identifier	chemical_formula	inchi	smiles	metabolite_identification	chemical_shift	multiplicity                            taxid	species	    database	database_version	reliability	    uri	    search_engine	search_engine_score	                smallmolecule_abundance_sub	smallmolecule_abundance_stdev_sub	smallmolecule_abundance_std_error_sub

                    0                   1                   2       3       4                           5               6               7       8               9       10          11          12                  13              14      15              16                  17              18                          19                                  20
            MS:     database_identifier	chemical_formula	inchi	smiles	metabolite_identification	mass_to_charge	fragmentation	charge	retention_time	taxid	species	    database	database_version	reliability	    uri	    search_engine	search_engine_score	modifications	smallmolecule_abundance_sub	smallmolecule_abundance_stdev_sub	smallmolecule_abundance_std_error_sub


        */

        //Common between MS and NMR
        String database_identifier          = mafLine[0];
        String chemical_formula             = mafLine[1];
        String inchi                        = mafLine[2];
        String smiles                       = mafLine[3];
        String metabolite_identification    = mafLine[4];

        //Differs between NMR and MS
        String chemical_shift = null, mass_to_charge = null, multiplicity = null, fragmentation= null, charge = null, retention_time = null, reliability = null, taxid = null, species = null,
                database = null, database_version = null, search_engine = null, uri = null, search_engine_score = null, modifications = null, abundance_sub = null, abundance_stdev_sub = null, abundance_std_error_sub = null;

        if (technology.equals(technologyMS)){
            mass_to_charge          = mafLine[5];
            fragmentation           = mafLine[6];
            charge                  = mafLine[7];
            retention_time          = mafLine[8];
            taxid                   = mafLine[9];
            species                 = mafLine[10];
            database                = mafLine[11];
            database_version        = mafLine[12];
            reliability             = mafLine[13];
            uri                     = mafLine[14];
            search_engine           = mafLine[15];
            search_engine_score     = mafLine[16];
            modifications           = mafLine[17];
            abundance_sub           = mafLine[18];
            abundance_stdev_sub     = mafLine[19];
            abundance_std_error_sub = mafLine[20];

        } else {  //NMR
            chemical_shift          = mafLine[5];
            multiplicity            = mafLine[6];
            taxid                   = mafLine[7];
            species                 = mafLine[8];
            database                = mafLine[9];
            database_version        = mafLine[10];
            reliability             = mafLine[11];
            uri                     = mafLine[12];
            search_engine           = mafLine[13];
            search_engine_score     = mafLine[14];
            abundance_sub           = mafLine[15];
            abundance_stdev_sub     = mafLine[16];
            abundance_std_error_sub = mafLine[17];
        }


        //Add the Database identifiers
        molecule.setIdentifier(utils.stringToList(database_identifier));
        molecule.setChemicalFormula(chemical_formula);

        //The molecule expects one of InChI or SMILES, not both!
        if (inchi != null || inchi.isEmpty()){
            molecule.setSmiles(utils.stringToList(""));
            molecule.setInchiKey(utils.stringToList(inchi));
        } else {
            //SMILES
            molecule.setSmiles(utils.stringToList(smiles));
            molecule.setInchiKey(utils.stringToList(""));
        }

        molecule.setDescription(metabolite_identification);

        molecule.setMassToCharge(utils.stringToDouble(mass_to_charge).get(0)); //only one entry anyway
        molecule.setRetentionTime(utils.stringToDouble(retention_time));


        if (charge == null || charge.isEmpty())
            charge = "0";

        molecule.setCharge(Integer.parseInt(charge));



        //Well this is fun, we adopted PSI reliability scores but this is not adopted in mzTAB
        if (reliability == null || reliability.isEmpty()){
            reliability = "3";        // Reliability must only be 1 (good), 2 (medium), and 3 (bad).
        } else {
            if (reliability.contains("1:") || reliability.contains("2:")) // "1:poor reliability" or "2:less poor reliability"
                reliability = "3";

            if (reliability.contains("3:")) // "3:medium reliability"
                reliability = "2";

            if (reliability.contains("4:") || reliability.contains("5:")) // "4:good reliability" or "5:very good reliability"
                reliability = "1";

        }

        molecule.setReliability(Integer.parseInt(reliability));

        if (abundance_sub == null || abundance_sub.isEmpty())
            abundance_sub = "0.0";

        if (abundance_stdev_sub == null || abundance_stdev_sub.isEmpty())
            abundance_stdev_sub = "0.0";

        if (abundance_std_error_sub == null ||abundance_std_error_sub.isEmpty())
            abundance_std_error_sub = "0.0";

        molecule.setAbundance(1,
                Double.parseDouble(abundance_sub),
                Double.parseDouble(abundance_stdev_sub),
                Double.parseDouble(abundance_std_error_sub) );

        return molecule;

    }

}
