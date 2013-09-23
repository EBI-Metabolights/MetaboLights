/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 23/09/13 13:25
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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


    //Public class for each MAF line
    public static class MetaboliteAssignmentLine {

        public MetaboliteAssignmentLine() {
            super();
        }

        // The following entries are columns defined in the MAF plugin, some values are available only in NMR or MS
        // V1 and V2 points to different versions of the plugin xml definition files

        //Column name changed from V1 to V2. Identifier, like CHEBI:15377
        private String identifier;  //V1 field
        private String databaseIdentifier; //V2 field

        //Common for all MS/NMR
        private String unitId;  //mzTab internal identificator
        private String chemicalFormula;
        private String smiles;  //V2 field only
        private String inchi;   //V2 field only

        //Column name changed from V1 to V2. Description, like "Alanine"
        private String description;  //V1 field
        private String metaboliteIdentification; //V2 field

        private String chemicalShift;
        private String multiplicity;
        private String massToCharge;
        private String fragmentation;
        private String modifications; //V2 field only
        private String charge;
        private String retentionTime;
        private String taxid;
        private String species;
        private String database;
        private String databaseVersion;
        private String reliability;
        private String uri;
        private String searchEngine;
        private String searchEngineScore;
        private String smallmoleculeAbundanceSub;
        private String smallmoleculeAbundanceStdevSub;
        private String smallmoleculeAbundanceStdErrorSub;

        //This is the concentration in the sample where the metabolite was identified
        private Collection<SampleMeasurement> sampleMeasurements;

        private String assayName;  //This is the name of the Assay record this MAF is assigned to

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getDatabaseIdentifier() {
            return databaseIdentifier;
        }

        public void setDatabaseIdentifier(String databaseIdentifier) {
            this.databaseIdentifier = databaseIdentifier;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getChemicalFormula() {
            return chemicalFormula;
        }

        public void setChemicalFormula(String chemicalFormula) {
            this.chemicalFormula = chemicalFormula;
        }

        public String getSmiles() {
            return smiles;
        }

        public void setSmiles(String smiles) {
            this.smiles = smiles;
        }

        public String getInchi() {
            return inchi;
        }

        public void setInchi(String inchi) {
            this.inchi = inchi;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMetaboliteIdentification() {
            return metaboliteIdentification;
        }

        public void setMetaboliteIdentification(String metaboliteIdentification) {
            this.metaboliteIdentification = metaboliteIdentification;
        }

        public String getChemicalShift() {
            return chemicalShift;
        }

        public void setChemicalShift(String chemicalShift) {
            this.chemicalShift = chemicalShift;
        }

        public String getMultiplicity() {
            return multiplicity;
        }

        public void setMultiplicity(String multiplicity) {
            this.multiplicity = multiplicity;
        }

        public String getMassToCharge() {
            return massToCharge;
        }

        public void setMassToCharge(String massToCharge) {
            this.massToCharge = massToCharge;
        }

        public String getFragmentation() {
            return fragmentation;
        }

        public void setFragmentation(String fragmentation) {
            this.fragmentation = fragmentation;
        }

        public String getModifications() {
            return modifications;
        }

        public void setModifications(String modifications) {
            this.modifications = modifications;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getRetentionTime() {
            return retentionTime;
        }

        public void setRetentionTime(String retentionTime) {
            this.retentionTime = retentionTime;
        }

        public String getTaxid() {
            return taxid;
        }

        public void setTaxid(String taxid) {
            this.taxid = taxid;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getDatabaseVersion() {
            return databaseVersion;
        }

        public void setDatabaseVersion(String databaseVersion) {
            this.databaseVersion = databaseVersion;
        }

        public String getReliability() {
            return reliability;
        }

        public void setReliability(String reliability) {
            this.reliability = reliability;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getSearchEngine() {
            return searchEngine;
        }

        public void setSearchEngine(String searchEngine) {
            this.searchEngine = searchEngine;
        }

        public String getSearchEngineScore() {
            return searchEngineScore;
        }

        public void setSearchEngineScore(String searchEngineScore) {
            this.searchEngineScore = searchEngineScore;
        }

        public String getSmallmoleculeAbundanceSub() {
            return smallmoleculeAbundanceSub;
        }

        public void setSmallmoleculeAbundanceSub(String smallmoleculeAbundanceSub) {
            this.smallmoleculeAbundanceSub = smallmoleculeAbundanceSub;
        }

        public String getSmallmoleculeAbundanceStdevSub() {
            return smallmoleculeAbundanceStdevSub;
        }

        public void setSmallmoleculeAbundanceStdevSub(String smallmoleculeAbundanceStdevSub) {
            this.smallmoleculeAbundanceStdevSub = smallmoleculeAbundanceStdevSub;
        }

        public String getSmallmoleculeAbundanceStdErrorSub() {
            return smallmoleculeAbundanceStdErrorSub;
        }

        public void setSmallmoleculeAbundanceStdErrorSub(String smallmoleculeAbundanceStdErrorSub) {
            this.smallmoleculeAbundanceStdErrorSub = smallmoleculeAbundanceStdErrorSub;
        }

        public Collection<SampleMeasurement> getSampleMeasurements() {
            return sampleMeasurements;
        }

        public void setSampleMeasurements(Collection<SampleMeasurement> sampleMeasurements) {
            this.sampleMeasurements = sampleMeasurements;
        }

        public String getAssayName() {
            return assayName;
        }

        public void setAssayName(String assayName) {
            this.assayName = assayName;
        }


        public class SampleMeasurement {

            public SampleMeasurement(){
                super();
            }

            private String sampleName; //The sample name from the Assay
            private String value;      //The concentration where the metabolite was identified in the sample

            public String getSampleName() {
                return sampleName;
            }

            public void setSampleName(String sampleName) {
                this.sampleName = sampleName;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
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
