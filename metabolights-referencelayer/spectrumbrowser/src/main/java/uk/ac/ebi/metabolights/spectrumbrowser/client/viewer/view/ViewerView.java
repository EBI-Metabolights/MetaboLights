/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 08/07/13 08:56
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

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
