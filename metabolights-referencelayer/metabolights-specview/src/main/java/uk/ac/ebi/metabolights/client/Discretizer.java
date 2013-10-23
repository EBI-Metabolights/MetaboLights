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
 * Discretizer.java
 *
 * 2013.02.21
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

package uk.ac.ebi.metabolights.client;


import org.apache.log4j.Logger;

/**
 * @name    Discretizer
 * @date    2013.02.21
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public class Discretizer {

    Double step;
    Double min;
    /**
     * Starts the discretizer with the given min, max and number of points.
     *
     * @param min of the range
     * @param max of the range
     * @param points number of points to discretize to.
     */
    public Discretizer(double min, double max, int points) {
        step = Math.abs(max - min)/points;
        this.min = min;
    }

    /**
     * Returns the value of the discrete mark at that point number of the interval. The first point number is zero. No
     * provisions are currently made for pointNumber > initial point count.
     *
     * @param pointNumber
     * @return
     */
    public Double valueAtPoint(int pointNumber) {
        return min + step*pointNumber;
    }


}
