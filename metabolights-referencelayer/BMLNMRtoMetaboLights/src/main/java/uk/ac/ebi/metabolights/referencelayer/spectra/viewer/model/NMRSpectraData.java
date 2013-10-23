/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 16:08
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model;

/**
 * @name    NMRSpectraData
 * @date    2013.02.20
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public interface NMRSpectraData {

    Double[] getData();

    String getxLabel();

    float getxMax();

    float getxMin();

    String getyLabel();

    Double getyMax();

    Double getyMin();

}
