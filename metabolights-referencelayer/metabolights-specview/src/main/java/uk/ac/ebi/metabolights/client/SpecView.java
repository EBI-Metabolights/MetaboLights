/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 09/09/13 12:20
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//import uk.ac.ebi.pride.widgets.client.spectrum.client.SpectrumViewer;
import uk.ac.ebi.biowidgets.spectrum.client.SpectrumViewer;
import uk.ac.ebi.biowidgets.spectrum.data.PeakList;
//import uk.ac.ebi.pride.widgets.client.spectrum.data.PeakList;
//import uk.ac.ebi.biowidgets.spectrum.model.Spectrum;

//
/**
 * Entry point classes define
 * <code>onModuleLoad()</code>.
 */
public class SpecView implements EntryPoint {

    /**
     * The message displayed to the user when the server cannot be reached or returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final Messages messages = GWT.create(Messages.class);
    private Label errorMsgLabel = new Label();
    private List<PeakList> peaksList;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button();//messages.sendButton() );
        final TextBox nameField = new TextBox();
        nameField.setText("Name");//messages.nameField() );
        final Label errorLabel = new Label();

        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        // To get the parameters
        // String value = Window.Location.getParameter("param");

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
//        RootPanel.get("nameFieldContainer").add(nameField);
//        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);
        nameField.selectAll();

        // Create the popup dialog box
        /*
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // assemble main panel
        errorMsgLabel.setStyleName("errorMessage");
        errorMsgLabel.setVisible(false);
        * */



        gwtGetHttp();
    }

    protected void createSpectrumViewer(){
        SpectrumViewer spectrumViewer = new SpectrumViewer(peaksList, 920, 290, SpectrumViewer.SpectrumType.NMR);
        RootPanel.get("viewerContainer").add(spectrumViewer);
    }

    private void gwtGetHttp() {
        String url = "/nmr-rest/nmrSpectra/ID50/ID60/nmrspec";

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
        //builder.setHeader("Accept", "application/json");
        try {
           Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    displayError("Request error:"+exception.getMessage());
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        JSONValue jsonValue = JSONParser.parseStrict(response.getText());
                        JSONObject aux  = jsonValue.isObject();
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.set(0, aux) ;

                        createPeakList(jsonArray);
                        createSpectrumViewer();
                    } else {
                        String res = response.getStatusText();
                        displayError("Error: "+res);
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
            displayError("Fatal error: "+e.getMessage());
        }
    }

    private void displayError(String error) {
        errorMsgLabel.setText(error);
        errorMsgLabel.setVisible(true);
    }

    private void createPeakList(JSONArray jsonArray) {
        //this.peaksList = new ArrayList<PeakList>();
        this.peaksList = new ArrayList<PeakList>();

        JSONObject jsObject;
        JSONValue jsVal;
        JSONArray jsData;
        JSONNumber jsMinX = null;
        JSONNumber jsMaxX = null;

        Discretizer disc;
        for (int i = 0; i < jsonArray.size(); i++) {
            if ((jsObject = jsonArray.get(i).isObject()) == null) {
                continue;
            }

            if (jsMinX == null) {
                if ((jsVal = jsObject.get("xMin")) == null) {
                    continue;
                }
                if ((jsMinX = jsVal.isNumber()) == null) {
                    continue;
                }
            }

            if (jsMaxX == null) {
                if ((jsVal = jsObject.get("xMax")) == null) {
                    continue;
                }
                if ((jsMaxX = jsVal.isNumber()) == null) {
                    continue;
                }
            }

            NMRPeakListAdapter nmrPickList = new NMRPeakListAdapter(jsMinX.doubleValue(), jsMaxX.doubleValue());

            if ((jsVal = jsObject.get("data")) == null) {
                continue;
            }
            if ((jsData = jsVal.isArray()) == null) {
                continue;
            }
            List<NMRPeakAdapter> peaks = new ArrayList<NMRPeakAdapter>();

            disc = new Discretizer(jsMinX.doubleValue(), jsMaxX.doubleValue(), jsData.size());
            for (int j = 0; j < jsData.size(); j++) {
                JSONValue value = jsData.get(j);
                if (value != null) {
                    JSONNumber dataPoint = value.isNumber();
                    if(dataPoint!=null){
                        peaks.add(new NMRPeakAdapter(dataPoint.doubleValue(), disc.valueAtPoint(j)));
                    }
                }
            }

            nmrPickList.set(peaks);
            this.peaksList.add(nmrPickList);
        }
    }
}
