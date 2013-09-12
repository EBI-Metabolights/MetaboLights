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

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.pride.jmztab.MzTabFile;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.io.*;
import java.util.Iterator;
import java.util.List;


public class MzTabWriter {

    MzTabSmallMolecule mzTabSmallMolecule = new MzTabSmallMolecule();
    MzTabUtils utils = new MzTabUtils();
    private String argsMessage = "Please use ' maf_file_name mztab_file_name' if you want to process known files.";

    public void main(String args[]){

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

    }

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

    private String getStudyIdentifier(String fileName){

        String[] identFileSections = fileName.split("_");
        return  identFileSections[1];

    }

    public void convertMAFToMZTab(File mafFile, String mzTabFile) throws MzTabParsingException {
        try {
            MzTabFile mzTab = new MzTabFile();
            Reader reader = new FileReader(mafFile);

            CSVReader csvReader =  new CSVReader(reader, '\t');
            List<String[]> mafList = csvReader.readAll();

            Iterator iterator = mafList.iterator();

            String technology = null;

            while (iterator.hasNext()){

                String[] mafLine = (String[]) iterator.next();  //The line from the MetaboLight MAF (Metabolite Assignment File)

                if(technology == null){ //Use the header file to determine if this is MS or NMR
                    if (mafLine[5].equals("mass_to_charge"))
                        technology = mzTabSmallMolecule.technologyMS;
                    else
                        technology = mzTabSmallMolecule.technologyNMR;

                    continue; //We have the technology, so skip the header row
                }

                //Test if we have the database identifier and description, only process rows that have id
                if (mafLine[0] == null || mafLine[0].isEmpty() || mafLine[4] == null || mafLine[4].isEmpty())
                    continue;

                SmallMolecule molecule = mzTabSmallMolecule.mafToMzTab(mafLine, technology);
                molecule.setUnitId(getStudyIdentifier(mafFile.getName()));     //Use the MTBLSid as the UNIT id

                mzTab.addSmallMolecule(molecule);

            }

            writeMzTab(mzTabFile, mzTab.toMzTab());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
