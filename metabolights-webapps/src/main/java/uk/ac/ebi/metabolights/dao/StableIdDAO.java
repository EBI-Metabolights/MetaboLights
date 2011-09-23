/*
 * EBI MetaboLights Project - 2011.
 *
 * File: StableIdDAO.java
 *
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.dao;
import uk.ac.ebi.metabolights.model.StableId;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 20/09/2011
 * Time: 14:12
 */
public interface StableIdDAO {

    public StableId getNextStableId();
    public void update(StableId stableId);

}
