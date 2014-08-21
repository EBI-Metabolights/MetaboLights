/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/7/13 4:02 PM
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

package uk.ac.ebi.metabolights.model;

import uk.ac.ebi.bioinvindex.model.Metabolite;
import uk.ac.ebi.metabolights.webapp.MetaboLightsUtilities;

import java.util.Collection;
import java.util.Map;

public class MetaboliteGUI {
	private Metabolite met;

	public MetaboliteGUI(Metabolite met) {
		this.met = met;
	}
	public Metabolite getMetabolite(){
		return this.met;
	}
	public String getLink(){
		
		String link = met.getChebiId()==null?met.getIdentifier():met.getChebiId();
		return MetaboLightsUtilities.getLink(link);
	}
    public String getIdentifier() {
        if (met.getChebiId() == null){
            return met.getIdentifier();
        } else {
            return met.getChebiId();
        }
    }
}
