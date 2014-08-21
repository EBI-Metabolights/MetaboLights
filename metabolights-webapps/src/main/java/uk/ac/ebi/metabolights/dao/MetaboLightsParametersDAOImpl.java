/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/8/13 3:34 PM
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
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
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
    @Transactional
    public List<MetaboLightsParameters> getAll() {

        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from MetaboLightsParameters");
        List<MetaboLightsParameters> parametersList = q.list();
        session.clear();

        return parametersList;
    }

    @Override
    @Transactional
    public void update(MetaboLightsParameters metaboLightsParameters) {
        Session session = sessionFactory.getCurrentSession();
        session.update(metaboLightsParameters);
    }

    @Override
    @Transactional
    public void insert(MetaboLightsParameters metaboLightsParameters) {
        Session session = sessionFactory.getCurrentSession();
        session.save(metaboLightsParameters);
    }

    @Override
    @Transactional
    public void delete(MetaboLightsParameters metaboLightsParameters) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(metaboLightsParameters);
    }
}
