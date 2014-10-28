/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/14/14 11:58 AM
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
import uk.ac.ebi.metabolights.model.MLStats;

import java.util.List;

@Repository
public class MetaboLightsStatsDAOImpl implements MetaboLightsStatsDAO {
    private static Logger logger = LoggerFactory.getLogger(MetaboLightsStatsDAOImpl.class);

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
