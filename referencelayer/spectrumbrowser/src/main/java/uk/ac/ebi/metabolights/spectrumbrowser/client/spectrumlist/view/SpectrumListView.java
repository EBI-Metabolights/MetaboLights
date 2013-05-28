package uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.view;

import uk.ac.ebi.metabolights.spectrumbrowser.client.common.AppView;
import uk.ac.ebi.metabolights.spectrumbrowser.client.spectrumlist.model.SpectrumItem;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface SpectrumListView extends AppView<SpectrumListView.Presenter> {

    interface Presenter extends AppView.Presenter{
        public void setSpectrumSelected(String id);
    }

    public void setSpectrumListData(List<SpectrumItem> list);
    public void setSelectedSpectrum(String id);
}
