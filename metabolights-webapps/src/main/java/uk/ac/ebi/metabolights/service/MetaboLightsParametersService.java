/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 1/17/13 4:49 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
