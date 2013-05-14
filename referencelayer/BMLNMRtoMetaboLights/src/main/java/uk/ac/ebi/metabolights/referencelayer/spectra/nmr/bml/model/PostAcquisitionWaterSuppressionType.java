
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for postAcquisitionWaterSuppressionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="postAcquisitionWaterSuppressionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="convolution"/>
 *     &lt;enumeration value="polynomial fitting"/>
 *     &lt;enumeration value="WaveWat"/>
 *     &lt;enumeration value="HSVD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "postAcquisitionWaterSuppressionType")
@XmlEnum
public enum PostAcquisitionWaterSuppressionType {

    @XmlEnumValue("convolution")
    CONVOLUTION("convolution"),
    @XmlEnumValue("polynomial fitting")
    POLYNOMIAL_FITTING("polynomial fitting"),
    @XmlEnumValue("WaveWat")
    WAVE_WAT("WaveWat"),
    HSVD("HSVD");
    private final String value;

    PostAcquisitionWaterSuppressionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PostAcquisitionWaterSuppressionType fromValue(String v) {
        for (PostAcquisitionWaterSuppressionType c: PostAcquisitionWaterSuppressionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
