package uk.ac.ebi.metabolights.service;

import java.util.List;

import uk.ac.ebi.metabolights.form.Contact;
 
public interface ContactService {
 
    public void addContact(Contact contact);
    public List<Contact> listContact();
    public void removeContact(Integer id);
}