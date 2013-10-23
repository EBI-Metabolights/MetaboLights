
/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for spectralProjectionAxisType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="spectralProjectionAxisType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="f1"/>
 *     &lt;enumeration value="f2"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "spectralProjectionAxisType")
@XmlEnum
public enum SpectralProjectionAxisType {

    @XmlEnumValue("f1")
    F_1("f1"),
    @XmlEnumValue("f2")
    F_2("f2");
    private final String value;

    SpectralProjectionAxisType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpectralProjectionAxisType fromValue(String v) {
        for (SpectralProjectionAxisType c: SpectralProjectionAxisType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
