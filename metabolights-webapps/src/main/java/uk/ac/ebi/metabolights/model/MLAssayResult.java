/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2/12/13 11:55 AM
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

package uk.ac.ebi.metabolights.model;

import uk.ac.ebi.bioinvindex.model.Annotation;
import uk.ac.ebi.bioinvindex.model.AssayResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: conesa
 * Date: 07/02/2013
 * Time: 11:33
 * Wrapper for the ISAtab model AssayRessult class to have some items ready for JSP, like files.
 */

public class MLAssayResult {

    private AssayResult ar;

    public List<Annotation> getFiles() {
        return files;
    }

    public AssayResult getAssayResult() {
        return ar;
    }

    private List<Annotation> files;
    public MLAssayResult (AssayResult ar){
        this.ar = ar;
        populateFiles();
    }

    private void populateFiles(){


        

        //files = ar.getData().getAnnotation("NMR Spectral Raw Data");

    }

}
