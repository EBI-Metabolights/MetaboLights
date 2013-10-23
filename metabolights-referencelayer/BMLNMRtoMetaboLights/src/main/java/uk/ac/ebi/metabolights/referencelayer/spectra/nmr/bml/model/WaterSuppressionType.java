
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
 * <p>Java class for waterSuppressionType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="waterSuppressionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Presat"/>
 *     &lt;enumeration value="NOESY-Presat"/>
 *     &lt;enumeration value="Watergate"/>
 *     &lt;enumeration value="WET"/>
 *     &lt;enumeration value="excitation sculpting"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "waterSuppressionType")
@XmlEnum
public enum WaterSuppressionType {

    @XmlEnumValue("Presat")
    PRESAT("Presat"),
    @XmlEnumValue("NOESY-Presat")
    NOESY_PRESAT("NOESY-Presat"),
    @XmlEnumValue("Watergate")
    WATERGATE("Watergate"),
    WET("WET"),
    @XmlEnumValue("excitation sculpting")
    EXCITATION_SCULPTING("excitation sculpting");
    private final String value;

    WaterSuppressionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WaterSuppressionType fromValue(String v) {
        for (WaterSuppressionType c: WaterSuppressionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
