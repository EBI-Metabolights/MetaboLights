package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.presenter;

import com.google.gwt.event.shared.SimpleEventBus;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.MainModule;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.events.WebReadyEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.events.SpectrumListItemSelectedEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model.SpectrumItem;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model.SpectrumListData;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view.SpectrumListView;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SprectrumListPresenter implements SpectrumListView.Presenter, MainModule.Handler {
    private SimpleEventBus eventBus;
    private SpectrumListView view;
    private SpectrumListData data;
    private String selectedId = null;

    public SprectrumListPresenter(SimpleEventBus eventBus, SpectrumListView view) {
        this.eventBus = eventBus;
        this.view = view;
        this.bind();

        this.eventBus.addHandler(MainModule.HANDLER_TYPE, this);
    }

    @Override
    public void bind() {
        this.view.setPresenter(this);
    }

    @Override
    public void onWebReady(WebReadyEvent event) {

        this.data = new SpectrumListData(event.getInnerHTML());

        List<SpectrumItem> list = data.getSpectrumList();

        if(list!=null && !list.isEmpty()){
            this.view.setSpectrumListData(list);
            this.selectedId = list.get(0).getId().toString();
            this.view.setSelectedSpectrum(this.selectedId);

            SpectrumItem si = getSpectrumItemById(this.selectedId);
            this.eventBus.fireEvent(new SpectrumListItemSelectedEvent(si));
        }
    }

    @Override
    public void setSpectrumSelected(String id) {
        if(id==null) return;
        if(this.selectedId==null || !this.selectedId.equals(id)){
            this.selectedId = id;
            SpectrumItem si = getSpectrumItemById(id);

            this.eventBus.fireEvent(new SpectrumListItemSelectedEvent(si));
        }
    }

    private SpectrumItem getSpectrumItemById(String id){

        for(SpectrumItem si:data.getSpectrumList()){
            if(si.getId().toString().equals(id)) return si;
        }

        return null;

    }
}
