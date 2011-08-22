/**
 * 
 */
package uk.ac.ebi.metabolights.dao;


import uk.ac.ebi.bioinvindex.model.Study;

/**
 * @author kenneth
 *
 */
public interface StudyDAO {

	public Study getStudy(String studyAcc);
	public void update(Study study);
	
}
