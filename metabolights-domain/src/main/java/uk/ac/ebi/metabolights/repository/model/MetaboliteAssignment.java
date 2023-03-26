/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/21/13 4:42 PM
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

public class MetaboliteAssignment {

    public String metaboliteAssignmentFileName;
    Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines;

    public String getMetaboliteAssignmentFileName() {
        return metaboliteAssignmentFileName;
    }

    public void setMetaboliteAssignmentFileName(String metaboliteAssignmentFileName) {
        this.metaboliteAssignmentFileName = metaboliteAssignmentFileName;
    }


    public Collection<MetaboliteAssignmentLine> getMetaboliteAssignmentLines() {
        return metaboliteAssignmentLines;
    }

    public void setMetaboliteAssignmentLines(Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines) {
        this.metaboliteAssignmentLines = metaboliteAssignmentLines;
    }

    public enum fieldNames {

        //setters vs maf column names
        identifier("identifier"),             //V1
        databaseIdentifier("database_identifier"),  //V2
        unitId("unit_id"),
        chemicalFormula("chemical_formula"),
        smiles("smiles"),
        inchi("inchi"),
        description("description"),       //V1
        metaboliteIdentification("metabolite_identification"),     //V2
        chemicalShift("chemical_shift"),
        multiplicity("multiplicity"),
        massToCharge("mass_to_charge"),
        fragmentation("fragmentation"),
        modifications("modifications"),
        charge("charge"),
        retentionTime("retention_time"),
        taxid("taxid"),
        species("species"),
        database("database"),
        databaseVersion("database_version"),
        reliability("reliability"),
        uri("uri"),
        searchEngine("search_engine"),
        searchEngineScore("search_engine_score"),
        smallmoleculeAbundanceSub("smallmolecule_abundance_sub"),
        smallmoleculeAbundanceStdevSub("smallmolecule_abundance_stdev_sub"),
        smallmoleculeAbundanceStdErrorSub("smallmolecule_abundance_std_error_sub");

        private final String name;

        private fieldNames(String toString) {
            this.name = toString;
        }

        public String toString() {
            return name;
        }

    }
}
