/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/7/13 5:28 PM
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

/**
 * User: conesa
 * Date: 02/05/2013
 * Time: 16:34
 */


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupingUtil<I,K>  {

    private HashMap<K,ArrayList<I>> groupedCol = new HashMap<>();

    private String clusteringMethod;
    private Class itemClass;
    public GroupingUtil(ArrayList<I> sourceCol, String clusteringMethod, Class itemClass){

        this.clusteringMethod = clusteringMethod;
        this.itemClass = itemClass;

        group(sourceCol);

    }

    public HashMap<K, ArrayList<I>> getGroupedCol() {
        return groupedCol;
    }

    private void group(ArrayList<I> sourceCol){

        // Get the member to call
        java.lang.reflect.Method method;

        try {
            method = itemClass.getMethod(clusteringMethod);

        } catch (SecurityException e) {
            // ...
            e.printStackTrace();
            return;
        } catch (NoSuchMethodException e) {
            // ...
            e.printStackTrace();
            return;
        }

        // For each item in the collection...
        for (I item: sourceCol){

            // Call the method
            try {

                // Get the value invoking the method
                K value = (K)method.invoke(item);

                // Get the entry fot that value
                ArrayList<I> collection = groupedCol.get(value);

                if (collection == null){

                    collection = new ArrayList<I>();
                    groupedCol.put(value,collection);
                }

                collection.add(item);


            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

    }

}
