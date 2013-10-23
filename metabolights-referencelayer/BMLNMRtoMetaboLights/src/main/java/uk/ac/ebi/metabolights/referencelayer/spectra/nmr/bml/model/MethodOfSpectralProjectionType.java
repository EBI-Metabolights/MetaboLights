
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
 * <p>Java class for methodOfSpectralProjectionType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="methodOfSpectralProjectionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="maximum intensity"/>
 *     &lt;enumeration value="summation"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "methodOfSpectralProjectionType")
@XmlEnum
public enum MethodOfSpectralProjectionType {

    @XmlEnumValue("maximum intensity")
    MAXIMUM_INTENSITY("maximum intensity"),
    @XmlEnumValue("summation")
    SUMMATION("summation");
    private final String value;

    MethodOfSpectralProjectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MethodOfSpectralProjectionType fromValue(String v) {
        for (MethodOfSpectralProjectionType c: MethodOfSpectralProjectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
