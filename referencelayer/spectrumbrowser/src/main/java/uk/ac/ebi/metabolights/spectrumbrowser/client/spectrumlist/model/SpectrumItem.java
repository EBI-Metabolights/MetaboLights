package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model;

import com.google.gwt.json.client.JSONObject;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SpectrumItem {
    private Integer id;
    private String name;
    private String url;

    public SpectrumItem(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url= url;
    }


    public SpectrumItem(JSONObject jsonObject) {
        if(jsonObject.containsKey("id")){
            this.id = (int) jsonObject.get("id").isNumber().doubleValue();
        }
        if(jsonObject.containsKey("name")){
            this.name = jsonObject.get("name").isString().stringValue();
        }
        if(jsonObject.containsKey("url")){
            this.url = jsonObject.get("url").isString().stringValue();
        }

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


}
