/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsParametersServiceImpl.java
 *
 * Last modified: 1/17/13 10:52 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.dao.MetaboLightsParametersDAO;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 17/01/2013
 * Time: 10:52
 */

@Service
public class MetaboLightsParametersServiceImpl implements MetaboLightsParametersService {

    @Autowired
    private MetaboLightsParametersDAO metaboLightsParametersDAO;

    @Override
    public MetaboLightsParameters getMetaboLightsParametersOnName(String parameterName) {
        MetaboLightsParameters metaboLightsParameters= metaboLightsParametersDAO.getOnName(parameterName);

        if (metaboLightsParameters == null)
            metaboLightsParameters = new MetaboLightsParameters();

        return metaboLightsParameters;
    }

    @Override
    public List<MetaboLightsParameters> getAll() {
        return metaboLightsParametersDAO.getAll();
    }

    @Override
    public void update(MetaboLightsParameters metaboLightsParameters) {
        metaboLightsParametersDAO.update(metaboLightsParameters);
    }

    @Override
    public void insert(MetaboLightsParameters metaboLightsParameters) {
        metaboLightsParametersDAO.insert(metaboLightsParameters);
    }

    @Override
    public void delete(MetaboLightsParameters metaboLightsParameters) {
        metaboLightsParametersDAO.delete(metaboLightsParameters);
    }
}
