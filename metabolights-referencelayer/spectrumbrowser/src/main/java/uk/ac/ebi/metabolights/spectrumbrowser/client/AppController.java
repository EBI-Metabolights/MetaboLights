/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.events.WebReadyEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class AppController implements RunAsyncCallback {
    private HasWidgets container;
    private String innerHTML;
    private SimpleEventBus eventBus;
    private Widget mainView;

    public AppController(SimpleEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Initializes the managers, views and presenters and runs the web application
     * @param container the widget to put the web application view in
     */
    public void go(final HasWidgets container, String innerHTML, final Widget mainView) {
        this.container = container;
        this.innerHTML = innerHTML;
        this.mainView = mainView;
        // lazily initialize our views
        GWT.runAsync(this);
    }

    private void loadManagerModules() {

    }

    @Override
    public void onSuccess() {

        loadManagerModules();

        this.container.clear();
        this.container.add(this.mainView);

        eventBus.fireEvent(new WebReadyEvent(this.innerHTML));
    }

    @Override
    public void onFailure(Throwable reason) {
        //TODO
    }
}
