/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.main;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.events.WebReadyEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public abstract class MainModule {
    public static final GwtEvent.Type<Handler> HANDLER_TYPE = new GwtEvent.Type<Handler>();

    public interface Handler extends EventHandler {
        void onWebReady(WebReadyEvent event);
    }
}
