package com.fiap.tech.categories.infra;


import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.product.infra.persistense.ProductJpaEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
public class CategoryJpaEntity {

    @Id
    private String id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<ProductJpaEntity> products;


    public CategoryJpaEntity(final String id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static CategoryJpaEntity from(Category aCategory) {
        return new CategoryJpaEntity(aCategory.getId().getValue(), aCategory.getName(), aCategory.getDescription());
    }

    public Category toAggregate() {
        return Category.with(CategoryID.from(this.id), this.name, this.description);
    }

}
