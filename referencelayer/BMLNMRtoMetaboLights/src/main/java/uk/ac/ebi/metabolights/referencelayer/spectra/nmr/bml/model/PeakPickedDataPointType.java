
package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for peakPickedDataPointType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="peakPickedDataPointType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="singlet"/>
 *     &lt;enumeration value="doublet"/>
 *     &lt;enumeration value="triplet"/>
 *     &lt;enumeration value="quadruplet"/>
 *     &lt;enumeration value="multiplet"/>
 *     &lt;enumeration value="unassigned"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "peakPickedDataPointType")
@XmlEnum
public enum PeakPickedDataPointType {

    @XmlEnumValue("singlet")
    SINGLET("singlet"),
    @XmlEnumValue("doublet")
    DOUBLET("doublet"),
    @XmlEnumValue("triplet")
    TRIPLET("triplet"),
    @XmlEnumValue("quadruplet")
    QUADRUPLET("quadruplet"),
    @XmlEnumValue("multiplet")
    MULTIPLET("multiplet"),
    @XmlEnumValue("unassigned")
    UNASSIGNED("unassigned");
    private final String value;

    PeakPickedDataPointType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PeakPickedDataPointType fromValue(String v) {
        for (PeakPickedDataPointType c: PeakPickedDataPointType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
