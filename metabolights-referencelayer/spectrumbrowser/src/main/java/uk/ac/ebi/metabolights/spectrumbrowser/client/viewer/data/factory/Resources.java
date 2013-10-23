/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

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
