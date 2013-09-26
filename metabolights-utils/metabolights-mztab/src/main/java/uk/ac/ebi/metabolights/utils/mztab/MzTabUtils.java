/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 25/09/13 16:06
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;


import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.model.ParamList;
import uk.ac.ebi.pride.jmztab.model.Param;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MzTabUtils {


    public boolean processLine(MetaboliteAssignmentLine metLine){
        if (metLine.getDatabaseIdentifier() == null || metLine.getDatabaseIdentifier().isEmpty() ||
                metLine.getMetaboliteIdentification() == null || metLine.getMetaboliteIdentification().isEmpty())
            return false;
        else
            return true;
    }

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

    public Double StrintToDouble(String strValue){
        if (strValue == null || strValue.isEmpty())
            strValue = "0.0";

        return Double.parseDouble(strValue);
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

    public int stringToInt(String stringValue){
        if (stringValue == null || stringValue.isEmpty())
            stringValue = "0";

        return Integer.parseInt(stringValue);
    }


    public URI stringToUri(String strURI){
        URI uri = null;
        try {
            uri = new URI(strURI);
        } catch (URISyntaxException e) {
            return uri;
        }

        return uri;
    }

    public Integer convertMSItoPSIreliability(String reliability){

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

        return Integer.parseInt(reliability);
    }


    public ParamList stringToParamList(String strValue){
        ParamList paramList = new ParamList();
        Param param = new Param(strValue);
        paramList.add(param);

        return paramList;
    }

}
