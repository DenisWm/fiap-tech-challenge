package com.fiap.tech.fiap_tech_challenge.product.domain;


import java.util.List;

public interface ProductGateway {

    Product getProduct(String productId);
    List<Product> getAllProducts();
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(String productId);


}
