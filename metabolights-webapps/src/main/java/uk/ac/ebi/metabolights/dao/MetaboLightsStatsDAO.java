/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsStatsDAO.java
 *
 * Last modified: 1/21/13 11:10 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.dao;

import uk.ac.ebi.metabolights.model.MLStats;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/01/2013
 * Time: 11:10
 */
public interface MetaboLightsStatsDAO {
    public List<MLStats> getByCategory(String categoryName);
    public List<MLStats> getAll();
}
