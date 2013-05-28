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
