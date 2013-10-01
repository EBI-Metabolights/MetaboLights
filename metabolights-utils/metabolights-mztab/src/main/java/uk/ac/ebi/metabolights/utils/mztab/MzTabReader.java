/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 01/10/13 14:51
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.pride.jmztab.MzTabFile;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;

import java.io.File;

public class MzTabReader {

    public MzTabFile readMzTab(String fileName){
        File inputFile = new File(fileName);
        MzTabFile mzTabFile = new MzTabFile();

        try {
            mzTabFile = new MzTabFile(inputFile);
        } catch (MzTabParsingException e) {
            e.printStackTrace();  //TODO
        }

        return mzTabFile;
    }


}
