/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/8/14 3:11 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.model.MLStats;

import java.util.List;

@Repository
public class MetaboLightsStatsDAOImpl implements MetaboLightsStatsDAO {
    private static Logger logger = Logger.getLogger(MetaboLightsStatsDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<MLStats> getByCategory(String categoryName) {

        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from MLStats where pageSection = :param order by sortOrder,displayName asc ");
        q.setString("param", categoryName);
        List<MLStats> statsList = q.list();
        session.clear();

        if (statsList !=null && statsList.size()>0)
            return statsList;
        else
            return null;
    }

    @Override
    public List<MLStats> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from MLStats order by pageSection, sortOrder asc");
        List<MLStats> statsList = q.list();
        session.clear();

        if (statsList !=null && statsList.size()>0)
            return statsList;
        else
            return null;
    }
}
