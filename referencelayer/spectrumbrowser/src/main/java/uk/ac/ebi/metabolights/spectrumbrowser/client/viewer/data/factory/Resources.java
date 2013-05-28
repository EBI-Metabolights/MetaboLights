package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface Resources extends ClientBundle {

    Resources INSTANCE = (Resources) GWT.create(Resources.class);

    @ClientBundle.Source("resources/data.json")
    TextResource responseText();
}
