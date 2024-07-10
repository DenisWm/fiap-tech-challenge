package com.fiap.tech.fiap_tech_challenge.product.domain;


import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    Optional<Product> findById(ProductID anId);
    Pagination<Product> findAll(SearchQuery aQuery);
    Product create(Product aProduct);
    Product update(Product aProduct);
    void deleteById(ProductID anId);


}
