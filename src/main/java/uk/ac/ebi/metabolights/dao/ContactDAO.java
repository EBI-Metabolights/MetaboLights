package uk.ac.ebi.metabolights.dao;

import java.util.List;

import uk.ac.ebi.metabolights.form.Contact;
 
public interface ContactDAO {
    public void addContact(Contact contact);
    public List<Contact> listContact();
    public void removeContact(Integer id);
}