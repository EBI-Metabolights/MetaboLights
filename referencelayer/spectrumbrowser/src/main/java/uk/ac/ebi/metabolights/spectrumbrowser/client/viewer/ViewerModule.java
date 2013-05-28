package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ViewerModule {

    public static final GwtEvent.Type<Handler> HANDLER_TYPE = new GwtEvent.Type<Handler>();

    public interface Handler extends EventHandler {

    }
}
