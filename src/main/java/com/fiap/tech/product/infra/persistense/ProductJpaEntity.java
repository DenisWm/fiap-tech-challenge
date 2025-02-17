package com.fiap.tech.product.infra.persistense;

import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.categories.infra.CategoryJpaEntity;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductID;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity(name="Product")
@Table(name="products")
@Getter
public class ProductJpaEntity {

    @Id
    private String id;

    private String name;

    @ManyToOne(targetEntity = CategoryJpaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryJpaEntity category;

    @Column(name = "category_id")
    private String categoryId;


    private BigDecimal price;

    private String description;

    public ProductJpaEntity(final String id, final String name, final BigDecimal price, final String description, final String aCategoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = aCategoryId;
    }

    public ProductJpaEntity() {

    }

    public static ProductJpaEntity from(final Product aProduct) {
        return new ProductJpaEntity(aProduct.getId().getValue(), aProduct.getName(), aProduct.getPrice(), aProduct.getDescription(), aProduct.getCategory().getValue());

    }

    public Product toAggregate() {
        return Product.with(ProductID.from(this.getId()), this.getName(), this.getDescription(), this.getPrice(),
                CategoryID.from(this.getCategoryId()));

    }
}
