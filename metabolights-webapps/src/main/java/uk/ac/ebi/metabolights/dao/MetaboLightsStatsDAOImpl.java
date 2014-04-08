/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/8/14 9:18 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsStatsDAOImpl.java
 *
 * Last modified: 1/21/13 11:21 AM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
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

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/01/2013
 * Time: 11:21
 */
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
