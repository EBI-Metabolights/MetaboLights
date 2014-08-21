/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/16/13 11:03 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.utils.sampletab;


import org.apache.log4j.Logger;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/05/2013
 * Time: 11:20
 */
public class SampleTabTools {

    private static Logger logger = Logger.getLogger(SampleTabTools.class);
    /**
     * Converts ISA-tab date format (string) to a SampleTab date (Date)
     * @param isaTabDate string from ISA-tab files
     * @return Date in SampleTab format
     */
    public Date parseDate(String isaTabDate){

        String isaDateFormat = "yyyy-MM-dd"; //ISA format for the date
        //String sampleDateFormat = "yyyy-MM-dd HH:mm:ss.ss"; //SampleTab date format according to the documentation
        String sampleDateFormat = "yyyy/MM/dd"; //SampleTab date format according to Adam (new)
        SimpleDateFormat isaFormatter, sampleFormatter;

        try {

            isaFormatter = new SimpleDateFormat(isaDateFormat);
            sampleFormatter = new SimpleDateFormat(sampleDateFormat);

            Date isaDate = isaFormatter.parse(isaTabDate);
            String sampleDateStr = sampleFormatter.format(isaDate);

            Date sampleDate = new SimpleDateFormat(sampleDateFormat).parse(sampleDateStr);

            return sampleDate;
        } catch (ParseException e) {
            logger.error("Cannot parse date for " + isaTabDate + " : " + e.getMessage());
            return null;
        }

    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public String cleanTermAttributes(String termNumber){
        if (termNumber.contains("_")){
            String[] termNumbers = termNumber.split("_");
            logger.info("Removing underscore from ontology term, "+ termNumber + " became "+ termNumbers[1]);
            termNumber = termNumbers[1]; //get rid if the term before the underscore, "obo:NCBITaxon_10090" becomes "10090"
        }

        return termNumber;
    }

    /**
     * The full ontology term in, a cleaner version out....
     * @param ontoTerm
     * @return
     */
    public String cleanOntologyTerm(String ontoTerm){

        String[] cleanDesigns = null;
        String cleanDesign = ontoTerm;

        if (ontoTerm.contains(":")){
            cleanDesigns = ontoTerm.split(":");

            if (cleanDesigns != null)
                cleanDesign = cleanDesigns[1];    //Skip the reference before the colon, "efo:EFO_0004529" becomes "EFO_0004529"
        }

        return cleanDesign;

    }


    /**
     * Checks if the correct parameters are given
     * @param args
     * @return true if you got it right
     */
    public static boolean commandLineValidation(String args[]){

        // If there isn't any parameter
        if (args == null || args.length== 0){
            return false;
        }

        // Check first parameter is the config folder
        File first = new File(args[0]);
        if (!first.exists()){
            System.out.println("ERROR:  1st parameter must be the configuration folder: " + args[0]);
            System.out.println("----");
            return false;
        }

        // Check first parameter is the config file file
        File secound = new File(args[1]);
        if (!secound.exists()){
            System.out.println("ERROR: 2nd parameter must be the ISAtab (MTBLS Study) folder" + args[1]);
            System.out.println("----");
            return false;
        }

        if (args[2] == null){
            System.out.println("ERROR: You must also give us the filename of the SampleTab file you want to export");
            System.out.println("----");
            return false;
        }

        return true;

    }
}
