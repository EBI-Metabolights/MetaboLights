package uk.ac.ebi.metabolights.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.dao.StudyDAO;

@Service
public class StudyService {
	
	private StudyDAO studyDAO;
    
	public void setStudyDAO(StudyDAO studyDAO) {
		this.studyDAO = studyDAO;
	}

	@Transactional
	public List<Study> findStudiesForUser(String userName){
		
		List<Study> studyList = studyDAO.findStudiesForUser(userName);
		Iterator<Study> studyIterator = studyList.iterator();
		
		while (studyIterator.hasNext()){
			Study study = (Study) studyIterator.next();
			studyList.add(study);
		}
		
	return studyList;
		
	}
	
	public Study getBiiStudy(String studyAcc){
		
		Study study = studyDAO.getStudy(studyAcc);
		
		if (studyAcc.isEmpty() || study.getAcc().isEmpty())
			return new Study();
			
		return study;
	}

}
