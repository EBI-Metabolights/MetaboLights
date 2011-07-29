package uk.ac.ebi.metabolights.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.dao.StudyDAO;

@Service
public class StudyServiceImpl implements StudyService {
	
	private static Logger logger = Logger.getLogger(StudyServiceImpl.class);

	@Autowired
	private StudyDAO studyDAO;

	@Transactional
	public Study getBiiStudy(String studyAcc){
		Study study = studyDAO.getStudy(studyAcc);
		if (studyAcc.isEmpty() || study.getAcc().isEmpty())
			return new Study();
		return study;
	}


}
