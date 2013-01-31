/**
 * 
 */
package uk.ac.ebi.metabolights.dao;


import uk.ac.ebi.bioinvindex.model.Study;

import java.util.List;

/**
 * @author kenneth
 *
 */
public interface StudyDAO {

	public Study getStudy(String studyAcc, boolean clearSession);
    public Study getStudy(String studyAcc, boolean clearSession, boolean fromQueue);
    public List<String> findAllAcc();
    public List<Study> findStudiesToGoPublic();
	public void update(Study study);
	
}
