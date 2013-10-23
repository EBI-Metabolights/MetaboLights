/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 08/07/13 08:56
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.main.interfaces;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.biowidgets.spectrum.client.SpectrumViewer;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ClientFactory {
    public SimpleEventBus getSimpleEventBus();
    public Widget getMainView(SimpleEventBus eventBus, SpectrumViewer.SpectrumType spectrumType);
}
