package com.fiap.tech.fiap_tech_challenge.categories.domain;

import com.fiap.tech.fiap_tech_challenge.common.domain.AggregateRoot;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.ValidationHandler;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category extends AggregateRoot<CategoryID> {


    private String name;

    private String description;

    public Category(CategoryID aCategoryId, final String aName, final String aDescription) {
        super(aCategoryId);
        this.name= aName;
        this.description = aDescription;
    }

    public static Category with(CategoryID id, String name, String description) {
        return new Category(id, name, description);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}
