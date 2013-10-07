/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 16:07
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class MzTabFileWriter {

    public MzTabFileWriter(){}

    ConfigurationReader configurationReader = new ConfigurationReader();

    public void writeMzTab(String fileName, String fileContext) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fileContext);
        fileWriter.close();
    }

    public void writeMAF(String fileName, String fileContext) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fileContext);
        fileWriter.close();
    }


    public TableReferenceObject getTableReferenceObject(String technology){

        TableReferenceObject tableReferenceObject = null;

        if (technology.equals(configurationReader.MS))
            tableReferenceObject = configurationReader.getMSConfig();
        else
            tableReferenceObject = configurationReader.getNMRConfig();

        return tableReferenceObject;
    }



    public Vector<String> getHeaderVector(String technology){
        TableReferenceObject tableReferenceObject = getTableReferenceObject(technology);
        Vector<String> headers = tableReferenceObject.getHeaders();
        headers.remove(0); // Skip the internal "Row No.", always first

        return headers;
    }

    public String getColumnHeaderNames(String technology){
        String tabChar="\t";
        String allHeaders = "";

        Vector<String> headers = getHeaderVector(technology);

        for (String header : headers){
            allHeaders = allHeaders + header + tabChar;
        }

        return allHeaders;
    }


}
