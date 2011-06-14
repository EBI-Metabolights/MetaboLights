package uk.ac.ebi.metabolights.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;

/**
 * DAO implementation for bioinvindex.model.Study.
 */
@Repository
public class StudyDAOImpl implements StudyDAO{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Study> findStudiesForUser(String userName) {
		/*
		 * TODO, fix this.
		 * Problem: user is queried trough user_studies.user_id -> user_details.id.  
		 * Have to use the getters to avoid lazy loading errors around Study.assayResults or Study.users 
		 */
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("FROM Study as s join s.users as u WHERE u.userName = :userName OR s.status = :status");
		q.setParameter("userName", userName);
		q.setParameter("status", VisibilityStatus.PUBLIC);

		List<Study> studyList = q.list();
		session.clear();

		return studyList;

	}

	@Transactional
	public Study getStudy(String studyAcc) {
		
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("FROM Study WHERE acc = :acc");
		q.setParameter("acc", studyAcc);
		
		Study localStudy = (Study) q.uniqueResult();
		
		session.clear();
		
		return localStudy;
		
	}


}
