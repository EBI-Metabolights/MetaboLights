
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fieldStrengthUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="fieldStrengthUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="gauss"/>
 *     &lt;enumeration value="tesla"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "fieldStrengthUnitType")
@XmlEnum
public enum FieldStrengthUnitType {

    @XmlEnumValue("gauss")
    GAUSS("gauss"),
    @XmlEnumValue("tesla")
    TESLA("tesla");
    private final String value;

    FieldStrengthUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FieldStrengthUnitType fromValue(String v) {
        for (FieldStrengthUnitType c: FieldStrengthUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
