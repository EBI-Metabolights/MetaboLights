package uk.ac.ebi.metabolights.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.dao.StudyDAO;

import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {
	
	private static Logger logger = Logger.getLogger(StudyServiceImpl.class);

	@Autowired
	private StudyDAO studyDAO;

    //Called from the study Queue system
    @Transactional
    public Study getBiiStudy(String studyAcc,boolean clearSession, boolean fromQueue) throws IllegalAccessException {
        Study study = studyDAO.getStudy(studyAcc,clearSession, fromQueue);
        if (studyAcc.isEmpty() || study.getAcc().isEmpty())
            return new Study();
        return study;
    }

    //Called from user interation in the webapp
	@Transactional
	public Study getBiiStudy(String studyAcc, boolean clearSession) throws IllegalAccessException {
		return getBiiStudy(studyAcc, clearSession, false);
	}

	@Transactional
	public void update(Study study){
		studyDAO.update(study);
	}

    @Transactional
    public List<String> findAllStudies() {
        return studyDAO.findAllAcc();
    }


}
