/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Mar-30
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.utils.ddi.ReadMTBLS;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by kenneth on 30/03/15.
 */
public class ReadMTBLSTest {

    private ReadMTBLS readMTBLS = null;

    @Before
    public void setUp(){

    if (readMTBLS == null)
        readMTBLS = new ReadMTBLS();

    }

    @Test
    public void testGetStudies(){
        List<Study> studyList = readMTBLS.getAllStudies();
        assertNotNull("StudyList should not be null", studyList);

    }
}
