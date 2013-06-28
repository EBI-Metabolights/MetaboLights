package uk.ac.ebi.metabolights.dao;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.bioinvindex.model.*;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

import java.util.*;


/**
 * DAO implementation for bioinvindex.model.Study.
 */
@Repository
public class StudyDAOImpl implements StudyDAO{

	private static Logger logger = Logger.getLogger(StudyDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

    /**
     * Retrieve a study based on the accession identifier.
     * @param studyAcc accession number of desired Study
     * @param clearSession hibernate hack, set to true when only selecting (faster)
     */
    @Override
    @Transactional
    public Study getStudy(String studyAcc, boolean clearSession) throws IllegalAccessException {
        return getStudy(studyAcc, clearSession, false);
    }

	/**
	 * Retrieve a study based on the accession identifier.
	 * @param studyAcc accession number of desired Study
	 * @param clearSession hibernate hack, set to true when only selecting (faster)
     * @param fromQueue, is this initiated from the queue system
	 */
	@Override
    @Transactional
	public Study getStudy(String studyAcc, boolean clearSession, boolean fromQueue) throws IllegalAccessException {

		Session session = sessionFactory.getCurrentSession();

		String queryStr = "FROM Study WHERE acc = :acc";
		
		Query q = session.createQuery(queryStr);
		q.setParameter("acc", studyAcc);

		logger.debug("retrieving study "+studyAcc);
		Study study = (Study) q.uniqueResult();
	
		if (study == null){
			Study emptyStudy = new Study();
			emptyStudy.setAcc("Error");
			emptyStudy.setDescription("Error.  Study " +studyAcc+ " could not be found.");
			return emptyStudy;
		}
			
		if (!fromQueue){   //Only check the user if called from the webapplication

            if (!study.getStatus().equals(VisibilityStatus.PUBLIC)){ //If not PUBLIC then must be owned by the user

                Boolean validUser = false, curator = false;
                Long userId = new Long(0);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (!auth.getPrincipal().equals(new String("anonymousUser"))){
                    MetabolightsUser principal = (MetabolightsUser) auth.getPrincipal();
                    userId = principal.getUserId();
                    curator = principal.isCurator(); //To Curate or not to Curate....
                }

                if (userId>0){
                    Collection<User> users = study.getUsers();    //These are the users that have access to the Study, NOT the logged in user
                    Iterator<User> iter = users.iterator();
                    while (iter.hasNext()){
                        User user = (User) iter.next();
                        if (user.getId().equals(userId) || curator ){
                            validUser = true;
                            break;
                        }
                    }

                }

                if ( !validUser) {


//                    Study invalidStudy = new Study();
//                    invalidStudy.setAcc(VisibilityStatus.PRIVATE.toString());
//                    invalidStudy.setDescription("This is a PRIVATE study, you are not Authorised to view this study.");
//                    invalidStudy.setTitle("Please log in as the submitter or a MetaboLights curator.");
//
//                    return invalidStudy;

                    throw new IllegalAccessException("This is a PRIVATE study, you are not Authorised to view this study.") ;
                }

            }  // Study PUBLIC
        } //Called from the queue?
		
		/*
		 * Initialize lazy collections here that we want to display .. otherwise the JSP will throw an error on rendering
		 */
		Hibernate.initialize(study.getContacts());
		Hibernate.initialize(study.getAnnotations());
		Hibernate.initialize(study.getPublications());
		Hibernate.initialize(study.getAssays());
		Hibernate.initialize(study.getDesign());
		Hibernate.initialize(study.getAssayGroups());
		//Hibernate.initialize(study.getUsers());

		Collection<AssayResult> assayResults = study.getAssayResults();
		Hibernate.initialize(assayResults);
		for (AssayResult assayResult : assayResults) {
			Hibernate.initialize(assayResult.getData().getFactorValues());
			for (FactorValue fv : assayResult.getData().getFactorValues()) {
				Hibernate.initialize(fv.getOntologyTerms());
			}
			Hibernate.initialize(assayResult.getCascadedPropertyValues());
			Hibernate.initialize(assayResult.getAssays());
			
			for (Assay assay: assayResult.getAssays()){
				Hibernate.initialize(assay.getAnnotations());
			}
			
		}
		Hibernate.initialize(study.getProtocols());
		
		
		// For each assay group..initialize metabolite collection
		for (AssayGroup ag: study.getAssayGroups()){
			Hibernate.initialize(ag.getMetabolites());
			
			for (Metabolite met: ag.getMetabolites()){
				Hibernate.initialize(met.getMetaboliteSamples());
			}
		}
		
		
		
		
		if (clearSession)
			session.clear();

		return study;
	}

    @Override
    public List<String> findAllAcc() {
        Session session = sessionFactory.getCurrentSession();

        Query q = session.createQuery("SELECT acc FROM Study");

        List<String> studyList = new ArrayList<String>();

        Iterator iterator = q.iterate();
        while (iterator.hasNext()){
            String studyAcc = (String) iterator.next();
            if (studyAcc != null)
                  studyList.add(studyAcc);

        }

        if(studyList == null)
            System.out.println("No studies found");

        return studyList;

    }

    @Override
    public List<String> findStudiesGoingLive() {
        Session session = sessionFactory.getCurrentSession();

        //Finds all studies about to live in the next 7 days
        //SELECT acc FROM Study WHERE status = 1 AND TRUNC(releaseDate-7)<=trunc(sysdate)
        Query q = session.createQuery("SELECT acc FROM Study WHERE status = 1 AND ( TRUNC(releaseDate-7)=trunc(sysdate) or TRUNC(releaseDate-2)=trunc(sysdate))");

        List<String> studyList = new ArrayList<String>();

        Iterator iterator = q.iterate();
        while (iterator.hasNext()){
            String studyAcc = (String) iterator.next();
            if (studyAcc != null)
                studyList.add(studyAcc);

        }

        if(studyList == null)
            System.out.println("No studies found");

        return studyList;
    }


    @Override
    @Transactional
	public void update(Study study) {
		Session session = sessionFactory.getCurrentSession();
		// Note: Spring manages the transaction, and the commit
		// This doesn't work. THrows next exception:
		// Found two representations of same collection:
		// See https://forum.hibernate.org/viewtopic.php?p=2231400.
		// We are using session.clear() at the end of getStudy.
		// If removed, it works. Is this problem?
		session.update(study);

	}
}
