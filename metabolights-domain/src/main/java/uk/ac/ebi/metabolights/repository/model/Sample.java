/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/16/13 11:03 AM
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

package uk.ac.ebi.metabolights.repository.model;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 17/09/13
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
public class Sample {
    private String sourceName;
    private String charactersticsOrg;
    private String charactersticsOrgPart;
    private String protocolRef;
    private String sampleName;

    private Collection<Factors> factors;

    public Collection<Factors> getFactors() {
        return factors;
    }

    public void setFactors(Collection<Factors> factors) {
        this.factors = factors;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCharactersticsOrg() {
        return charactersticsOrg;
    }

    public void setCharactersticsOrg(String charactersticsOrg) {
        this.charactersticsOrg = charactersticsOrg;
    }

    public String getCharactersticsOrgPart() {
        return charactersticsOrgPart;
    }

    public void setCharactersticsOrgPart(String charactersticsOrgPart) {
        this.charactersticsOrgPart = charactersticsOrgPart;
    }

    public String getProtocolRef() {
        return protocolRef;
    }

    public void setProtocolRef(String protocolRef) {
        this.protocolRef = protocolRef;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }
}
