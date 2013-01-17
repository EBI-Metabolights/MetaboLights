/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsParametersDAOImpl.java
 *
 * Last modified: 1/17/13 10:56 AM
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
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 17/01/2013
 * Time: 10:56
 */

@Repository
public class MetaboLightsParametersDAOImpl implements MetaboLightsParametersDAO{

    private static Logger logger = Logger.getLogger(MetaboLightsParametersDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public MetaboLightsParameters getOnName(String parameterName) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from MetaboLightsParameters where parameterName =:param");
        q.setString("param", parameterName);
        List<MetaboLightsParameters> list = q.list();
        session.clear();

        if (list !=null && list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public List<MetaboLightsParameters> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from MetaboLightsParameters");
        List<MetaboLightsParameters> parametersList = q.list();
        session.clear();

        return parametersList;
    }

    @Override
    public void update(MetaboLightsParameters metaboLightsParameters) {
        Session session = sessionFactory.getCurrentSession();
        session.update(metaboLightsParameters);
    }

    @Override
    public void insert(MetaboLightsParameters metaboLightsParameters) {
        Session session = sessionFactory.getCurrentSession();
        session.save(metaboLightsParameters);
    }

    @Override
    public void delete(MetaboLightsParameters metaboLightsParameters) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(metaboLightsParameters);
    }
}
