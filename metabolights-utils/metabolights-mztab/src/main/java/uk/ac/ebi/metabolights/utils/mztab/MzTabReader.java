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

import uk.ac.ebi.pride.jmztab.model.MZTabFile;
import uk.ac.ebi.pride.jmztab.model.Metadata;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class MzTabReader {

    public MZTabFile readMzTab(String mzTabfileName){
        File inputFile = new File(mzTabfileName);
        Metadata metadata = new Metadata();
        MZTabFile mzTabFile = new MZTabFile(metadata);

        try {
            //mzTabFile.

        } catch (Exception e) {
            e.printStackTrace();  //TODO
        }

        return mzTabFile;
    }


    public Collection<SmallMolecule> getSmallMolecules(String mzTabfileName){

        Collection<SmallMolecule> smallMolecules = new ArrayList<SmallMolecule>();
        MZTabFile mzTabFile = readMzTab(mzTabfileName);
        smallMolecules = mzTabFile.getSmallMolecules();
        return smallMolecules;

    }

    public File readMAF(String fileName){
        File file = new File(fileName);
        return file;
    }

}
