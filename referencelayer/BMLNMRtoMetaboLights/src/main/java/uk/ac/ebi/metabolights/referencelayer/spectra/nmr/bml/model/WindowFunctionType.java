
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for windowFunctionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="windowFunctionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="exponential multiplication"/>
 *     &lt;enumeration value="gaussian broadening"/>
 *     &lt;enumeration value="sine"/>
 *     &lt;enumeration value="sine2"/>
 *     &lt;enumeration value="sinc"/>
 *     &lt;enumeration value="sem"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "windowFunctionType")
@XmlEnum
public enum WindowFunctionType {

    @XmlEnumValue("exponential multiplication")
    EXPONENTIAL_MULTIPLICATION("exponential multiplication"),
    @XmlEnumValue("gaussian broadening")
    GAUSSIAN_BROADENING("gaussian broadening"),
    @XmlEnumValue("sine")
    SINE("sine"),
    @XmlEnumValue("sine2")
    SINE_2("sine2"),
    @XmlEnumValue("sinc")
    SINC("sinc"),
    @XmlEnumValue("sem")
    SEM("sem");
    private final String value;

    WindowFunctionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WindowFunctionType fromValue(String v) {
        for (WindowFunctionType c: WindowFunctionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
