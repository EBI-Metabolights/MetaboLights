package uk.ac.ebi.metabolights.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.ebi.metabolights.authenticate.MetabolightsUser;

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
        q.setString("u", userName);
        List<MetabolightsUser> list = q.list();
        session.clear();

        if (list !=null && list.size()>0)
        	return list.get(0);
        else
        	return null;

    }
}
