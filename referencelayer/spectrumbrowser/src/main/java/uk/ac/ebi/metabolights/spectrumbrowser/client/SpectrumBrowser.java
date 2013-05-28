package uk.ac.ebi.metabolights.spectrumbrowser.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.interfaces.ClientFactory;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SpectrumBrowser implements EntryPoint {
    // IMPORTANT! ATTENTION!
    // Do NOT use the "module rename-to" for the place holder ( but it is case sensitive :D )
    private static final String PLACE_HOLDER = "spectrumbrowser";

    public void onModuleLoad() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                ClientFactory clientFactory = GWT.create(ClientFactory.class);
                AppController appController = new AppController(clientFactory.getSimpleEventBus());
                AbsolutePanel placeHolder = RootPanel.get(PLACE_HOLDER);
                String innerHTML = placeHolder.getElement().getInnerHTML();
                placeHolder.getElement().setInnerHTML("");
                appController.go(placeHolder, innerHTML, clientFactory.getMainView());
            }
        });
    }
}

