/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 16:08
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import com.csvreader.CsvWriter;
import org.isatools.isacreator.configuration.FieldObject;
import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class ConvertToMAF {

    MzTabFileWriter fileWriter = new MzTabFileWriter();
    ConfigurationReader configurationReader = new ConfigurationReader();

    private static int mapIsaStudyFieldName(TableReferenceObject tableReferenceObject, String sourceName){

        Collection<FieldObject> isaStudyFieldValue = tableReferenceObject.getFieldLookup().values();
        int colNo = 0;

        if(!isaStudyFieldValue.isEmpty()){
            for(FieldObject fieldValue: isaStudyFieldValue){
                if(fieldValue.getFieldName().equalsIgnoreCase(sourceName)){
                    colNo = fieldValue.getColNo();
                }
            }
        }

        return colNo;
    }


    public void convertMzTabToMAF(String mzTabfileName, String MetaboliteAssignmentFileName, String technology){

        try {

            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(MetaboliteAssignmentFileName, true), '\t');

            TableReferenceObject tableReferenceObject = fileWriter.getTableReferenceObject(technology);

            //http://www.csvreader.com/java_csv_samples.php

            Vector<String> headers = fileWriter.getHeaderVector(technology);

            //Write the header records
            for (String header : headers){
                csvOutput.write(header);
            }

            csvOutput.endRecord();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
