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
 * NMRRouter.java
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

package uk.ac.ebi.nmr.rest;


import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import uk.ac.ebi.nmr.rest.spectra.NMRSpectraDataResource;

/**
 * @name    NMRRouter
 * @date    2013.02.20
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public class NMRRouter extends Application {

    private static final Logger LOGGER = Logger.getLogger( NMRRouter.class );

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attach("/nmrSpectra/{studyID}/{metaboliteID}/nmrspec", NMRSpectraDataResource.class);

        return router;
    }

}
