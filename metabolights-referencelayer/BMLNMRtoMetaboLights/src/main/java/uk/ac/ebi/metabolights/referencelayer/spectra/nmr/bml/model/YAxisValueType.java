
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
 * <p>Java class for yAxisValueType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="yAxisValueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="power"/>
 *     &lt;enumeration value="magnitude"/>
 *     &lt;enumeration value="real"/>
 *     &lt;enumeration value="imaginary"/>
 *     &lt;enumeration value="complex"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "yAxisValueType")
@XmlEnum
public enum YAxisValueType {

    @XmlEnumValue("power")
    POWER("power"),
    @XmlEnumValue("magnitude")
    MAGNITUDE("magnitude"),
    @XmlEnumValue("real")
    REAL("real"),
    @XmlEnumValue("imaginary")
    IMAGINARY("imaginary"),
    @XmlEnumValue("complex")
    COMPLEX("complex");
    private final String value;

    YAxisValueType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static YAxisValueType fromValue(String v) {
        for (YAxisValueType c: YAxisValueType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
