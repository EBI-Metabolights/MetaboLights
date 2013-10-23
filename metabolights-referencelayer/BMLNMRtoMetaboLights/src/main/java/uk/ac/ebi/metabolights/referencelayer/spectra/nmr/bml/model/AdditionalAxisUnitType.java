
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
 * <p>Java class for additionalAxisUnitType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="additionalAxisUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="seconds"/>
 *     &lt;enumeration value="milliseconds"/>
 *     &lt;enumeration value="microseconds"/>
 *     &lt;enumeration value="hertz"/>
 *     &lt;enumeration value="kilohertz"/>
 *     &lt;enumeration value="megahertz"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "additionalAxisUnitType")
@XmlEnum
public enum AdditionalAxisUnitType {

    @XmlEnumValue("seconds")
    SECONDS("seconds"),
    @XmlEnumValue("milliseconds")
    MILLISECONDS("milliseconds"),
    @XmlEnumValue("microseconds")
    MICROSECONDS("microseconds"),
    @XmlEnumValue("hertz")
    HERTZ("hertz"),
    @XmlEnumValue("kilohertz")
    KILOHERTZ("kilohertz"),
    @XmlEnumValue("megahertz")
    MEGAHERTZ("megahertz");
    private final String value;

    AdditionalAxisUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdditionalAxisUnitType fromValue(String v) {
        for (AdditionalAxisUnitType c: AdditionalAxisUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
