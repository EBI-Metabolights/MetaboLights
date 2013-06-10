package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model.SpectrumItem;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model.SpectrumListData;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SpectrumListViewImpl implements SpectrumListView, ChangeHandler {
    private Presenter presenter;
    private ListBox container;

    public SpectrumListViewImpl() {
        this.container = new ListBox();
        this.container.setVisibleItemCount(1);
        this.container.addChangeHandler(this) ;
        //this.container.setStyleName("hola");
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Widget asWidget() {
        return this.container;
    }

    @Override
    public void setSpectrumListData(List<SpectrumItem> list) {
        for (SpectrumItem spectrumItem : list) {
            this.container.addItem(spectrumItem.getName(), spectrumItem.getId().toString());
        }
    }

    @Override
    public void setSelectedSpectrum(String id) {
        for (int i = 0; i < this.container.getItemCount(); i++) {
            String value = this.container.getValue(i);
            if(value.equals(id)){
                this.container.setSelectedIndex(i);
                return;
            }
        }
        //TODO: If you reach this line you haven't found the id in the list (ACTION NEEDED!)
    }

    @Override
    public void onChange(ChangeEvent changeEvent) {
        String id = this.container.getValue(this.container.getSelectedIndex());
        this.presenter.setSpectrumSelected(id);

    }
}
