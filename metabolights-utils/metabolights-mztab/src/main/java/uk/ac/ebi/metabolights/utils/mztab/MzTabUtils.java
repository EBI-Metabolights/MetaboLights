/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 11/09/13 15:20
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class MzTabUtils {



    public List<String> stringToList(String strValue){
        List<String> list = new ArrayList<String>();
        list.add(strValue);
        return list;
    }

    public List<Double> stringToDouble(String strValue){
        List<Double> doubleList = new ArrayList<Double>();

        if (strValue == null)
            strValue = "0";

        double value = Double.parseDouble(strValue);
        doubleList.add(value);

        return doubleList;
    }

    public File[] findMafFile(String folderName){
        File dir = new File(folderName);      //Folder that holds the MAF

        File[] matches = dir.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.startsWith("m_") && name.endsWith(".tsv");
            }
        });

        return matches;

    }

}
