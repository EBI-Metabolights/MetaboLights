package uk.ac.ebi.metabolights.spectrumbrowser.client.main.impl;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.metabolights.spectrumbrowser.client.common.EventBus;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.presenter.SprectrumListPresenter;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view.SpectrumListView;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view.SpectrumListViewImpl;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.interfaces.ClientFactory;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.presenter.ViewerPresenter;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view.ViewerView;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view.ViewerViewImpl;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ClientFactoryImpl implements ClientFactory {
    private static SimpleEventBus eventBus;
    private static SpectrumListView spectrumListView;
    private static ViewerView viewerView;

    private static VerticalPanel mainView;

    @Override
    public SimpleEventBus getSimpleEventBus() {
        if(eventBus==null){
            eventBus = new EventBus();
        }
        return eventBus;
    }

    private SpectrumListView getSpectrumListView() {
        if(spectrumListView==null){
            spectrumListView = new SpectrumListViewImpl();
            new SprectrumListPresenter(this.getSimpleEventBus(), spectrumListView);
        }
        return spectrumListView;
    }

    private ViewerView getViewerView(){
        if(viewerView==null){
            viewerView = new ViewerViewImpl();
            new ViewerPresenter(this.getSimpleEventBus(), viewerView);
        }
        return viewerView;
    }

    @Override
    public Widget getMainView() {
        if(mainView==null){
            mainView = new VerticalPanel();
            mainView.setSpacing(10);

            mainView.add(this.getSpectrumListView().asWidget());
            mainView.add(this.getViewerView().asWidget());

//            mainView.setStyleName("yourClassName");
        }
        return mainView;
    }
}
