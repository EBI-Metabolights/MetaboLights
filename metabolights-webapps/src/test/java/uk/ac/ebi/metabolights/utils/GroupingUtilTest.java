/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/7/13 3:52 PM
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

package uk.ac.ebi.metabolights.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.Species;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: conesa
 * Date: 07/05/2013
 * Time: 14:38
 */
public class GroupingUtilTest {


    @Test
    public void testGroupingUtils(){


        Collection<MetSpecies> metSpeciesCollection = new ArrayList<MetSpecies>();


        Species sp1 = new Species();
        sp1.setSpecies("Species1");


        Species sp2 = new Species();
        sp2.setSpecies("Species2");


        // Add test data
        // First cluster, sp2
        MetSpecies ms1 = new MetSpecies(new Long (1),sp1,null);
        MetSpecies ms2 = new MetSpecies(new Long (2),sp1,null);
        MetSpecies ms3 = new MetSpecies(new Long (3),sp1,null);


        MetSpecies ms4 = new MetSpecies(new Long (4),sp2,null);
        MetSpecies ms5 = new MetSpecies(new Long (5),sp2,null);


        // Add items to the collection
        metSpeciesCollection.add(ms1);
        metSpeciesCollection.add(ms2);
        metSpeciesCollection.add(ms3);
        metSpeciesCollection.add(ms4);
        metSpeciesCollection.add(ms5);


        // Instantiate the Grouping tool
        GroupingUtil gu = new GroupingUtil(metSpeciesCollection,"getSpecies", MetSpecies.class);

        assertEquals("Groups must be 2", 2, gu.getGroupedCol().size());


        // Get the first group: sp1
        Collection<MetSpecies> msc = (Collection<MetSpecies>) gu.getGroupedCol().get(sp1);
        assertEquals("Items in first group count", 3, msc.size());


        // Get the second group: sp2
        msc = (Collection<MetSpecies>) gu.getGroupedCol().get(sp2);
        assertEquals("Items in second group count", 2, msc.size());










    }

}
