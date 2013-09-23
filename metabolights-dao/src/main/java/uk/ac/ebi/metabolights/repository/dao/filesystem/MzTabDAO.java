/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 23/09/13 13:33
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import com.csvreader.CsvReader;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment.MetaboliteAssignmentLine;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment.MetaboliteAssignmentLine.SampleMeasurement;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class MzTabDAO {

    public MetaboliteAssignment mapMetaboliteAssignmentFile(String assignmentFileName) {
        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();

        //Assay has the reference to the MAF (file name) so we know the full filename (directory and the filename with extension)
        if (checkFileExists(assignmentFileName)){
            metaboliteAssignment.setMetaboliteAssignmentFileName(assignmentFileName);         // Set the fully qualified filename
            metaboliteAssignment.setMetaboliteAssignmentLines(convertToMetaboliteAssignmentLines(assignmentFileName));    //Add records from MAF
        }

        return metaboliteAssignment;

    }

    private Collection<MetaboliteAssignmentLine> convertToMetaboliteAssignmentLines(String fileName){

        Integer maxColumnNumber = -1; //We need to know when the known columns end and the sample records start. 0 is the first column number
        CsvReader fileData = null;
        Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines = new ArrayList<MetaboliteAssignmentLine>();

        try {

            fileData = new CsvReader(fileName, '\t');     // Read the tab-separated MAF
            fileData.readHeaders();                       // Read the header records
            while (fileData.readRecord()){
                MetaboliteAssignment.MetaboliteAssignmentLine assignmentLine = new MetaboliteAssignment.MetaboliteAssignmentLine();

                // Get the identifier V1 and V2
                String identifier = fileData.get(MetaboliteAssignment.fieldNames.databaseIdentifier.toString());
                if (identifier == null || identifier.isEmpty())      //Could be V1 of the MAF, then the column name is different
                    identifier = fileData.get(MetaboliteAssignment.fieldNames.identifier.toString());
                assignmentLine.setIdentifier(identifier);


                // Get the description V1 and V2
                String description = fileData.get(MetaboliteAssignment.fieldNames.metaboliteIdentification.toString());
                if (description == null || description.isEmpty())      //Could be V1 of the MAF, then the column name is different
                    description = fileData.get(MetaboliteAssignment.fieldNames.description.toString());
                assignmentLine.setDescription(description);

                // All other lines should have the same name in V1 and V2
                assignmentLine.setUnitId(fileData.get(MetaboliteAssignment.fieldNames.unitId.toString()));
                assignmentLine.setChemicalFormula(fileData.get(MetaboliteAssignment.fieldNames.chemicalFormula.toString()));
                assignmentLine.setSmiles(fileData.get(MetaboliteAssignment.fieldNames.smiles.toString()));
                assignmentLine.setInchi(fileData.get(MetaboliteAssignment.fieldNames.inchi.toString()));
                assignmentLine.setChemicalShift(fileData.get(MetaboliteAssignment.fieldNames.chemicalShift.toString()));
                assignmentLine.setMultiplicity(fileData.get(MetaboliteAssignment.fieldNames.multiplicity.toString()));
                assignmentLine.setMassToCharge(fileData.get(MetaboliteAssignment.fieldNames.massToCharge.toString()));
                assignmentLine.setFragmentation(fileData.get(MetaboliteAssignment.fieldNames.fragmentation.toString()));
                assignmentLine.setModifications(fileData.get(MetaboliteAssignment.fieldNames.modifications.toString()));
                assignmentLine.setCharge(fileData.get(MetaboliteAssignment.fieldNames.charge.toString()));
                assignmentLine.setRetentionTime(fileData.get(MetaboliteAssignment.fieldNames.retentionTime.toString()));
                assignmentLine.setTaxid(fileData.get(MetaboliteAssignment.fieldNames.taxid.toString()));
                assignmentLine.setSpecies(fileData.get(MetaboliteAssignment.fieldNames.species.toString()));
                assignmentLine.setDatabase(fileData.get(MetaboliteAssignment.fieldNames.database.toString()));
                assignmentLine.setDatabaseVersion(fileData.get(MetaboliteAssignment.fieldNames.databaseVersion.toString()));
                assignmentLine.setReliability(fileData.get(MetaboliteAssignment.fieldNames.reliability.toString()));
                assignmentLine.setUri(fileData.get(MetaboliteAssignment.fieldNames.uri.toString()));
                assignmentLine.setSearchEngine(fileData.get(MetaboliteAssignment.fieldNames.searchEngine.toString()));
                assignmentLine.setSearchEngineScore(fileData.get(MetaboliteAssignment.fieldNames.searchEngineScore.toString()));
                assignmentLine.setSmallmoleculeAbundanceSub(fileData.get(MetaboliteAssignment.fieldNames.smallmoleculeAbundanceSub.toString()));
                assignmentLine.setSmallmoleculeAbundanceStdevSub(fileData.get(MetaboliteAssignment.fieldNames.smallmoleculeAbundanceStdevSub.toString()));
                assignmentLine.setSmallmoleculeAbundanceStdErrorSub(fileData.get(MetaboliteAssignment.fieldNames.smallmoleculeAbundanceStdErrorSub.toString()));


                //TODO, add sample lines now.
                if (maxColumnNumber == -1)
                    maxColumnNumber = getMaxColumnNumber(fileData, MetaboliteAssignment.fieldNames.smallmoleculeAbundanceStdErrorSub.toString(), maxColumnNumber);    //TODO, this assumes the submitter has not reorganised the column ordering

                Collection<MetaboliteAssignmentLine.SampleMeasurement> sampleMeasurements = new ArrayList<SampleMeasurement>();

                for (Integer colNumber = maxColumnNumber+1; colNumber <= fileData.getColumnCount(); colNumber++){
                    SampleMeasurement sampleMeasurement = assignmentLine.new SampleMeasurement();

                    sampleMeasurement.setSampleName(fileData.getHeader(colNumber));
                    sampleMeasurement.setValue(fileData.get(colNumber));

                    sampleMeasurements.add(sampleMeasurement);
                }

                assignmentLine.setSampleMeasurements(sampleMeasurements);


                metaboliteAssignmentLines.add(assignmentLine);
            }

            return metaboliteAssignmentLines;
        } catch (Exception e) {
            e.printStackTrace(); //TODO
        }

        return metaboliteAssignmentLines;

    }


    //This can be used to find (MAF) files on the filesystem
    private File[] findMAFiles(String folderName){
        File dir = new File(folderName);      //Folder that holds the MAF files

        File[] matches = dir.listFiles(
            new FilenameFilter(){
                public boolean accept(File dir, String name){
                        return name.startsWith("m_") && name.endsWith(".tsv");
                }

            }

        );

        return matches;

    }

    private int getMaxColumnNumber(CsvReader fileData, String columnName, int previousMaxNumber){
        Integer columnNumber = 0;

        try {
            columnNumber = fileData.getIndex(columnName);
        } catch (IOException e) {
            return previousMaxNumber;
        }

        if (columnNumber > previousMaxNumber)
            return columnNumber;

        return previousMaxNumber;
    }

    private Boolean checkFileExists(String assignmentFileName){

        if (assignmentFileName == null || assignmentFileName.isEmpty())
            return false;        // No filename given

        File f = new File(assignmentFileName);

        if(f.exists())
            return true;        // Found the file

        return false;           // Did not find the file

    }

}
