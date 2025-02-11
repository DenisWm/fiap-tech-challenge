package com.fiap.tech.categories.infra;


import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.product.infra.persistense.ProductJpaEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="categories")
@Data
public class CategoryJpaEntity {

    @Id
    private String id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<ProductJpaEntity> products;

    public Category toAggregate() {
        return Category.with(CategoryID.from(this.getId()), this.getName(), this.getDescription());
    }

}
