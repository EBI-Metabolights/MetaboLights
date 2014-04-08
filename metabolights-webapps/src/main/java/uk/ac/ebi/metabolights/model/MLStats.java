/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/8/14 9:27 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MLStats.java
 *
 * Last modified: 1/21/13 10:09 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/01/2013
 * Time: 10:09
 */
@Entity
@Table(name = "ML_STATS")
public class MLStats {

    @Id
    @Column(name = "ID")
    @NotEmpty
    private String id;

    @Column(name = "PAGE_SECTION")
    @NotEmpty
    private String pageSection;

    @Column(name = "STR_NAME")
    @NotEmpty
    private String displayName;

    @Column(name = "STR_VALUE")
    @NotEmpty
    private String displayValue;

    @Column(name = "SORT_ORDER")
    private int sortOrder;

    public static enum PageCategory {

        DATA("Data"), IDENTIFIED("Identified"), SUBMITTERS("Submitters"), SPECIES("Species"), COMPOUNDS("Compounds");

        private String val;
        PageCategory(String val) {
            this.val = val;
        }
        public String getValue() {
            return val;
        }
    }

    ;

    public MLStats() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageSection() {
        return pageSection;
    }

    public void setPageSection(String pageSection) {
        this.pageSection = pageSection;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
