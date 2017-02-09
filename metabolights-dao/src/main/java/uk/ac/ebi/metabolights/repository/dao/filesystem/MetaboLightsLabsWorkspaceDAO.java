package uk.ac.ebi.metabolights.repository.dao.filesystem;

import java.io.File;
import org.slf4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import net.minidev.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.MLLUser;
import uk.ac.ebi.metabolights.repository.model.MLLProject;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;

/**
 * Created by venkata on 22/08/2016.
 */
public class MetaboLightsLabsWorkspaceDAO {

    private final static Logger logger = LoggerFactory.getLogger(MetaboLightsLabsWorkspaceDAO.class.getName());
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
