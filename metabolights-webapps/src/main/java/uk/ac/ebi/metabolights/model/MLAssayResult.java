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
