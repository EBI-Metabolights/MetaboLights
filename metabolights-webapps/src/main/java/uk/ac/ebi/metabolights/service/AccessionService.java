/*
 * EBI MetaboLights Project - 2011.
 *
 * File: AccessionService.java
 *
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.model.MetaboLightsSubmittedId;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 20/09/2011
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public interface AccessionService {

    public String getAccessionNumber();
    public String getDefaultPrefix ();
    public void setDefaultPrefix(String defaultPrefix);
    public MetaboLightsSubmittedId getSubmittedId(String studyAcc);
    public void saveSubmittedId(String orgId, String studyAcc);

}
