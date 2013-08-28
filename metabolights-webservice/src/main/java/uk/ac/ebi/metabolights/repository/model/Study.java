package uk.ac.ebi.metabolights.repository.model;

import java.util.Collection;
import java.util.Date;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 11:27
 */
public class Study {
    private String studyIdentifier;
    private Date studyPublicReleaseDate;
    private Date studySubmissionDate;
    private String title;
    private String description;

    // Collections
    private Collection<Contact> contacts;
    private Collection<StudyDesignDescriptors> descriptors;


    public String getStudyIdentifier() {
        return studyIdentifier;
    }

    public void setStudyIdentifier(String studyIdentifier) {
        this.studyIdentifier = studyIdentifier;
    }

    public Date getStudyPublicReleaseDate() {
        return studyPublicReleaseDate;
    }

    public void setStudyPublicReleaseDate(Date studyPublicReleaseDate) {
        this.studyPublicReleaseDate = studyPublicReleaseDate;
    }

    public Date getStudySubmissionDate() {
        return studySubmissionDate;
    }

    public void setStudySubmissionDate(Date studySubmissionDate) {
        this.studySubmissionDate = studySubmissionDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
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

}
