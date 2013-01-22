/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsStatsServiceImpl.java
 *
 * Last modified: 1/21/13 11:33 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.dao.MetaboLightsStatsDAO;
import uk.ac.ebi.metabolights.model.MLStats;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/01/2013
 * Time: 11:33
 */
@Service
public class MetaboLightsStatsServiceImpl implements MetaboLightsStatsService {

    @Autowired
    private MetaboLightsStatsDAO metaboLightsStatsDAO;

    @Override
    public List<MLStats> getByPageCategory(String categoryName) {
        List<MLStats> statsList = metaboLightsStatsDAO.getByCategory(categoryName);

        return statsList;
    }

    @Override
    public List<MLStats> getAll() {
        List<MLStats> statsList = metaboLightsStatsDAO.getAll();

        return statsList;
    }
}
