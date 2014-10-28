/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/29/14 4:23 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.model.MetaboLightsStudyXRef;
import uk.ac.ebi.metabolights.model.StableId;

import java.util.Collection;
import java.util.List;

@Repository
public class StableIdDAOImpl implements StableIdDAO {

	private static Logger logger = LoggerFactory.getLogger(StableIdDAOImpl.class);

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
            MetaboLightsStudyXRef submittedId = new MetaboLightsStudyXRef(oldId, newId);
            session.save(submittedId);
        }
    }

    @Transactional
    public MetaboLightsStudyXRef getInitialId(String studyAcc) {
        Session session = sessionFactory.getCurrentSession();

		String studyXRefClass = MetaboLightsStudyXRef.class.getSimpleName();
		Query q = session.createQuery("from " + studyXRefClass + " where studyAcc = :acc AND isOriginalId = 1");
        q.setParameter("acc", studyAcc);

        logger.debug("retrieving " + studyXRefClass);
        MetaboLightsStudyXRef submittedId = (MetaboLightsStudyXRef) q.uniqueResult();

        return submittedId;
    }

	@Transactional
	public List<MetaboLightsStudyXRef> getStudyXRefs(String studyAcc) {
		Session session = sessionFactory.getCurrentSession();

		String studyXRefClass = MetaboLightsStudyXRef.class.getSimpleName();
		Query q = session.createQuery("from " + studyXRefClass + " where studyAcc = :acc");
		q.setParameter("acc", studyAcc);

		logger.debug("retrieving all " + studyXRefClass);
		List<MetaboLightsStudyXRef> studyXRefList =  q.list();

		return studyXRefList;
	}
}

