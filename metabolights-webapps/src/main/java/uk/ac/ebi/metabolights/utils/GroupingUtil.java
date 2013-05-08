package uk.ac.ebi.metabolights.utils;

/**
 * User: conesa
 * Date: 02/05/2013
 * Time: 16:34
 */


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class GroupingUtil <I,K>  {

    private Multimap<K,I> sourceCol = ArrayListMultimap.create();
    private Map<K,Collection<I>> groupedCol;

    private String clusteringMethod;
    private Class itemClass;
    public GroupingUtil  (Collection<I> sourceCol, String clusteringMethod, Class itemClass){

        this.clusteringMethod = clusteringMethod;
        this.itemClass = itemClass;

        group(sourceCol);

    }

    public Map<K, Collection<I>> getGroupedCol() {
        return groupedCol;
    }

    public String getClusteringMethod() {
        return clusteringMethod;
    }

    public Class getItemClass() {
        return itemClass;
    }


    private void group(Collection<I> sourceCol){

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

                // Add this object to the multimap
                this.sourceCol.put(value, item);


            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

        //Create the map...
        groupedCol = this.sourceCol.asMap();

    }

}
