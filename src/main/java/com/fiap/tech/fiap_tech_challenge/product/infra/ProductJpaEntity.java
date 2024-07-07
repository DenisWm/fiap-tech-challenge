package com.fiap.tech.fiap_tech_challenge.product.infra;

import com.fiap.tech.fiap_tech_challenge.categories.domain.Category;
import com.fiap.tech.fiap_tech_challenge.categories.infra.CategoryJpaEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@Data
public class ProductJpaEntity {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    private BigDecimal price;

    private String description;
}
