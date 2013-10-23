
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
 * <p>Java class for encodingType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="encodingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TPPI"/>
 *     &lt;enumeration value="States"/>
 *     &lt;enumeration value="States-TPPI"/>
 *     &lt;enumeration value="Quadrature filter"/>
 *     &lt;enumeration value="Hadamard"/>
 *     &lt;enumeration value="Radon"/>
 *     &lt;enumeration value="GFT"/>
 *     &lt;enumeration value="Frydman"/>
 *     &lt;enumeration value="Echo/Anti-Echo"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "encodingType")
@XmlEnum
public enum EncodingType {

    TPPI("TPPI"),
    @XmlEnumValue("States")
    STATES("States"),
    @XmlEnumValue("States-TPPI")
    STATES_TPPI("States-TPPI"),
    @XmlEnumValue("Quadrature filter")
    QUADRATURE_FILTER("Quadrature filter"),
    @XmlEnumValue("Hadamard")
    HADAMARD("Hadamard"),
    @XmlEnumValue("Radon")
    RADON("Radon"),
    GFT("GFT"),
    @XmlEnumValue("Frydman")
    FRYDMAN("Frydman"),
    @XmlEnumValue("Echo/Anti-Echo")
    ECHO_ANTI_ECHO("Echo/Anti-Echo");
    private final String value;

    EncodingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EncodingType fromValue(String v) {
        for (EncodingType c: EncodingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
