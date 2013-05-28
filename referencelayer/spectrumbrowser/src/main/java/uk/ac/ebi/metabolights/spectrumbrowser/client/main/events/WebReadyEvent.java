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
