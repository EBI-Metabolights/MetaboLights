/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SpectrumListData {
    //private static final String DATA_TAG = "spectrumbrowserdata";

    private List<SpectrumItem> spectrumItems;


    public SpectrumListData(String jsonItems){

        JSONObject data = JSONParser.parseStrict(jsonItems).isObject();
        JSONArray listAux = data.get("list").isArray();
        spectrumItems = new LinkedList<SpectrumItem>();
        for(int i=0; i<listAux.size(); ++i){
            JSONObject spectrumItem = listAux.get(i).isObject();
            spectrumItems.add(new SpectrumItem(spectrumItem));
        }

    }
    public List<SpectrumItem> getSpectrumList(){
        return spectrumItems;
    }
}
