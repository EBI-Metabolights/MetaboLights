/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2017-Jan-13
 * Modified by:   kenneth
 *
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.utils.mafsplitter.MAFSplitter;

/**
 * Created by kenneth on 13/01/2017.
 */
public class MAFSplitterTest extends TestCase {

    MAFSplitter mafSplitter = new MAFSplitter();
    String studyDirectory;

    @Before
    public void runBeforeEveryTest() {
        studyDirectory = MAFSplitterTest.class.getClassLoader().getResource(".").getPath();
    }


    @Test
    public void testMAFSplitter(){
        if (studyDirectory == null)
            studyDirectory = MAFSplitterTest.class.getClassLoader().getResource(".").getPath();
        //String testStudy = "/MTBLS313/m_imagingms_metabolite_profiling_mass_spectrometry_v2_maf2.tsv";
        String testStudy = "MTBLS313/m_imagingms_metabolite_profiling_mass_spectrometry_v2_maf.tsv";

        mafSplitter.convertMAF(studyDirectory + testStudy);
    }

}
