package com.fiap.tech.fiap_tech_challenge.product.infra.persistense;

import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryID;
import com.fiap.tech.fiap_tech_challenge.categories.infra.CategoryJpaEntity;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@Data
public class ProductJpaEntity {

    @Id
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;


    private BigDecimal price;

    private String description;

    public ProductJpaEntity(final String id, final String name, final BigDecimal price, final String description, final CategoryJpaEntity category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public ProductJpaEntity() {

    }

    public static ProductJpaEntity from(final Product aProduct, final CategoryJpaEntity category) {
        return new ProductJpaEntity(aProduct.getId().getValue(), aProduct.getName(), aProduct.getPrice(), aProduct.getDescription(), category);

    }

    public Product toAggregate() {
        return Product.with(ProductID.from(this.getId()), this.getName(), this.getDescription(), this.getPrice(),
                CategoryID.from(this.getCategory().getId()));

    }
}
