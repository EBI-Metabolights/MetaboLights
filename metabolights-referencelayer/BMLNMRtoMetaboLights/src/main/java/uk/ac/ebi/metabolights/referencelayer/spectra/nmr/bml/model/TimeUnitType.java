
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
 * <p>Java class for timeUnitType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="timeUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="seconds"/>
 *     &lt;enumeration value="milliseconds"/>
 *     &lt;enumeration value="microseconds"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "timeUnitType")
@XmlEnum
public enum TimeUnitType {

    @XmlEnumValue("seconds")
    SECONDS("seconds"),
    @XmlEnumValue("milliseconds")
    MILLISECONDS("milliseconds"),
    @XmlEnumValue("microseconds")
    MICROSECONDS("microseconds");
    private final String value;

    TimeUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TimeUnitType fromValue(String v) {
        for (TimeUnitType c: TimeUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
