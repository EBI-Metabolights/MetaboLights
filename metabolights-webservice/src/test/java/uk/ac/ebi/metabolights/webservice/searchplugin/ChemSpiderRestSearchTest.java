/*
 * EBI MetaboLights - https://www.ebi.ac.uk/metabolights
 * Metabolomics team
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2018-Nov-28
 * Modified by:   kalai
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.searchplugin;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChemSpiderRestSearchTest extends TestCase {

    public static final String TEST_INCHI = "InChI=1S/C15H15NO6/c17-11-7-21-15(20)12(11)22-13(18)14(15,19)5-8-6-16-10-4-2-1-3-9(8)10/h1-4,6,11-12,16-17,19-20H,5,7H2/t11-,12+,14?,15-/m0/s1";

    @Test
    public void testCall() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderRestSearch(TEST_INCHI, SearchTermCategory.INCHI));
        executor.shutdown();
        Collection<CompoundSearchResult> results = chemSpiderResults.get();
        assertEquals(chemSpiderResults.get().size(),1);
    }
}