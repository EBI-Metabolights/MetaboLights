package uk.ac.ebi.metabolomes.springdemo.service;

import java.io.Serializable;
import java.util.List;
import uk.ac.ebi.metabolomes.springdemo.domain.Product;

/**
 * Next we create the ProductManager. This is the service responsible for handling products. It contains two methods: 
 * a business method  increasePrice() that increases prices for all products and a getter method getProducts() 
 * for retrieving all products. 
 * 
 * We have chosen to make it an interface instead of a concrete class for an number of reasons. 
 * First of all, it makes writing unit tests for Controllers easier (as we'll see in the next chapter). 
 * Secondly, the use of interfaces means JDK proxying (a Java language feature) can be used to make the 
 * service transactional instead of CGLIB (a code generation library).

 */
public interface ProductManager extends Serializable{

    public void increasePrice(int percentage);
    
    public List<Product> getProducts();
    
}