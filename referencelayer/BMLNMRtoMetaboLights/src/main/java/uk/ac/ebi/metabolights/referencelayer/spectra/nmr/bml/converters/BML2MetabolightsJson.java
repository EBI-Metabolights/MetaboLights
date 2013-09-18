/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 12/09/13 11:43
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.converters;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model.MSICompliantNMRMetabolomicsExperimentReport;
import uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model.SimpleNMRSpectraData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

/**
 * User: conesa
 * Date: 09/05/2013
 * Time: 12:32
 */
public class BML2MetabolightsJson {

    static Logger logger = LoggerFactory.getLogger(BML2MetabolightsJson.class);

    File bmlFile;
    File jsonOutputFile;

    JAXBContext context = null;
    Unmarshaller unmarshaller = null;
    int compressionFactor = 1;

    final String VERSION = "1";



    public BML2MetabolightsJson(File bmlFile, File jsonOutputFile){

        this.bmlFile = bmlFile;
        this.jsonOutputFile = jsonOutputFile;
    }

    public BML2MetabolightsJson(File bmlFile){

        this.bmlFile = bmlFile;

        // If bmlfile is a folder the json
        if (bmlFile.isDirectory()) {
            // ..we output to the same folder
            this.jsonOutputFile = bmlFile;
        } else {
            // We output to the same filename + .json
            this.jsonOutputFile = new File (bmlFile.getAbsolutePath() + ".json");
        }

        logger.info("Calculated output destiny file: {}", jsonOutputFile.getAbsolutePath());

    }

    public int getCompressionFactor() {
        return compressionFactor;
    }

    public void setCompressionFactor(int compressionFactor) {
        this.compressionFactor = compressionFactor;
    }

    public static void main (String[] args){


        int compressionFactor=1;

        if(commandLineValidation(args)){

            BML2MetabolightsJson instance = null;

            // First parameter to File instance
            File first = new File(args[0]);

            // If there is 1 parameter
            if (args.length==1){

                logger.info("Using only one parameter: " + first.getAbsolutePath());
                instance = new BML2MetabolightsJson(first);

            }else if(args.length==2){

                // If its a number...
                if (StringUtils.isNumeric(args[1])){

                    compressionFactor = Integer.parseInt(args[1]);

                    logger.info("Using two parameters: " + first.getAbsolutePath() + ", compression factor: " + compressionFactor);
                    instance = new BML2MetabolightsJson(first);


                } else {
                    File second = new File(args[1]);

                    logger.info("Using two parameters:\n" + first.getAbsolutePath() + "\n" + second.getAbsolutePath());
                    instance = new BML2MetabolightsJson(first,second);
                }

            } else {

                File second = new File(args[1]);

                compressionFactor = Integer.parseInt(args[2]);

                logger.info("Using 3 parameters:\n" + first.getAbsolutePath() + "\n" + second.getAbsolutePath()+ ", compression factor: " + compressionFactor);
                instance = new BML2MetabolightsJson(first,second);

            }

            // Set the compression factor
            instance.setCompressionFactor(compressionFactor);

            instance.run();

        } else {


            System.out.println("Usage:");
            System.out.println("A:) One parameter (being that a BML xml file): Output file will be genarated in the same folder appending \".json\" at the end" );
            System.out.println("B:) One parameter (being that a folder): Will loop through all xml files in that folder and will genarate outputfiles appending \".json\" at the end");
            System.out.println("C:) Two parameters (both being files): convert first file (xml one) into the jason file and save it as the second file");
            System.out.println("D:) Two parameters (both being folders): Will loop through all xml files in that folder and will genarate outputfiles appending \".json\" at the end in the folder specified (2nd parameter)");
            System.out.println("E:) Two parameters (1st file, 2nd folder): Convert 1st file into a json file and saves it in the folder specified (2nd parameter)");
            System.out.println("F:) There is no such an option F...!");
            System.out.println("G:) now there is: if it's a number it will be considered as a compression factor: 1 is default and no compression.");

        }

    }

    private static boolean commandLineValidation(String args[]){

        // If there isn't any parameter
        if (args == null || args.length== 0){

            return false;
        }


        // Check first parameter is a file and exists (Either folder or file)
        File first = new File(args[0]);

        if (!first.exists()){
            System.out.println("First parameter must be a file, folder that exists and it doesn't: " + args[0]);
            return false;
        }

        // If there are four or more parameters
        if (args.length>3){
            // Inform ignoring them
            System.out.println("Only working with 3 parameters, ignoring the rest. Invoke without parameters for usage.");
            return false;
        }

        return true;


    }

    /**
     * It will convert BML xml spectra files to spectra viewer json files
     *
     */
    public void run() {


        if (validateSetup()) {

            // Get the list of input files..may be one or al xml in a directory..
            Collection<File> files = getInputFiles();

            if (!initJAXBContextAndUnmarshaller())  return;


            for(File inputFile:files){

                File outputFile= null;

                try {

                    outputFile = getOutputFile(inputFile);

                    bmlToJson(inputFile,outputFile);

                }catch (Exception e){

                    // Log and proceed
                    logger.info("Can't convert {} into {}.\nReason:{}" , new Object[]{inputFile.getName() , outputFile.getName(),e.getMessage()});

                }
            }

        }


    }

    private File getOutputFile(File inputFile){

        // If output file is a directory
        if (jsonOutputFile.isDirectory()){
            // Compose the name
            return new File (jsonOutputFile.getAbsolutePath()+ "/" + inputFile.getName() + "V" + VERSION + "_CF" + compressionFactor +  ".json");
        } else {

            return jsonOutputFile;
        }
    }

