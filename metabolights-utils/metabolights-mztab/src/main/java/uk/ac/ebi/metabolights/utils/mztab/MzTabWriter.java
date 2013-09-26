/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 25/09/13 16:03
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.MzTabFile;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;


public class MzTabWriter {

    MzTabSmallMolecule mzTabSmallMolecule = new MzTabSmallMolecule();
    MzTabUtils utils = new MzTabUtils();
    MzTabDAO mzTabDAO = new MzTabDAO();
    private final static Logger logger = Logger.getLogger(MzTabWriter.class.getName());

    private String argsMessage = "Please use either'maf_file_name mztab_file_name' to process files.";

    public void main(String args[]){

        if (!commandLineValidation(args)){

            System.out.println("Usage:");
            System.out.println("Parameter 1: The existing MetaboLights MAF (Metabolite Assignment File)");
            System.out.println("Parameter 2: The name for the new mzTab file we should create");
            System.out.println("Parameter 3: The MetaboLights Accession number (MTBLS id) for this MetaboLights study");

        } else {

            try {
                String maf = args[0], mzTab = args[1], accessionNumber = args[2];
                logger.info("Starting to convert file "+maf+ " into "+mzTab+ " for study " +accessionNumber);
                convertMAFToMzTab(maf, mzTab, accessionNumber);
            } catch (MzTabParsingException e) {
                System.out.println("ERROR: Could not process the file: s" + e.toString());
                logger.error("ERROR: Could not process the files. " + e.toString());
            }
        }

        /*
        if (args[0] == null) {
            System.out.println("No arguments passed, I will try to look for the MAF file (m_<some_name>.tsv) in this directory");

            File[] mafFiles = utils.findMafFile(".");  //Set directory to the current folder

            if (mafFiles != null)
                convertAllMAFs(mafFiles);
            else
                System.out.println("Sorry, I could not find a maf file. " +argsMessage);

        } else {

            if (args.length != 2){
                System.out.println(argsMessage);
            } else {
              File inMAFfile = readMAF(args[0]);
                try {
                    convertMAFToMZTab(inMAFfile, args[1]);
                } catch (MzTabParsingException e) {
                    e.printStackTrace();
                }
            }

        }
        */

    }

    /*
    private void convertAllMAFs(File[] mafFiles){

        for (File files : mafFiles){
            File mafFile = readMAF(files.getName());
            if (mafFile != null)
                try {
                    convertMAFToMZTab(mafFile, mafFile.getName().replace(".tsv",".mztab"));
                } catch (MzTabParsingException e) {
                    e.printStackTrace();
                }
        }
    }
    */

    public File readMAF(String fileName){
        File file = new File(fileName);
        return file;
    }

    public void writeMzTab(String fileName, String fileContext) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fileContext);
        fileWriter.close();
    }

    public void convertMAFToMzTab(String mafFileName, String mzTabFile, String accessionNumber) throws MzTabParsingException {
        try {

            MzTabFile mzTab = new MzTabFile();
            File mafFile = new File(mafFileName);

            Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines = new ArrayList<MetaboliteAssignmentLine>();

            MetaboliteAssignment metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(mafFile.toString());
            if (metaboliteAssignment != null)
                metaboliteAssignmentLines = metaboliteAssignment.getMetaboliteAssignmentLines();

            for (MetaboliteAssignmentLine metLine : metaboliteAssignmentLines){

                //Test if we have the database identifier and description, only process rows that have id
                if (!utils.processLine(metLine))
                    continue;

                SmallMolecule molecule = mzTabSmallMolecule.convertToMzTab(metLine);
                molecule.setUnitId(accessionNumber);
                mzTab.addSmallMolecule(molecule);

            }

            writeMzTab(mzTabFile, mzTab.toMzTab());

        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("ERROR:  1st parameter must be the existing MetaboLights MAF (Metabolite Assignment File): " + args[0]);
            System.out.println("----");
            return false;
        }

        if (args[1] == null){
            System.out.println("ERROR: 2nd parameter must be the name for the new mzTab file.");
            System.out.println("----");
            return false;
        }

        if (args[2] == null){
            System.out.println("ERROR: You must also give us the MetaboLights Accession number (MTBLS id) for this MetaboLights study");
            System.out.println("----");
            return false;
        }

        return true;

    }

}
