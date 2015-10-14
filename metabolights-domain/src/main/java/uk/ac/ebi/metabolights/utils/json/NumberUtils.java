package uk.ac.ebi.metabolights.utils.json;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by kalai on 02/09/15.
 */
public class NumberUtils {

    static final DecimalFormat formatter = new DecimalFormat("0.00");



    public static String getHumanReadableByteSize(BigDecimal sizeInKiloBytes) {

        String humanReadableSize = "";

        double mb = sizeInKiloBytes.doubleValue() / 1024.0;
        double gb = sizeInKiloBytes.doubleValue() / 1048576.0;
        double tb = sizeInKiloBytes.doubleValue() / 1073741824.0;

        if (tb > 1) {
            humanReadableSize = formatter.format(tb).concat("TB");
        } else if (gb > 1) {
            humanReadableSize = formatter.format(gb).concat("GB");
        } else if (mb > 1) {
            humanReadableSize = formatter.format(mb).concat("MB");
        } else {
            humanReadableSize = formatter.format(sizeInKiloBytes).concat("KB");
        }
        return humanReadableSize;
    }

}
