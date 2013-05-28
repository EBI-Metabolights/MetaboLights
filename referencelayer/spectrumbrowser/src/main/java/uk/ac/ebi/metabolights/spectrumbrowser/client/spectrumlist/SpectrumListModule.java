package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.events.SpectrumListItemSelectedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SpectrumListModule {

    public static final GwtEvent.Type<Handler> HANDLER_TYPE = new GwtEvent.Type<Handler>();

    public interface Handler extends EventHandler {
        public void onSpectrumListItemSelected(SpectrumListItemSelectedEvent event);
    }
}
