/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.main.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.MainModule;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class WebReadyEvent extends GwtEvent<MainModule.Handler> {
    private String innerHTML;

    public WebReadyEvent(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    @Override
    public Type<MainModule.Handler> getAssociatedType() {
        return MainModule.HANDLER_TYPE;
    }

    @Override
    protected void dispatch(MainModule.Handler handler) {
        handler.onWebReady(this);
    }

    public String getInnerHTML() {
        return innerHTML;
    }
}
