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

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 18/09/13
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class Ontology {

    private String splitName[];

    private String name;
    private String ontology;


    public String getName(String name) {

        if(name.contains(":")){
            splitName = name.split(":");
            this.name = splitName[1];
            return this.name;
        } else {
            return name;
        }


    }

    public String getOntology(String ontology) {
        if(ontology.contains(":")){
            splitName = ontology.split(":");
            this.ontology = splitName[0];
            return this.ontology;
        } else {
            return ontology;
        }

    }
}
