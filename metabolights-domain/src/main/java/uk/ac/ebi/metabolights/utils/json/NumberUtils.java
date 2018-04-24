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

        double kb = sizeInBytes.doubleValue() / 1024.0;
        double mb = kb / 1024.0;
        double gb = mb / 1024.0;
        double tb = gb / 1024.0;
                                                    
        if (tb > 1) {
            humanReadableSize = formatter.format(tb).concat("TB");
        } else if (gb > 1) {
            humanReadableSize = formatter.format(gb).concat("GB");
        } else if (mb > 1) {
            humanReadableSize = formatter.format(mb).concat("MB");
        } else if (kb > 1) {
            humanReadableSize = formatter.format(mb).concat("KB");
        } else {
            humanReadableSize = formatter.format(sizeInBytes).concat("Bytes");
        }
        return humanReadableSize;
    }

}
