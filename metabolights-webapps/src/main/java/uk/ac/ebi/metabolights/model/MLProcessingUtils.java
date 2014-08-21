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

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Data;
import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.processing.DataNode;
import uk.ac.ebi.bioinvindex.model.processing.GraphElement;
import uk.ac.ebi.bioinvindex.utils.processing.ExperimentalPipelineVisitor;
import uk.ac.ebi.bioinvindex.utils.processing.ProcessingVisitAction;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: conesa
 * Date: 07/02/2013
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 * Based on Eamons code: https://gist.github.com/eamonnmag/4731752
 */

public class MLProcessingUtils {

    /**
     * An helper that finds all the AssayResults related to the assay parameter. It is based on the experimental pipeline
     * the assay's material belong in.
     */
    public static Collection<AssayResult> findAllDataInAssay ( Assay assay )
    {
        if ( assay == null )
            throw new RuntimeException ( "findAllDataInAssay(): null assay passed to the method" );

        Material material = assay.getMaterial ();
        if ( material == null )
            throw new RuntimeException ( "findAllDataInAssay( " + assay + "): no material associated to the assay" );

        final Study study = assay.getStudy ();
        if ( study == null )
            throw new RuntimeException ( "findAllDataInAssay ( " + assay + " ): no study associated to the assay" );

        final Collection<AssayResult> result = new HashSet<AssayResult>();
        ProcessingVisitAction visitor = new ProcessingVisitAction ()
        {
            public boolean visit ( GraphElement graphElement )
            {
                if ( ! ( graphElement instanceof DataNode) ) return true;
                Data data = ( (DataNode) graphElement ).getData ();
                result.add(new AssayResult(data, study));
                return true;
            }
        };

        new ExperimentalPipelineVisitor( visitor ).visitForward(material.getMaterialNode());
        return result;
    }

}
