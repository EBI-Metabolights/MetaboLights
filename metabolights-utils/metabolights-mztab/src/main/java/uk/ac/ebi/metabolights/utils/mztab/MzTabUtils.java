/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 14:44
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;


import net.sf.jniinchi.JniInchiException;
import net.sf.jniinchi.JniInchiOutputKey;
import net.sf.jniinchi.JniInchiWrapper;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.model.Modification;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//import uk.ac.ebi.pride.jmztab.model.ParamList;

public class MzTabUtils {

    /**
     * Converts InChI to InChiKey
     * @param inchi
     * @return InChiKey
     */
    public String inchiToinchiKey(String inchi)  {
        JniInchiOutputKey output = null;
        if (inchi == null || inchi.isEmpty())
            return null;

        if (!inchi.startsWith("InChI="))
            return null;

        try {
            output = JniInchiWrapper.getInchiKey(inchi);
        } catch (JniInchiException e) {
            e.printStackTrace();
        }
        return output.getKey();
    }

    /**
     * Returns a comma separated list of modifications (Modification)
     * @param modifications
     * @return String
     */
    public String modificationsToString(List<Modification> modifications){
        String modStr = "";

        if (modifications == null)
             return modStr;

        for (Modification modification : modifications){

            if (!modStr.isEmpty())
                modStr = modStr + ",";

            modStr = modStr + modification.toString();
        }

        return modStr;
    }

    public boolean processLine(MetaboliteAssignmentLine metLine){

        if ( (notNullOrEmpty(metLine.getDatabaseIdentifier()) || notNullOrEmpty(metLine.getDatabaseIdentifier()) )
                && notNullOrEmpty(metLine.getMetaboliteIdentification()))
            return true;

        //if (notNullOrEmpty(metLine.getMetaboliteIdentification()))
        //    return true;

        return false;

    }

    public boolean notNullOrEmpty(String metStr){
        if (metStr == null || metStr.isEmpty() || metStr.equals(""))
            return false;
        else
            return true;
    }

    public String makeSureNotEmpty(String metStr){
        if (metStr == null || metStr.isEmpty() || metStr.equals(""))
            return null;
        else
            return metStr;
    }

    public List<String> stringToList(String strValue){
        List<String> list = new ArrayList<String>();
        if (!strValue.isEmpty())
            list.add(strValue);

        return list;
    }

    /**
     * Returns a pipeline (|) separated list of strings
     * @param strings
     * @return
     */
    public String listEntriesToString(List<String> strings){
        String values = "";

        if (strings == null)
            return values;

        for (String entry : strings){
            if (!values.isEmpty())
                values = values + "|";

            values = values + entry;
        }

        return values;
    }

    /**
     * Converts a String to Double
     * @param strValue
     * @return Double
     */
    public List<Double> stringToDoubleList(String strValue){
        List<Double> doubleList = new ArrayList<Double>();

        if (strValue == null || strValue.trim().isEmpty())
            strValue = "0";

        double value = Double.parseDouble(strValue);
        doubleList.add(value);

        return doubleList;
    }

    public Double stringToDouble(String strValue){
        if (strValue == null || strValue.isEmpty())
            strValue = "0.0";

        return Double.parseDouble(strValue);
    }

    /**
     * Converts a list of Double to a pipeline separated string
     * @param doubles
     * @return String
     */
    public String doubleListToString(List<Double> doubles){
        String str = "";

        for (Double doub : doubles){

            if (!str.isEmpty())
                str = str + "|";

            str = str + doub.toString();
        }

        return str;
    }

    public String doubleToString(Double doubleVal){
        String str = "";

        if (doubleVal == null)
            return str;

        return doubleVal.toString();

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

        stringValue = stringValue.trim();

        if (stringValue.contains(":")){ //Ontology, like "NEWT:9606"
            String[] strings = stringValue.split(":");
            stringValue = strings[1];  //You are left with "9606"
        }

        return Integer.parseInt(stringValue);
    }

    public int convertPosNegToInt(String stringValue){
        if (stringValue == null || stringValue.isEmpty())
            stringValue = "0";

        if (stringValue.toLowerCase().contains("positive"))
            stringValue = "1";

        if (stringValue.toLowerCase().contains("negative"))
            stringValue = "0";

        return stringToInt(stringValue);

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

    public String uriToString(URI uri){

        String uriStr = "";
        if (uri == null)
            return uriStr;

        return uri.toString();
    }

    public Integer convertMSItoPSIreliability(String reliability){

        //Well this is fun, we adopted PSI reliability scores but this is not adopted in mzTAB
        if (reliability == null || reliability.isEmpty()){
            reliability = "3";        // Reliability must only be 1 (good), 2 (medium), and 3 (bad).
        } else {
            if (reliability.contains("0:") || reliability.contains("1:") || reliability.contains("2:")) // "0:non-significant identification" or "1:poor reliability" or "2:less poor reliability"
                reliability = "3";

            if (reliability.contains("3:")) // "3:medium reliability"
                reliability = "2";

            if (reliability.contains("4:") || reliability.contains("5:")) // "4:good reliability" or "5:very good reliability"
                reliability = "1";

        }

        return Integer.parseInt(reliability);
    }

//    public ParamList stringToParamList(String strValue){
//        ParamList paramList = new ParamList();
//
//        if (strValue == null || strValue.isEmpty())
//            return null;
//
//        Param param = new Param(strValue);
//        paramList.add(param);
//
//        return paramList;
//    }
//
//    public String paramListToString(ParamList params){
//        String paramStr = "";
//
//        if (params == null)
//            return paramStr;
//
//        for (Param param : params){
//
//            if (!paramStr.isEmpty())
//                paramStr = paramStr + ",";
//
//            paramStr = paramStr + param.toString();
//
//        }
//
//        return paramStr;
//    }


}
