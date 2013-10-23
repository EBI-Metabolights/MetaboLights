/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.presenter;

import com.google.gwt.event.shared.SimpleEventBus;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.SpectrumListModule;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.events.SpectrumListItemSelectedEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view.ViewerView;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ViewerPresenter implements ViewerView.Presenter, SpectrumListModule.Handler {
    protected SimpleEventBus eventBus;
    private ViewerView view;

    public ViewerPresenter(SimpleEventBus eventBus, ViewerView view) {
        this.eventBus = eventBus;
        this.view = view;
        this.bind();

        this.eventBus.addHandler(SpectrumListModule.HANDLER_TYPE, this);
    }

    @Override
    public void bind() {
        this.view.setPresenter(this);
    }

    @Override
    public void onSpectrumListItemSelected(SpectrumListItemSelectedEvent event) {
        this.view.setSpectrumUrl(event.getSpectrumItem().getUrl());
    }
}
