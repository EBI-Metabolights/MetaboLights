/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/1/14 9:28 AM
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonTypeName("Study")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Study extends LiteStudy {

    private String description;
    private String studyLocation;


    // Collections
    private Collection<Contact> contacts;
    private Collection<StudyDesignDescriptors> descriptors;
    private Collection<Publication> publications;
    private Collection<Protocol> protocols;
    private List<Assay> assays;
    private Collection<Backup> backups;


    private List<Validation> validationList = new ArrayList<>();
    private Validations validations = new Validations();

    // Tables Sample & Assays
    private Table sampleTable;

    public List<Assay> getAssays() {
        return assays;
    }

    public void setAssays(List<Assay> assays) {
        this.assays = assays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudyLocation() {
        return studyLocation;
    }

    public void setStudyLocation(String studyLocation) {
        this.studyLocation = studyLocation;
    }

    // Collections

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> colContacts) {
        this.contacts = colContacts;
    }

    public Collection<StudyDesignDescriptors> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(Collection<StudyDesignDescriptors> colDescriptors) {
        this.descriptors = colDescriptors;
    }

    public Collection<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Collection<Publication> publications) {
        this.publications = publications;
    }

    public Collection<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(Collection<Protocol> protocols) {
        this.protocols = protocols;
    }

    public Table getSampleTable() {
        return sampleTable;
    }

    public void setSampleTable(Table sampleTable) {
        this.sampleTable = sampleTable;
    }

    public Collection<Backup> getBackups() {
        return backups;
    }

    public void setBackups(Collection<Backup> backups) {
        this.backups = backups;
    }

    public Validations getValidations() {
        return validations;
    }

    public void setValidations(Validations validations) {
        this.validations = validations;
    }

    public List<Validation> getValidationList() {
        return validationList;
    }

    public void setValidationList(List<Validation> validationList) {
        this.validationList = validationList;
    }
}
