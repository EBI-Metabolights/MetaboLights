/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsStatsService.java
 *
 * Last modified: 1/21/13 11:31 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.model.MLStats;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/01/2013
 * Time: 11:31
 */
public interface MetaboLightsStatsService {

    public List<MLStats> getByPageCategory(String categoryName);
    public List<MLStats> getAll();

}
