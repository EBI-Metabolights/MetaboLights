package uk.ac.ebi.metabolomes.springdemo.service;


import uk.ac.ebi.metabolomes.springdemo.domain.Product;
import uk.ac.ebi.metabolomes.springdemo.repository.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class SimpleProductManager implements ProductManager {

	private static final long serialVersionUID = -7726544683358051989L;

	// private List<Product> products;
	//  public void setProducts(List<Product> products) {
	//  this.products = products;
	//}

	private ProductDao productDao;
    
    public List<Product> getProducts() {
        // return products;
        return productDao.getProductList();
    }

    public void increasePrice(int percentage) {
        List<Product> products = productDao.getProductList();
        if (products != null) {
            for (Product product : products) {
                double newPrice = product.getPrice().doubleValue() * 
                                    (100 + percentage)/100;
                product.setPrice(newPrice);
                productDao.saveProduct(product);
            }
        }
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    
}