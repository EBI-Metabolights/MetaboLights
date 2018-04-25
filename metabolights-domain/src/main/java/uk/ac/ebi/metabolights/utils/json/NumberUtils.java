package uk.ac.ebi.metabolights.utils.json;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by kalai on 02/09/15.
 */
public class NumberUtils {

    static final DecimalFormat formatter = new DecimalFormat("0.00");

    public static String getHumanReadableByteSize(BigDecimal sizeInBytes) {

        String humanReadableSize = "";
        double blockSize = 1024.0;

        double kb = sizeInBytes.doubleValue() / blockSize;
        double mb = kb / blockSize;
        double gb = mb / blockSize;
        double tb = gb / blockSize;

        if (tb > 1) {
            humanReadableSize = formatter.format(tb).concat("TB");
        } else if (gb > 1) {
            humanReadableSize = formatter.format(gb).concat("GB");
        } else if (mb > 1) {
            humanReadableSize = formatter.format(mb).concat("MB");
        } else if (kb > 1) {
            humanReadableSize = formatter.format(kb).concat("KB");
        } else {
            humanReadableSize = formatter.format(sizeInBytes).concat("Bytes");
        }
        return humanReadableSize;
    }

}
