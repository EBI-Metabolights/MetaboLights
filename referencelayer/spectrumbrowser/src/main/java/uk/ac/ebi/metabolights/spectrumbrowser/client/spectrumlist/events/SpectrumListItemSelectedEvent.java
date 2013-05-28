package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.SpectrumListModule;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model.SpectrumItem;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SpectrumListItemSelectedEvent extends GwtEvent<SpectrumListModule.Handler> {
    private SpectrumItem si;

    public SpectrumListItemSelectedEvent(SpectrumItem si) {
        this.si = si;
    }

    @Override
    public Type<SpectrumListModule.Handler> getAssociatedType() {
        return SpectrumListModule.HANDLER_TYPE;
    }

    @Override
    protected void dispatch(SpectrumListModule.Handler handler) {
        handler.onSpectrumListItemSelected(this);
    }

    public SpectrumItem getSpectrumItem() {
        return si;
    }

    @Override
    public String toString() {
        return "SpectrumListItemSelected {" +
                "spectrumId='" + si.getId() + '\'' +
                "url='" + si.getUrl() + '\'' +
                '}';
    }
}
