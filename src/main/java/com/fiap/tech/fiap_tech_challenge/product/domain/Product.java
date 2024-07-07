package com.fiap.tech.fiap_tech_challenge.product.domain;

import com.fiap.tech.fiap_tech_challenge.categories.domain.Category;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    @Id
    private Long id;

    private String name;

    private Category category;

    private BigDecimal price;

    private String description;
}
