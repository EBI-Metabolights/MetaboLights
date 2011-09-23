package uk.ac.ebi.metabolights.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
		logger.debug("Got StableId sequence =" +stableId.getSeq());

		return stableId;
	}

	public void update(StableId stableId) {
		Session session = sessionFactory.getCurrentSession();
		session.update(stableId);
		logger.debug("Updated StableId sequence =" +stableId.getSeq());
		
	}

}
