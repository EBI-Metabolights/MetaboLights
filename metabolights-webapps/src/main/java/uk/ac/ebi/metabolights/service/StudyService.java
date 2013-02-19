package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.bioinvindex.model.Study;

import java.util.List;

/**
 * Services retrieval of study details
 *
 */
public interface StudyService {

	public Study getBiiStudy(String studyAcc, boolean clearSession) throws IllegalAccessException;
    public Study getBiiStudy(String studyAcc, boolean clearSession, boolean fromQueue) throws IllegalAccessException;
	public void update(Study study);
    public List<Study> findStudiesToGoPublic();
    public List<String> findAllStudies();

}


