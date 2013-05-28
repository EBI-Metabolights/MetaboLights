package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.biowidgets.spectrum.client.SpectrumViewer;
import uk.ac.ebi.biowidgets.spectrum.data.PeakList;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.NMRSpectraData;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.NMRSpectraDataImpl;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.proxy.NMRPeakListAdapter;

import java.util.*;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ViewerViewImpl implements ViewerView {
    private Presenter presenter;
    private HTMLPanel container;
    private Map<String, SpectrumViewer> map = new HashMap<String, SpectrumViewer>();

    public ViewerViewImpl() {
        this.container = new HTMLPanel("");
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
    public void setSpectrumUrl(String url) {
        if(map.containsKey(url)){
            this.show(map.get(url));
        }else{

            gwtGetHttp(url);

        }
    }

    private void gwtGetHttp(final String url ) {

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {

                    //TODO displayError("Request error:"+exception.getMessage());
                    System.out.println(exception.getMessage());
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {

//                        try {

                            NMRSpectraData peakList = jsonToSpectra(response.getText());


                            NMRPeakListAdapter spectrum = new NMRPeakListAdapter(peakList);

                            List<PeakList> spectrumList = new ArrayList<PeakList>();

                            spectrumList.add(spectrum);

                            SpectrumViewer spectrumViewer = new SpectrumViewer(spectrumList, SpectrumViewer.SpectrumType.NMR);
                            map.put(url, spectrumViewer);
                            show(spectrumViewer);


//                        } catch (ModelFactoryException e) {
//                            System.err.println(e.getMessage());
//                        }
                    } else {
                        String res = response.getStatusText();
                        System.err.println(res);
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
            // TODO displayError("Fatal error: "+e.getMessage());
        }
    }

    private NMRSpectraData jsonToSpectra(String text) {
        // It doesnt' work.
        //final NMRSpectraData peakList = ModelFactory.getModelObject(NMRSpectraData.class, response.getText());

        return jsonToSpectraJSONObject(text);


    }

    private NMRSpectraData jsonToSpectraJSONObject(String text) {

        JSONValue jsonValue = JSONParser.parseStrict(text);
        JSONObject aux  = jsonValue.isObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.set(0, aux) ;

        return  createPeakList(jsonArray);
    }


    private NMRSpectraData createPeakList(JSONArray jsonArray) {

        NMRSpectraData nmrSpectraData = new NMRSpectraDataImpl();
        List<Double> intensities = new ArrayList<Double>();

        JSONObject jsObject;
        JSONValue jsVal;
        JSONArray jsData;
        JSONNumber jsMinX = null;
        JSONNumber jsMaxX = null;


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

            nmrSpectraData.setxMax((float) jsMaxX.doubleValue());
            nmrSpectraData.setxMin((float) jsMinX.doubleValue());
//            //NMRPeakListAdapter nmrPickList = new NMRPeakListAdapter(jsMinX.doubleValue(), jsMaxX.doubleValue());


            if ((jsVal = jsObject.get("data")) == null) {
                continue;
            }
            if ((jsData = jsVal.isArray()) == null) {
                continue;
            }

            for (int j = 0; j < jsData.size(); j++) {
                JSONValue value = jsData.get(j);
                if (value != null) {
                    JSONNumber dataPoint = value.isNumber();
                    if(dataPoint!=null){
                        intensities.add(dataPoint.doubleValue());
                    }
                }
            }

            Double[] doublesArray = intensities.toArray(new Double[intensities.size()]);
            nmrSpectraData.setData(doublesArray);

        }

        return nmrSpectraData;
    }
    private void show(SpectrumViewer spectrumViewer){
        this.container.clear();
        this.container.add(spectrumViewer);
    }

}
