package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.view;

import com.google.gwt.json.client.JSONObject;
import uk.ac.ebi.biowidgets.spectrum.client.SpectrumViewer;
import uk.ac.ebi.metabolights.spectrumbrowser.client.common.AppView;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ViewerView extends AppView<ViewerView.Presenter> {

    interface Presenter extends AppView.Presenter {

    }

    public void setSpectrumUrl(String id);
    public void setSpectrumType(SpectrumViewer.SpectrumType spectrumType);

}
