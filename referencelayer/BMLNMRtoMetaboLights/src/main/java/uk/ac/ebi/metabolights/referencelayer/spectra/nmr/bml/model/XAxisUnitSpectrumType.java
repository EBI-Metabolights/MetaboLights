
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xAxisUnitSpectrumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="xAxisUnitSpectrumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="hertz"/>
 *     &lt;enumeration value="kilohertz"/>
 *     &lt;enumeration value="megahertz"/>
 *     &lt;enumeration value="ppm"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "xAxisUnitSpectrumType")
@XmlEnum
public enum XAxisUnitSpectrumType {

    @XmlEnumValue("hertz")
    HERTZ("hertz"),
    @XmlEnumValue("kilohertz")
    KILOHERTZ("kilohertz"),
    @XmlEnumValue("megahertz")
    MEGAHERTZ("megahertz"),
    @XmlEnumValue("ppm")
    PPM("ppm");
    private final String value;

    XAxisUnitSpectrumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XAxisUnitSpectrumType fromValue(String v) {
        for (XAxisUnitSpectrumType c: XAxisUnitSpectrumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
