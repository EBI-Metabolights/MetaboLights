
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for frequencyUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="frequencyUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="hertz"/>
 *     &lt;enumeration value="kilohertz"/>
 *     &lt;enumeration value="megahertz"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "frequencyUnitType")
@XmlEnum
public enum FrequencyUnitType {

    @XmlEnumValue("hertz")
    HERTZ("hertz"),
    @XmlEnumValue("kilohertz")
    KILOHERTZ("kilohertz"),
    @XmlEnumValue("megahertz")
    MEGAHERTZ("megahertz");
    private final String value;

    FrequencyUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FrequencyUnitType fromValue(String v) {
        for (FrequencyUnitType c: FrequencyUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
