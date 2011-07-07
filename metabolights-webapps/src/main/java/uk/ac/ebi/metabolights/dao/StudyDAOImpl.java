package uk.ac.ebi.metabolights.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Protocol;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.Design;
import uk.ac.ebi.bioinvindex.model.term.OntologyTerm;

/**
 * DAO implementation for bioinvindex.model.Study.
 */
@Repository
public class StudyDAOImpl implements StudyDAO{

	private static Logger logger = Logger.getLogger(StudyDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * TODO dok
	 */
	@Override
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

	/**
	 * Retrieve a study based on the accession identifier.  
	 */
	@Override
	public Study getStudy(String studyAcc) {

		Session session = sessionFactory.getCurrentSession();
		
		Query q = session.createQuery("FROM Study WHERE acc = :acc");
		q.setParameter("acc", studyAcc);

		logger.debug("retrieving study "+studyAcc);
		Study study = (Study) q.uniqueResult();
		
		/*
		 * Initialize lazy collections here that we want to display .. otherwise the JSP will throw an error on rendering
		 */
		Hibernate.initialize(study.getContacts());
		Hibernate.initialize(study.getAnnotations());
		Hibernate.initialize(study.getPublications());

		Collection<AssayResult> assayResults = study.getAssayResults();
		Hibernate.initialize(assayResults);
		for (AssayResult assayResult : assayResults) {
			Hibernate.initialize(assayResult.getData().getFactorValues());
			Hibernate.initialize(assayResult.getCascadedPropertyValues());
			Hibernate.initialize(assayResult.getAssays());
		}
		Hibernate.initialize(study.getProtocols());
		
		session.clear();
		return study;
	}

}
