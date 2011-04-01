package uk.ac.ebi.metabolomes.springdemo.domain;


import java.io.Serializable;
/**
 * 
 * First we implement the Product class as a POJO with a default constructor (automatically provided if we don't specify 
 * any constructors) and getters and setters for its properties 'description' and 'price'. Let's also make it Serializable, 
 * not necessary for our application, but could come in handy later on when we persist and store its state. 
 * The class is a domain object, so it belongs in the 'domain' package.
 * 
 */
public class Product implements Serializable {
    private int id;
    private String description;
    private Double price;
    
    public void setId(int i) {
        id = i;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Description: " + description + ";");
        buffer.append("Price: " + price);
        return buffer.toString();
    }
}