package uk.ac.ebi.metabolights.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import uk.ac.ebi.metabolights.dao.ContactDAO;
import uk.ac.ebi.metabolights.form.Contact;
 
/**
 * We have created an interface ContactService and implemented it in class ContactServiceImpl. Note that we used few Spring annotations 
 * such as @Service, @Autowired and @Transactional in our code. These annotations are called Spring stereotype annotations.
 * 
 * The @Service stereotype annotation used to decorate the ContactServiceImpl class is a specialized form of the @Component annotation. 
 * It is appropriate to annotate the service-layer classes with @Service to facilitate processing by tools or anticipating any future 
 * service-specific capabilities that may be added to this annotation. 
 *
 */

@Service
public class ContactServiceImpl implements ContactService {
 
    @Autowired
    private ContactDAO contactDAO;
 
    @Transactional
    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }
 
    @Transactional
    public List<Contact> listContact() {
 
        return contactDAO.listContact();
    }
 
    @Transactional
    public void removeContact(Integer id) {
        contactDAO.removeContact(id);
    }
}