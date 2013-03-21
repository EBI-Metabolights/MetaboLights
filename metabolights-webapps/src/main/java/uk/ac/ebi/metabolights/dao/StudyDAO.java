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

	public Study getStudy(String studyAcc, boolean clearSession) throws IllegalAccessException;
    public Study getStudy(String studyAcc, boolean clearSession, boolean fromQueue) throws IllegalAccessException;
    public List<String> findAllAcc();
	public void update(Study study);
	
}
