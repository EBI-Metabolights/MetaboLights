package uk.ac.ebi.metabolomes.springdemo.repository;

import java.util.List;

import uk.ac.ebi.metabolomes.springdemo.domain.Product;

public interface ProductDao {

    public List<Product> getProductList();

    public void saveProduct(Product prod);

}