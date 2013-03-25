package uk.ac.ebi.metabolights.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.model.MetaboLightsSubmittedId;
import uk.ac.ebi.metabolights.model.StableId;

@Repository
public class StableIdDAOImpl implements StableIdDAO {

	private static Logger logger = Logger.getLogger(StableIdDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public StableId getNextStableId() {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from StableId");

		logger.debug("retrieving StableId");
		StableId stableId = (StableId) q.uniqueResult();
		
		if (stableId != null){
			logger.debug("Got StableId sequence = " + stableId.getSeq());
		}else{
			logger.debug("WARNING: No StableId");
		}

		return stableId;
	}

	public void update(StableId stableId) {
		Session session = sessionFactory.getCurrentSession();
		session.update(stableId);
		logger.debug("Updated StableId sequence =" +stableId.getSeq());
		
	}

    @Transactional
    public void storeInitialId(String oldId, String newId) {
        Session session = sessionFactory.getCurrentSession();

        if (getInitialId(newId) == null)   {         //Ignore if we already have stored this
            MetaboLightsSubmittedId submittedId = new MetaboLightsSubmittedId(oldId, newId);
            session.save(submittedId);
        }
    }

    @Transactional
    public MetaboLightsSubmittedId getInitialId(String studyAcc) {
        Session session = sessionFactory.getCurrentSession();

        Query q = session.createQuery("from MetaboLightsSubmittedId where studyAcc = :acc");
        q.setParameter("acc", studyAcc);

        logger.debug("retrieving MetaboLightsSubmittedId");
        MetaboLightsSubmittedId submittedId = (MetaboLightsSubmittedId) q.uniqueResult();

        return submittedId;
    }

}
