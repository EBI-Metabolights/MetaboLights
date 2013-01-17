/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsParametersDAO.java
 *
 * Last modified: 1/17/13 10:53 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.dao;

import uk.ac.ebi.metabolights.model.MetaboLightsParameters;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 17/01/2013
 * Time: 10:53
 */
public interface MetaboLightsParametersDAO {

    public MetaboLightsParameters getOnName(String parameterName);

    public List<MetaboLightsParameters> getAll();

    public void update(MetaboLightsParameters metaboLightsParameters);
    public void insert(MetaboLightsParameters metaboLightsParameters);
    public void delete(MetaboLightsParameters metaboLightsParameters);
}
