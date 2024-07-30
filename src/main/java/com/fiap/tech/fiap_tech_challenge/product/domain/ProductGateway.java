package com.fiap.tech.fiap_tech_challenge.product.domain;


import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.domain.pagination.ProductSearchQuery;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    Optional<Product> findById(ProductID anId);
    Pagination<Product> findAll(ProductSearchQuery aQuery);
    Product create(Product aProduct);
    Product update(Product aProduct);
    void deleteById(ProductID anId);


    List<ProductID> existsByIds(List<String> products);

    List<Product> findByIds(List<String> productIds);
}
