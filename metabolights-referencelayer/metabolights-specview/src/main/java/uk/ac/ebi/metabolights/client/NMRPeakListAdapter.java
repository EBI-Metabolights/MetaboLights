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
 * NMRPeakListAdapter.java
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


import java.util.ArrayList;
import java.util.List;
import uk.ac.ebi.biowidgets.spectrum.data.PeakList;
import uk.ac.ebi.biowidgets.spectrum.data.Peak;
//import uk.ac.ebi.pride.widgets.client.spectrum.data.Peak;
//import uk.ac.ebi.pride.widgets.client.spectrum.data.PeakList;

/**
 * @name    NMRPeakListAdapter
 * @date    2013.02.21
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public class NMRPeakListAdapter implements PeakList {


    List<NMRPeakAdapter> peaks;
    Double xAxisStart;
    Double xAxisStop;

    public NMRPeakListAdapter(Double xMin, Double xMax) {
        this.xAxisStart = xMin;
        this.xAxisStop = xMax;
    }

    public long getSpectrumId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Peak> getPeaks() {
        return new ArrayList<Peak>(peaks);
    }

    public Double getMzStart() {
        return xAxisStart;
    }

    public Double getMzStop() {
        return xAxisStop;
    }

    void set(List<NMRPeakAdapter> peaks) {
        this.peaks = peaks;
    }


}
