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
 * NMRSpectraDataResource.java
 *
 * 2013.02.20
 *
 * This file is part of the CheMet library
 *
 * The CheMet library is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * CheMet is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with CheMet. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.nmr.rest.spectra;

import java.io.InputStream;
import org.apache.log4j.Logger;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import uk.ac.ebi.nmr.execs.PlotFID02;
import uk.ac.ebi.nmr.fid.Acqu;
import uk.ac.ebi.nmr.fid.FastFourierTransformTool;
import uk.ac.ebi.nmr.fid.Fid;
import uk.ac.ebi.nmr.fid.JTransformFFTTool;
import uk.ac.ebi.nmr.fid.io.AcquReader;
import uk.ac.ebi.nmr.fid.io.FidReader;

/**
 * @name NMRSpectraDataResource
 * @date 2013.02.20
 * @version $Rev$ : Last Changed $Date$
 * @author pmoreno
 * @author $Author$ (this version)
 * @brief ...class description...
 *
 */
public class NMRSpectraDataResource extends ServerResource {

    private static final Logger LOGGER = Logger.getLogger(NMRSpectraDataResource.class);

    @Get
    public Representation represent() {
        try {

            Acqu acquisition = (new AcquReader(PlotFID02.class.getResourceAsStream("/examples/file_formats/bmse000109/1H/acqu"))).read();
            InputStream fidInput = PlotFID02.class.getResourceAsStream("/examples/file_formats/bmse000109/1H/fid");
            FastFourierTransformTool ft;

            Fid fid = new FidReader(fidInput, acquisition).read();
            ft = new JTransformFFTTool(fid, acquisition);


            Long milis = System.currentTimeMillis();
            Double[] spectrum = ft.computeFFT();
            System.out.println("FFT timimg " + ft.getClass().getCanonicalName() + " " + (System.currentTimeMillis() - milis) + " ms ");

            NMRSpectraData res = new SimpleNMRSpectraData(spectrum, 100.0d, 0.0d);
            JacksonRepresentation rep = new JacksonRepresentation(res);
            return rep;
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonRepresentation("{ \"Error\": \"" + e.toString() + "\" }");
        }
    }
}
