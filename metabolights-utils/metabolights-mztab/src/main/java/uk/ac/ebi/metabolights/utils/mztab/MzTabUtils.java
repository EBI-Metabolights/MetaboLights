package uk.ac.ebi.metabolights.utils.mztab;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2013 EMBL - European Bioinformatics Institute
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 11/04/2013
 * Time: 08:58
 */
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
