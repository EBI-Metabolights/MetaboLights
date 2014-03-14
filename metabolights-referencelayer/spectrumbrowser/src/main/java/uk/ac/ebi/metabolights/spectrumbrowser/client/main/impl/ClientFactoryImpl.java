/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 08/07/13 08:56
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.main.impl;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.biowidgets.spectrum.client.SpectrumViewer;
import uk.ac.ebi.metabolights.spectrumbrowser.client.common.EventBus;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.interfaces.ClientFactory;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.presenter.SprectrumListPresenter;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view.SpectrumListView;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view.SpectrumListViewImpl;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.presenter.ViewerPresenter;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view.ViewerView;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view.ViewerViewImpl;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ClientFactoryImpl implements ClientFactory {
//    private static SimpleEventBus eventBus;
//    private static SpectrumListView spectrumListView;
//    private static ViewerView viewerView;
//
//    private static VerticalPanel mainView;
//
//    @Override
//    public SimpleEventBus getSimpleEventBus() {
//        if(eventBus==null){
//            eventBus = new EventBus();
//        }
//        return eventBus;
//    }
//
//    private SpectrumListView getSpectrumListView() {
//        if(spectrumListView==null){
//            spectrumListView = new SpectrumListViewImpl();
//            new SprectrumListPresenter(this.getSimpleEventBus(), spectrumListView);
//        }
//        return spectrumListView;
//    }
//
//    private ViewerView getViewerView(SpectrumViewer.SpectrumType spectrumType){
//        if(viewerView==null){
//            viewerView = new ViewerViewImpl();
//            viewerView.setSpectrumType(spectrumType);
//            new ViewerPresenter(this.getSimpleEventBus(), viewerView);
//        }
//        return viewerView;
//    }
//
//    @Override
//    public Widget getMainView(SpectrumViewer.SpectrumType spectrumType) {
//        if(mainView==null){
//            mainView = new VerticalPanel();
//            mainView.setSpacing(10);
//
//            mainView.add(this.getSpectrumListView().asWidget());
//            mainView.add(this.getViewerView(spe).asWidget());
//
////            mainView.setStyleName("yourClassName");
//        }
//        return mainView;
//    }
//private static SimpleEventBus eventBus;


    @Override
    public SimpleEventBus getSimpleEventBus() {

        SimpleEventBus eventBus = new EventBus();

        return eventBus;
    }

    private SpectrumListView getSpectrumListView(SimpleEventBus eventBus) {

        SpectrumListView spectrumListView = new SpectrumListViewImpl();
        new SprectrumListPresenter(eventBus, spectrumListView);
        return spectrumListView;
    }

    private ViewerView getViewerView(SpectrumViewer.SpectrumType spectrumType, SimpleEventBus eventBus){

        ViewerView viewerView = new ViewerViewImpl();
        viewerView.setSpectrumType(spectrumType);
        new ViewerPresenter(eventBus, viewerView);

        return viewerView;
    }

    @Override
    public Widget getMainView(SimpleEventBus eventBus, SpectrumViewer.SpectrumType spectrumType) {

        VerticalPanel mainView = new VerticalPanel();
        mainView.setSpacing(10);

        mainView.add(this.getSpectrumListView(eventBus).asWidget());
        mainView.add(this.getViewerView(spectrumType,eventBus).asWidget());

//            mainView.setStyleName("yourClassName");

        return mainView;
    }
}
