/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

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
