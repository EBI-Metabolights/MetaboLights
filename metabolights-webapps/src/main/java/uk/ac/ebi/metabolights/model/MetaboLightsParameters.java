/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsParameters.java
 *
 * Last modified: 1/17/13 11:29 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

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
public class MetaboLightsParameters{
    @Id
    @Column(name="NAME")
    @NotEmpty
    private String parameterName;

    @Column(name="VALUE")
    @NotEmpty
    private String parameterValue;

    public MetaboLightsParameters(){};

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
