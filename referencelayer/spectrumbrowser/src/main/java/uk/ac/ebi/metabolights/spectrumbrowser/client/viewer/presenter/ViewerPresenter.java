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
