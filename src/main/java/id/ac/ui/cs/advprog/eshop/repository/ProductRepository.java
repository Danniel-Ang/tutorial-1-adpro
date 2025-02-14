package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public boolean delete(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                productData.remove(product);
                return true;
            }
        }
        return false;
    }

    public Product findById(String productId) {
        for (Product product : productData) {
            if(product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product update(Product updatedProduct) {
        for (Product product : productData) {
            if(product.getProductId().equals(updatedProduct.getProductId())) {
                product.setProductId(updatedProduct.getProductId());
                product.setProductName(updatedProduct.getProductName());
                product.setProductQuantity(updatedProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}
