package uk.ac.ebi.metabolights.controller;


import java.util.Map;

import uk.ac.ebi.metabolights.form.Contact;
import uk.ac.ebi.metabolights.service.ContactService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * The spring controller defines three methods to manipulate contact manager application.
 * listContacts method – This method uses Service interface ContactServer to fetch all the contact details in our application. 
 * It returns an array of contacts. Note that we have mapped request “/contact” to this method. 
 * Thus Spring will automatically calls this method whenever it encounters this url in request.
 * 
 * addContact method – This method adds a new contact to contact list. The contact details are fetched in Contact object 
 * using @ModelAttribute annotation. Also note that the request “/add” is mapped with this method. The request method 
 * should also be POST. Once the contact is added in contact list using ContactService, we redirect to /index page which in turn 
 * calls listContacts() method to display contact list to user.
 * 
 * deleteContact method – This methods removes a contact from the contact list. Similar to addContact this method also redirects user 
 * to /index page once the contact is removed. One this to note in this method is the way we have mapped 
 * request url using @RequestMapping annotation. The url “/delete/{contactId}” is mapped thus whenever user send a 
 * request /delete/12, the deleteCotact method will try to delete contact with ID:12.
 * 
 */

@Controller
public class ContactController {
 
    @Autowired
    private ContactService contactService;
 
    @RequestMapping("/contact") // so: annotations are being used to map URL requests to Java classes. I'll be damned 
    public String listContacts(Map<String, Object> map) {
 
        map.put("contact", new Contact());                    // "contact" is an object name for the target JSP to use
        map.put("contactList", contactService.listContact()); //  similar for "contactList"
 
        return "contact"; // this will be resolved to a contact.jsp file
    }
 
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") Contact contact, BindingResult result) {
        contactService.addContact(contact);
        return "redirect:/contact";
    }
 
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId")
    Integer contactId) {
        contactService.removeContact(contactId);
        return "redirect:/contact";
    }
}
