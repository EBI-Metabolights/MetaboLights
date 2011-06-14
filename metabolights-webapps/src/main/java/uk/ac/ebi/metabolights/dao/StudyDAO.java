/**
 * 
 */
package uk.ac.ebi.metabolights.dao;

import java.util.List;

import uk.ac.ebi.bioinvindex.model.Study;

/**
 * @author kenneth
 *
 */
public interface StudyDAO {

	public List<Study> findStudiesForUser(String userName);
	public Study getStudy(String studyAcc);
	
}
