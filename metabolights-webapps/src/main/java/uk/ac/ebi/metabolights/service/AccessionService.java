/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/29/14 12:34 PM
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
 * EBI MetaboLights Project - 2011.
 *
 * File: AccessionService.java
 *
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.model.MetaboLightsStudyXRef;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 20/09/2011
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public interface AccessionService {

    public String getAccessionNumber();
    public String getDefaultPrefix ();
    public void setDefaultPrefix(String defaultPrefix);
    public MetaboLightsStudyXRef getSubmittedId(String studyAcc);
	public List<MetaboLightsStudyXRef> getStudyXRefs(String studyAcc);
    public void saveSubmittedId(String orgId, String studyAcc);

}
