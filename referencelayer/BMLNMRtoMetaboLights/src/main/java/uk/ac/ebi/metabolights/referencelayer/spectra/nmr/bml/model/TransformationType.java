
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transformationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="transformationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="fourier transformation"/>
 *     &lt;enumeration value="non-fourier transformation"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "transformationType")
@XmlEnum
public enum TransformationType {

    @XmlEnumValue("fourier transformation")
    FOURIER_TRANSFORMATION("fourier transformation"),
    @XmlEnumValue("non-fourier transformation")
    NON_FOURIER_TRANSFORMATION("non-fourier transformation");
    private final String value;

    TransformationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransformationType fromValue(String v) {
        for (TransformationType c: TransformationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
