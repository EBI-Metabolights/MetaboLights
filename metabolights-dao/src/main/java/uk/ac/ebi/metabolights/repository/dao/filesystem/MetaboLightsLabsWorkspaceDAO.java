package uk.ac.ebi.metabolights.repository.dao.filesystem;

import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;

/**
 * Created by venkata on 22/08/2016.
 */
public class MetaboLightsLabsWorkspaceDAO {

    private MLLWorkSpace mllWorkSpace = null;

    public MetaboLightsLabsWorkspaceDAO(User user, String root) {
        this.mllWorkSpace = new MLLWorkSpace(user, root);
    }

    public MLLWorkSpace getMllWorkSpace() {
        return mllWorkSpace;
    }

    public void setMllWorkSpace(MLLWorkSpace mllWorkSpace) {
        this.mllWorkSpace = mllWorkSpace;
    }

}
