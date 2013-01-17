/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsParametersService.java
 *
 * Last modified: 1/17/13 10:46 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.service;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 17/01/2013
 * Time: 10:46
 */
public interface MetaboLightsParametersService {

    //Gets the value based on the name, name is also primary key
    public MetaboLightsParameters getMetaboLightsParametersOnName(String parameterName);

    //Get all parameters
    public List<MetaboLightsParameters> getAll();

    //Update existing parameters
    @Transactional
    public void update(MetaboLightsParameters metaboLightsParameters);

    //Insert new parameters
    @Transactional
    public void insert(MetaboLightsParameters metaboLightsParameters);

    //Delete a parameter
    @Transactional
    public void delete(MetaboLightsParameters metaboLightsParameters);

}
