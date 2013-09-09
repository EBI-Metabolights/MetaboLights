/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.nmr.rest.spectra;

import org.apache.log4j.Logger;

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

    Double getxMax();

    Double getxMin();

    String getyLabel();

    Double getyMax();

    Double getyMin();
    
}
