/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/15/14 3:33 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.webservice.dao.hibernate;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.metabolights.webservice.dao.UserDAO;
import uk.ac.ebi.metabolights.webservice.model.User;

import java.util.List;

/**
 * DAO implementation for Users.
 */
@Repository

public class UserDAOHibernate implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

    @Override
	public User findByName(String userName) {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User where userName =:u");
		q.setString("u", userName.toLowerCase()); //! userNames are stored lowercase
		List<User> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;

	}
	@Override
	public User findByUserToken(String userToken) {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User where api_Token =:u");
		q.setString("u", userToken); //! userNames are stored lowercase
		List<User> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;

	}


	@Override
	public List<User> getAll() {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User order by lastName");
		List<User> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list;
		else
			return null;

	}

	@Override
	public User findByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User where email =:e");
		q.setString("e", email);
		List<User> list = q.list();
		session.clear();

		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public Long insert(User user) {
		Session session = sessionFactory.getCurrentSession();
		// Note: Spring manages the transaction, and the commit
		session.save(user);
		return user.getUserId();
	}

	@Override
	public void update(User changedUser) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("load by ID "+changedUser.getUserId()+" new password encoded is "+changedUser.getDbPassword());
		User user = (User) session.load(User.class, changedUser.getUserId());
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
	public User findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User where id =:id");
		q.setLong("id", id);
		List<User> list = q.list();
		session.clear();
		if (list !=null && list.size()>0)
			return list.get(0);
		else
			return null;

	}

	@Override
	public void delete(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}
}
