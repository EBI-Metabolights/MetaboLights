package uk.ac.ebi.metabolights.spectrumbrowser.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.biowidgets.spectrum.client.SpectrumViewer;
import uk.ac.ebi.metabolights.spectrumbrowser.client.common.EventBus;
import uk.ac.ebi.metabolights.spectrumbrowser.client.main.interfaces.ClientFactory;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SpectrumBrowser implements EntryPoint {
    // IMPORTANT! ATTENTION!
    // Do NOT use the "module rename-to" for the place holder ( but it is case sensitive :D )
    private static final String PLACE_HOLDER_NMR = "spectrumbrowser";
    private static final String PLACE_HOLDER_MS = "spectrumbrowserms";

    public void onModuleLoad() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                ClientFactory clientFactory = GWT.create(ClientFactory.class);


                // Set up NMR content
                //Search for NMR stuff
                AbsolutePanel placeHolder = RootPanel.get(PLACE_HOLDER_NMR);

                if (placeHolder != null){

                    SimpleEventBus eventBus = clientFactory.getSimpleEventBus();
                    AppController appController = new AppController(eventBus);
                    String innerHTML = placeHolder.getElement().getInnerHTML();
                    placeHolder.getElement().setInnerHTML("");
                    appController.go(placeHolder, innerHTML, clientFactory.getMainView(eventBus, SpectrumViewer.SpectrumType.NMR));
                }

                // Search for MS content
                AbsolutePanel placeHolderMS = RootPanel.get(PLACE_HOLDER_MS);

                if (placeHolderMS != null){

                    SimpleEventBus eventBusMS = clientFactory.getSimpleEventBus();
                    AppController appControllerMS = new AppController(eventBusMS);
                    String innerHTMLMS = placeHolderMS.getElement().getInnerHTML();
                    placeHolderMS.getElement().setInnerHTML("");
                    appControllerMS.go(placeHolderMS, innerHTMLMS, clientFactory.getMainView(eventBusMS, SpectrumViewer.SpectrumType.MS));
                }

            }
        });
    }
}

