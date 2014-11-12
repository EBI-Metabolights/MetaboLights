/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/17/13 2:31 PM
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

public class Assay {

    private String measurement;
    private String technology;
    private String platform;
    private String fileName;
    private int assayNumber;
    private MetaboliteAssignment metaboliteAssignment; //this also contains a file name MAF

	private Table assayTable;

	public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getAssayNumber() {
        return assayNumber;
    }

    public void setAssayNumber(int assayNumber) {
        this.assayNumber = assayNumber;
    }

	public Table getAssayTable() {
		return assayTable;
	}

	public void setAssayTable(Table assayTable) {
		this.assayTable = assayTable;
	}


	public MetaboliteAssignment getMetaboliteAssignment() {
        if (metaboliteAssignment == null)
            metaboliteAssignment = new MetaboliteAssignment();

        return metaboliteAssignment;
    }

    public void setMetaboliteAssignment(MetaboliteAssignment metaboliteAssignment) {
        this.metaboliteAssignment = metaboliteAssignment;
    }
}
