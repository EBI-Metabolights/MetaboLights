
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for windowFunctionParameterType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="windowFunctionParameterType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="line broadening"/>
 *     &lt;enumeration value="line sharpening"/>
 *     &lt;enumeration value="sine bell length"/>
 *     &lt;enumeration value="sine bell shift"/>
 *     &lt;enumeration value="gaussian broadening"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "windowFunctionParameterType")
@XmlEnum
public enum WindowFunctionParameterType {

    @XmlEnumValue("line broadening")
    LINE_BROADENING("line broadening"),
    @XmlEnumValue("line sharpening")
    LINE_SHARPENING("line sharpening"),
    @XmlEnumValue("sine bell length")
    SINE_BELL_LENGTH("sine bell length"),
    @XmlEnumValue("sine bell shift")
    SINE_BELL_SHIFT("sine bell shift"),
    @XmlEnumValue("gaussian broadening")
    GAUSSIAN_BROADENING("gaussian broadening");
    private final String value;

    WindowFunctionParameterType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WindowFunctionParameterType fromValue(String v) {
        for (WindowFunctionParameterType c: WindowFunctionParameterType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
