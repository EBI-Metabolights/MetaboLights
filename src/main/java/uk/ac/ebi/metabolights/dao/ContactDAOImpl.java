package uk.ac.ebi.metabolights.dao;

import java.util.List;

import uk.ac.ebi.metabolights.form.Contact;
 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
/**
 * ContactDAOImpl implements the data access interface ContactDAO which defines methods such as listContact(), addContact() etc to access data from database.
 * Note that we have used two Spring annotations @Repository and @Autowired.
 * Classes marked with annotations are candidates for auto-detection by Spring when using annotation-based configuration and 
 * classpath scanning. The @Component annotation is the main stereotype that indicates that an annotated class is a “component”.
 * 
 * The @Repository annotation is yet another stereotype that was introduced in Spring 2.0. This annotation is used to indicate that a class functions 
 * as a repository and needs to have exception translation applied transparently on it. The benefit of exception translation is that the service layer 
 * only has to deal with exceptions from Spring’s DataAccessException hierarchy, even when using plain JPA in the DAO classes.
 * 
 * Another annotation used in ContactDAOImpl is @Autowired. This is used to autowire the dependency of the ContactDAOImpl on the SessionFactory.
 *
 */

@Repository
public class ContactDAOImpl implements ContactDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public void addContact(Contact contact) {
        sessionFactory.getCurrentSession().save(contact);
    }
 
    public List<Contact> listContact() {
 
        return sessionFactory.getCurrentSession().createQuery("from Contact")
                .list();
    }
 
    public void removeContact(Integer id) {
        Contact contact = (Contact) sessionFactory.getCurrentSession().load(
                Contact.class, id);
        if (null != contact) {
            sessionFactory.getCurrentSession().delete(contact);
        }
 
    }
}