    private Collection<File> getInputFiles(){

        Collection<File> input = new ArrayList<File>();

        // If the input folder is a directory...
        if (bmlFile.isDirectory()){

            // return a list of xml file in the directory.
            File[] xmlFiles= bmlFile.listFiles(new FilenameFilter() {

                public boolean accept(File file, String name) {
                    return name.endsWith(".xml");
                }
            });

            input.addAll(Arrays.asList(xmlFiles));
        } else {
            input.add(bmlFile);
        }

        return input;
    }


    private boolean validateSetup(){

        if (bmlFile == null) {
            logger.error("Input BML file/directory can't be empty.");
            return false;
        }

        if (jsonOutputFile == null) {
            logger.error("Output json file/directory can't be empty.");
            return false;
        }

        if (!jsonOutputFile.isDirectory() && bmlFile.isDirectory()){
            logger.error("Unconsistent parameters: json output file must be a directory when \"Input BML\" parameter is a directory as well");
            return false;

        }

        return true;
    }

    private boolean validateSingleFileConversion(File bml){

        // If bml file is writable...
        if (bml.canWrite()) {
            // We do not proceed...(
            logger.error("Source BML xml file must be read only for protection: " + bmlFile.getAbsolutePath());
            return false;
        }

        return true;
    }

    private boolean initJAXBContextAndUnmarshaller(){



        try {
            context = JAXBContext.newInstance(
                    MSICompliantNMRMetabolomicsExperimentReport.class.getPackage().getName());
        } catch (JAXBException e) {
            logger.error("Can't create JABX context instance based on {}:\n{}",MSICompliantNMRMetabolomicsExperimentReport.class.getPackage().getName(), e.getMessage());
            return false;
        }


        try {

            unmarshaller = context.createUnmarshaller();

        } catch (JAXBException e) {

            logger.error("Can't create BML xml loader (JABX unmarshaller):\n {}", e.getMessage());
            return false;

        }

        return true;
    }


    private void bmlToJson (File bml, File json){


        // Validate
        if (!validateSingleFileConversion(bml)) return;

        // Log start
        logger.info("Conversion started from {} to {}.", bml.getAbsolutePath(), json.getAbsolutePath());

        // Load the xml into the model objects...
        MSICompliantNMRMetabolomicsExperimentReport bmlData = null;

        // Instantiate the Java/json object
        //JsonSpectra jsonSpectra = new JsonSpectra();
        SimpleNMRSpectraData nmrSpectraData;

        try {

            // TODO: do not unmarshall all...
            bmlData = (MSICompliantNMRMetabolomicsExperimentReport)  unmarshaller.unmarshal(bml);

            // Populate main properties...this is
            // TODO: Spectra id?
            //jsonSpectra.setSpectrumId(1);

            MSICompliantNMRMetabolomicsExperimentReport.Analysis.Experiment1D exp1d= null;

            // Get the 1D experiment
            exp1d = bmlData.getAnalysis().getExperiment1D();


            // If its null it must be a 2D experiment....we do not do this, ATM.
            if (exp1d == null){
                logger.info("Not a 1D experiment, probably a 2D...we will not convert this experiment: " + bmlFile.getName());
                return;
            }

            // Get the spectra, we expect only one, so we will take the first one...
            List<MSICompliantNMRMetabolomicsExperimentReport.Analysis.Experiment1D.Spectrum1D> sp1dList;

            // Get list of spectra (there should be only one)
            sp1dList = exp1d.getSpectrum1D();

            MSICompliantNMRMetabolomicsExperimentReport.Analysis.Experiment1D.Spectrum1D sp1d;

            sp1d = sp1dList.iterator().next();


            // Get end value and start value
            //jsonSpectra.setMzStart(sp1d.getXStartValue());
            //jsonSpectra.setMzStop(sp1d.getXEndValue());

            // Populate peaks
            MSICompliantNMRMetabolomicsExperimentReport.Analysis.Experiment1D.Spectrum1D.DataMatrix dm;

            // Get the data matrix
            dm = sp1d.getDataMatrix();

            // Counter for skipping points...
            int pointsToIgnore = 0;


            ArrayList<Double> intensityPoints = new ArrayList<Double>();


            // For each data point in
            for (MSICompliantNMRMetabolomicsExperimentReport.Analysis.Experiment1D.Spectrum1D.DataMatrix.DataPoint dp: dm.getDataPoint()){



                if (pointsToIgnore == 0) {

                      intensityPoints.add(Double.valueOf(dp.getRValue()));

//                    JsonSpectraPeak jsp = new JsonSpectraPeak(dp.getRValue().doubleValue(),dp.getXValue().doubleValue());
//                    jsonSpectra.getPeaks().add(jsp);



                    pointsToIgnore = compressionFactor -1;
                } else {
                    pointsToIgnore--;
                }
            }


            // Reverse the points as it is needed for the spec viewer.
            Collections.reverse(intensityPoints);

            Double[] doublesArray = intensityPoints.toArray(new Double[intensityPoints.size()]);



            // Instantiate the NMR spectrum object...
            nmrSpectraData = new SimpleNMRSpectraData(doublesArray, sp1d.getXStartValue(), sp1d.getXEndValue());


        } catch (JAXBException e) {
            logger.error("Can't load spectra file: {}:\n {}",bml.getAbsolutePath(), e.getMessage());
            return;
        }



        // Save json file
        Gson gson = new Gson();

        // Instantiate the file writer...
        FileWriter fw = null;
        try {
            fw = new FileWriter(json);
        } catch (IOException e) {
            logger.error("Can't write json file {} :\n {}",json.getAbsolutePath(), e.getMessage());
        }

        // Write the file
        //gson.toJson(jsonSpectra, fw);
        gson.toJson(nmrSpectraData, fw);


        try {

            fw.close();
        } catch (IOException e) {
            logger.error("filewriter write or close error: " + e.getMessage());
        }

    }

}
