package uk.ac.ebi.metabolights.spectrumbrowser.client.main.interfaces;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ClientFactory {
    public SimpleEventBus getSimpleEventBus();
    public Widget getMainView();
}
