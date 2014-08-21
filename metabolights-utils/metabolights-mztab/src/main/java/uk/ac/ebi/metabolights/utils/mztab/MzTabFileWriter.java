/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/5/13 10:41 AM
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

package uk.ac.ebi.metabolights.utils.mztab;

import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;
import uk.ac.ebi.pride.jmztab.model.MZTabFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class MzTabFileWriter {

    public MzTabFileWriter(){}

    ConfigurationReader configurationReader = new ConfigurationReader();

    public void writeMzTab(String fileName, MZTabFile mzTabFile) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write(mzTabFile.toString());                 //Add the metadata

        //MZTabColumnFactory factory = mzTabFile.getSmallMoleculeColumnFactory();
        //fileWriter.append(factory.toString());  //Add column headers

        //for (SmallMolecule smallMolecule : mzTabFile.getSmallMolecules())  {
        //    fileWriter.append(smallMolecule.toString());        // Add column data
        //}

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
