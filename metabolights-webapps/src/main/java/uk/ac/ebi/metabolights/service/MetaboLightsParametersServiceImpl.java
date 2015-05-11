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

package uk.ac.ebi.metabolights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.dao.MetaboLightsParametersDAO;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;

import java.util.List;


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
