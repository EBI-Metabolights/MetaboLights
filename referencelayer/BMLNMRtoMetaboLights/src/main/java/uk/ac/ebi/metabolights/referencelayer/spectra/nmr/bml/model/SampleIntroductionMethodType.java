
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sampleIntroductionMethodType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sampleIntroductionMethodType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="tube"/>
 *     &lt;enumeration value="MAS"/>
 *     &lt;enumeration value="flow probe"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sampleIntroductionMethodType")
@XmlEnum
public enum SampleIntroductionMethodType {

    @XmlEnumValue("tube")
    TUBE("tube"),
    MAS("MAS"),
    @XmlEnumValue("flow probe")
    FLOW_PROBE("flow probe");
    private final String value;

    SampleIntroductionMethodType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SampleIntroductionMethodType fromValue(String v) {
        for (SampleIntroductionMethodType c: SampleIntroductionMethodType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
