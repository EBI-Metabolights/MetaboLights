
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for temperatureUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="temperatureUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="centigrade"/>
 *     &lt;enumeration value="kelvin"/>
 *     &lt;enumeration value="fahrenheit"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "temperatureUnitType")
@XmlEnum
public enum TemperatureUnitType {

    @XmlEnumValue("centigrade")
    CENTIGRADE("centigrade"),
    @XmlEnumValue("kelvin")
    KELVIN("kelvin"),
    @XmlEnumValue("fahrenheit")
    FAHRENHEIT("fahrenheit");
    private final String value;

    TemperatureUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TemperatureUnitType fromValue(String v) {
        for (TemperatureUnitType c: TemperatureUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
