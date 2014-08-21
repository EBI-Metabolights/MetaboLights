/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/16/14 9:59 AM
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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

import java.util.List;

/**
 * DAO implementation for MetabolightsUsers.
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

    @Override
	public MetabolightsUser findByName(String userName) {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from MetabolightsUser where userName =:u");
		q.setString("u", userName.toLowerCase()); //! userNames are stored lowercase
		List<MetabolightsUser> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;

	}

    @Override
	public List<MetabolightsUser> getAll() {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from MetabolightsUser order by lastName");
		List<MetabolightsUser> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list;
		else
			return null;

	}

	@Override
	public MetabolightsUser findByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from MetabolightsUser where email =:e");
		q.setString("e", email);
		List<MetabolightsUser> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public Long insert(MetabolightsUser user) {
		Session session = sessionFactory.getCurrentSession();
		// Note: Spring manages the transaction, and the commit
		session.save(user);
		return user.getUserId();
	}

	@Override
	public void update(MetabolightsUser changedUser) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("load by ID "+changedUser.getUserId()+" new password encoded is "+changedUser.getDbPassword());
		MetabolightsUser user = (MetabolightsUser) session.load(MetabolightsUser.class, changedUser.getUserId());
		try {
			BeanUtils.copyProperties(user,changedUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not copy changed properties to user");
		}
		//user.setDbPassword(IsaTabAuthenticationProvider.encode(user.getDbPassword()));
		session.update(user);
	}

	@Override
	public MetabolightsUser findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from MetabolightsUser where id =:id");
		q.setLong("id", id);
		List<MetabolightsUser> list = q.list();
		session.clear();
		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;

	}

	@Override
	public void delete(MetabolightsUser user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}
}
