/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 09/09/13 12:20
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

/**
 * SimpleNMRSpectraData.java
 *
 * 2013.02.20
 *
 * This file is part of the CheMet library
 *
 * The CheMet library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CheMet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CheMet.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.ebi.nmr.rest.spectra;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;

/**
 * @name    SimpleNMRSpectraData
 * @date    2013.02.20
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public class SimpleNMRSpectraData implements NMRSpectraData {

    private static final Logger LOGGER = Logger.getLogger( SimpleNMRSpectraData.class );

    String xLabel;
    String yLabel;

    Double xMax;
    Double yMax;

    Double xMin;
    Double yMin;

    Double[] data;


    public SimpleNMRSpectraData(Double[] data, Double xMax, Double xMin) {
        this.data = new Double[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
        yLabel = "Intensity";
        xLabel = "ppm";

        List<Double> intensities = Arrays.asList(this.data);
        yMax = Collections.max(intensities);
        yMin = Collections.min(intensities);

        this.xMax = xMax;
        this.xMin = xMin;
    }

    @Override
    public Double[] getData() {
        return this.data;
    }

    @Override
    public String getxLabel() {
        return xLabel;
    }

    @Override
    public String getyLabel() {
        return yLabel;
    }

    @Override
    public Double getxMax() {
        return xMax;
    }

    @Override
    public Double getyMax() {
        return yMax;
    }

    @Override
    public Double getxMin() {
        return xMin;
    }

    @Override
    public Double getyMin() {
        return yMin;
    }


}
