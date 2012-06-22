package uk.ac.ebi.metabolights.dao;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * DAO implementation for MetabolightsUsers.
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

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

	public List<MetabolightsUser> getAll() {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from MetabolightsUser");
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
