/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/17/13 2:31 PM
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

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import com.csvreader.CsvReader;
import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.metabolights.repository.model.SampleMeasurement;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class MzTabDAO {

    private final static Logger logger = Logger.getLogger(MzTabDAO.class.getName());

    public MetaboliteAssignment mapMetaboliteAssignmentFile(String assignmentFileName) {
        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();
        logger.info("Metabolite Assignment File Name is given as: "+assignmentFileName);

        //Assay has the reference to the MAF (file name) so we know the full filename (directory and the filename with extension)
        if (checkFileExists(assignmentFileName)){
            metaboliteAssignment.setMetaboliteAssignmentFileName(assignmentFileName);         // Set the fully qualified filename
            metaboliteAssignment.setMetaboliteAssignmentLines(convertToMetaboliteAssignmentLines(assignmentFileName));    //Add records from MAF
            logger.info("Metabolite Assignment File Name - Found: "+assignmentFileName);
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
                MetaboliteAssignmentLine assignmentLine = new MetaboliteAssignmentLine();

                // Get the identifier V1 and V2
                String databaseIdentifier = fileData.get(MetaboliteAssignment.fieldNames.databaseIdentifier.toString());
                if (databaseIdentifier == null || databaseIdentifier.isEmpty())      //Could be V1 of the MAF, then the column name is different
                    databaseIdentifier = fileData.get(MetaboliteAssignment.fieldNames.identifier.toString());     //Version 1 name for the column
                assignmentLine.setDatabaseIdentifier(databaseIdentifier);


                // Get the description V1 and V2
                String metaboliteIdentification = fileData.get(MetaboliteAssignment.fieldNames.metaboliteIdentification.toString());
                if (metaboliteIdentification == null || metaboliteIdentification.isEmpty())      //Could be V1 of the MAF, then the column name is different
                    metaboliteIdentification = fileData.get(MetaboliteAssignment.fieldNames.description.toString());  //Version 1 name for the column
                assignmentLine.setMetaboliteIdentification(metaboliteIdentification);

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


                if (maxColumnNumber == -1)
                    maxColumnNumber = getMaxColumnNumber(fileData, MetaboliteAssignment.fieldNames.smallmoleculeAbundanceStdErrorSub.toString(), maxColumnNumber);    //TODO, this assumes the submitter has not reorganised the column ordering

                Collection<SampleMeasurement> sampleMeasurements = new ArrayList<SampleMeasurement>();

                for (Integer colNumber = maxColumnNumber+1; colNumber <= fileData.getColumnCount(); colNumber++){
                    SampleMeasurement sampleMeasurement = new SampleMeasurement();

                    sampleMeasurement.setSampleName(fileData.getHeader(colNumber));
                    sampleMeasurement.setValue(fileData.get(colNumber));

                    sampleMeasurements.add(sampleMeasurement);
                }

                assignmentLine.setSampleMeasurements(sampleMeasurements);

                if (assignmentLine.getDatabaseIdentifier().contains("|")){   //The submitter has submitted more than one metabolite per row, this is a supported feature of mzTab

                    //We need to split the metabolite into separate lines.
                    for (String eachMetabolite : assignmentLine.getDatabaseIdentifier().split("\\|")){
                        assignmentLine.setDatabaseIdentifier(eachMetabolite); //Update the identified metabolite
                        metaboliteAssignmentLines.add(assignmentLine); //Add the row with only one metabolite reported
                    }

                } else {
                    //Only one metabolite reported so add a single rows
                    metaboliteAssignmentLines.add(assignmentLine);
                }
            }

            return metaboliteAssignmentLines;
        } catch (Exception e) {
            logger.error("Could not add MetaboliteAssignmentLines for: "+fileName);
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
