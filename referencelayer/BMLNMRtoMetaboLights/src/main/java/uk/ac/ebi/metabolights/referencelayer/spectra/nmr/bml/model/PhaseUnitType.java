
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for phaseUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="phaseUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="degrees"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "phaseUnitType")
@XmlEnum
public enum PhaseUnitType {

    @XmlEnumValue("degrees")
    DEGREES("degrees");
    private final String value;

    PhaseUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PhaseUnitType fromValue(String v) {
        for (PhaseUnitType c: PhaseUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
