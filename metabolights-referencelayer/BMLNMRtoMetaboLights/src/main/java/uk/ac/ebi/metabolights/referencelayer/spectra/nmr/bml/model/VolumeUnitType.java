
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
 * <p>Java class for volumeUnitType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="volumeUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="millilitres"/>
 *     &lt;enumeration value="microlitres"/>
 *     &lt;enumeration value="millimetres"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "volumeUnitType")
@XmlEnum
public enum VolumeUnitType {

    @XmlEnumValue("millilitres")
    MILLILITRES("millilitres"),
    @XmlEnumValue("microlitres")
    MICROLITRES("microlitres"),
    @XmlEnumValue("millimetres")
    MILLIMETRES("millimetres");
    private final String value;

    VolumeUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VolumeUnitType fromValue(String v) {
        for (VolumeUnitType c: VolumeUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
