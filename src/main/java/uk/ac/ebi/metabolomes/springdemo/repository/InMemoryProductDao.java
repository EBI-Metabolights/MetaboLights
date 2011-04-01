package uk.ac.ebi.metabolomes.springdemo.repository;

import java.util.List;

import uk.ac.ebi.metabolomes.springdemo.domain.Product;

/**
 * We rewrote the SimpleProductManager and now the tests will of course fail. We need to provide the ProductManager with an in-memory implementation 
 * of the ProductDao. We don't really want to use the real DAO here since we'd like to avoid having to access a database 
 * for our unit tests. We will add an internal class called InMemoryProductDao that will hold on to a list pf products provided in the constructor. 
 * This in-memory class has to be passed in when we create a new SimpleProductManager.
 *
 */
public class InMemoryProductDao implements ProductDao {

    private List<Product> productList;

    public InMemoryProductDao(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void saveProduct(Product prod) {
    }

}