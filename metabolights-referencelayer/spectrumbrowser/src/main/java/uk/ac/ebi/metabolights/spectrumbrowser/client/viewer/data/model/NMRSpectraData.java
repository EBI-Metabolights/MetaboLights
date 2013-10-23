/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model;

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
    void setData(Double[] peaks);


    String getxLabel();
    void setxLabel(String xLabel);

    float getxMax();
    void setxMax(float xMax);

    float getxMin();
    void setxMin(float xMin);

    String getyLabel();
    void setyLabel(String yLabel);

    float getyMax();
    void setyMax(float yMax);

    float getyMin();
    void setyMin(float yMin);


}