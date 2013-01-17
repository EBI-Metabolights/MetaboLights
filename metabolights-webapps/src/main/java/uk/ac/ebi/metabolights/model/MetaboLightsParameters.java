/*
 * EBI MetaboLights Project - 2013.
 *
 * File: Parameters.java
 *
 * Last modified: 1/17/13 10:37 AM
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
 * Date: 17/01/2013
 * Time: 10:37
 */

@Entity
@Table(name = "METABOLIGHTS_PARAMETERS")
public class MetaboLightsParameters {
    @Id
    @Column(name="NAME")
    @NotEmpty
    private String parameterName;

    @Column(name="VALUE")
    @NotEmpty
    private String parameterValue;

}
