/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2017-Jan-13
 * Modified by:   kenneth
 *
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.utils.mafsplitter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by kenneth on 13/01/2017.
 */
public class MAFSplitter {

    private final static Logger logger = LoggerFactory.getLogger(MAFSplitter.class.getName());

    public static String getPipeline() {
        return pipeline;
    }

    public static void setPipeline(String pipeline) {

        try {
            MAFSplitter.pipeline  = new String(pipeline.getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            MAFSplitter.pipeline = pipeline;
        }
    }

    private static String pipeline;
    private static CSVReader reader;
    private static List<String[]> allLines = new ArrayList<String[]>();
    private static List<String[]> allNewLines = new ArrayList<String[]>();
    private static String[] headerRow;

    public static CSVReader getReader() {
        return reader;
    }

    public static void setReader(CSVReader reader) {
        MAFSplitter.reader = reader;
    }


    public static void main(String[] args){

        if (!commandLineValidation(args)){

            System.out.println("Usage:");
            System.out.println("Parameter 1: name of MAF file");
            System.out.println();

        } else {

            String fileName = args[0];
            logAndOutput("Starting to read Metabolite Assignment File " +fileName);
            convertMAF(fileName);

        }
    }


    /**
     * Checks if the correct parameters are given
     * @param args
     * @return true if you got it right
     */
    public static boolean commandLineValidation(String args[]){

        // If there isn't any parameter
        if (args == null || args.length == 0){
            return false;
        }

        File first = new File(args[0]);
        if (!first.exists()){
            logAndOutput("ERROR: Please supply name (with full path) to your Metabolite Annotation File (MAF) " + args[0]);
            return false;
        }

        return true;

    }

    public static void convertMAF(String fileName) {

        logAndOutput("Starting to convert MAF file " +fileName);
        try {
            setReader(new CSVReader(new FileReader(fileName), '\t' , '"' , 0));
            readCSVFile();

            setPipeline("|");

            //Does the MAF contain pipelines? if not we can stop processing now
            if (hasPipeline()){
                if(splitMAF()) {
                    writeNewMAF(fileName); //Write the new file
                    logAndOutput("Successfully split the metabolite annotation file into Individual lines");
                } else {
                    logAndOutput("ERROR: Failed to split the metabolite annotation file into Individual lines");
                }

            } else {
                logAndOutput("File does not contain pipeline(s)");
            }

        } catch (Exception e) {
            logAndOutput("ERROR: Could not process the file: " + e.toString());
        }

    }

    /**
     * Read the entire MAF into a list of String[]
     */
    private static void readCSVFile(){
        String[] nextLine;
        try {
            while ((nextLine = getReader().readNext()) != null) {
                allLines.add(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeNewMAF(String fileName){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fileName+"_SPLIT" ), '\t', '"');

            //Write the entire list
            writer.writeAll(allNewLines);

            //close the writer
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasPipeline(){
        //Read CSV line by line to check if there is any pipelines "|" used
        String[] nextLine;
        for (int i = 0; i < allLines.size(); i++) {
            nextLine = allLines.get(i);
            if (nextLine != null) {
                    //Verifying the read data here
                    for (String cell : nextLine) {
                        if (cell.contains(getPipeline())) {
                            return true;
                        }
                    }
                }
            }

        return false;
    }

    private static boolean splitMAF(){
        //Read CSV line by line to check if there is any pipelines "|" used
        //TODO, split on pipeline first (All columns), then on ";" and "/" in metabolite_identifcation column
        String[] nextLine;
        int numberOfPipes = 0;

        for (int i = 0; i < allLines.size(); i++) {
            nextLine = allLines.get(i);
            numberOfPipes = correctNumberOfPipes(nextLine);  //Keep in mind that two dataelements only contain 1 pipeline
            if (nextLine != null && numberOfPipes > 0) { // We have the same number of pipelines in all cells so we can proceed
                for (int i1 = 0; i1 < numberOfPipes+1; i1++) { //Loop the number of times we have pipelines (+1 to get the last data element)
                    List<String> newLine = new ArrayList<String>(); //The modified line
                    for (String cell : nextLine) {
                        if (cell.contains(getPipeline())) { //Splitting!

                            cell = cleanCells(cell);

                            String[] newCell = cell.split(Pattern.quote(getPipeline()));
                            if (newCell.length >= i1+1) { //Make sure the array after split containt the same number of elements as we try to loop through
                                newLine.add(newCell[i1]);    //Adding the modified cell
                            } else {  // Could not split after all.
                                logAndOutput("Error in splitting: "+cell);
                                newLine.add(cell); //Adding the unmodified cell
                            }

                        } else {
                            newLine.add(cell); //Adding the unmodified cell
                        }
                    }
                    String[] modifedRow = newLine.toArray(new String[0]);
                    allNewLines.add(modifedRow);  //add the new modified line
                }
            } else {
                //TODO, https://www.pivotaltracker.com/story/show/142041611
                allNewLines.add(nextLine); //No pipeline or incorrect number of pipes. Move on nothing to see here....
            }

        }

        return true;
    }

    private static String cleanCells(String cell){
        if (cell.contains(getPipeline()+getPipeline())) {
            cell = cell.replaceAll("\\|\\|", "| |"); //Need to add space
            cell = cell.replaceAll("\\|\\|", "| |"); //Stupid thing!
        }

        if (cell.equals(getPipeline()))
            cell = " | ";           //Stupid thing!

        if (cell.endsWith(getPipeline()))
            cell = cell + " ";      //Stupid thing!

        if (cell.startsWith(getPipeline()))
            cell = " " + cell ;     //Stupid thing!

        return cell;
    }

    private static int correctNumberOfPipes(String[] nextLine){
        int firstNumberOfPipes = 0;
        List<Integer> allPipes = new ArrayList<Integer>();
        for (String cell: nextLine){
            if (cell.contains(getPipeline()))
                allPipes.add(StringUtils.countMatches(cell, getPipeline()));
        }

        //check for same number of pipelines in all cells for this row.
        if(allPipes != null && allPipes.size() > 0){
            firstNumberOfPipes = allPipes.get(0);

            for (Integer allPipe : allPipes) {
                if (allPipe != firstNumberOfPipes){
                    logAndOutput(String.format("Warning: Incorrect number of pipelines, ignoring: " + Arrays.toString(nextLine)));
                    return -1;
                }
            }
        }
        return firstNumberOfPipes;
    }

    private static void logAndOutput(String text){
        System.out.println(text);
        logger.info(text);
    }
}
