package uk.ac.ebi.metabolights.form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * http://viralpatel.net/blogs/2010/11/spring3-mvc-hibernate-maven-tutorial-eclipse-example.html
 *  
 * You’ll notice that the import statements import from javax.persistence rather than a Hibernate or Spring package. 
 * Using Hibernate with Spring, the standard JPA annotations work just as well and that’s what I’m using here.
 * 
 * First we’ve annotated the class with @Entity which tells Hibernate that this class represents an object that we can persist.
 * 
 * The @Table(name = "CONTACTS") annotation tells Hibernate which table to map properties in this class to. 
 * The first property in this class is our object ID which will be unique for all events persisted. This is why we’ve annotated it with @Id.
 * The @GeneratedValue annotation says that this value will be determined by the datasource, not by the code.
 * 
 * The @Column(name = "FIRSTNAME") annotation is used to map this property to the FIRSTNAME column in the CONTACTS table.
 * 
 */

@Entity
@Table(name="CONTACTS")
public class Contact {
 
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
 
    @Column(name="FIRSTNAME")
    private String firstname;
 
    @Column(name="LASTNAME")
    private String lastname;
 
    @Column(name="EMAIL")
    private String email;
 
    @Column(name="TELEPHONE")
    private String telephone;
 
    
    
    // Getters and setters
    public String getEmail() {
        return email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}