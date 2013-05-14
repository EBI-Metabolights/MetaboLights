
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for referenceShiftUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="referenceShiftUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ppm"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "referenceShiftUnitType")
@XmlEnum
public enum ReferenceShiftUnitType {

    @XmlEnumValue("ppm")
    PPM("ppm");
    private final String value;

    ReferenceShiftUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReferenceShiftUnitType fromValue(String v) {
        for (ReferenceShiftUnitType c: ReferenceShiftUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
