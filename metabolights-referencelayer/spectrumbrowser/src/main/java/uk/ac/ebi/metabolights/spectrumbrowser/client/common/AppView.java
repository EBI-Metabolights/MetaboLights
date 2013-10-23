/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.common;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface AppView<P> extends IsWidget {

    interface Presenter {
        public void bind();
    }

    void setPresenter(P presenter);
}
