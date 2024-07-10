package com.fiap.tech.fiap_tech_challenge.product.domain;


import com.fiap.tech.fiap_tech_challenge.common.domain.validation.Error;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.ValidationHandler;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.Validator;

import java.math.BigDecimal;

public class ProductValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 1;
    public static final String NAME_SHOULD_NOT_BE_NULL = "'name' should not be null";
    public static final String NAME_SHOULD_NOT_BE_EMPTY = "'name' should not be empty";
    public static final String NAME_MUST_BE_BETWEEN_1_AND_255_CHARACTERS = "'name' must be between 1 and 255 characters";
    public static final String PRICE_SHOULD_NOT_BE_NULL = "'price' should not be null";
    public static final String PRICE_MUST_BE_GREATER_THAN_0 = "'price' must be greater than 0";
    private final Product product;
    public ProductValidator(Product product, ValidationHandler aHandler) {
        super(aHandler);
        this.product = product;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkPriceConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.product.getName();
        if(name == null) {
            getHandler().append(new Error(NAME_SHOULD_NOT_BE_NULL));
            return;
        }
        if(name.isBlank()) {
            getHandler().append(new Error(NAME_SHOULD_NOT_BE_EMPTY));
            return;
        }
        final int nameLength = name.trim().length();
        if(nameLength < NAME_MIN_LENGTH || nameLength > NAME_MAX_LENGTH) {
            getHandler().append(new Error(NAME_MUST_BE_BETWEEN_1_AND_255_CHARACTERS));
        }
    }

    private void checkPriceConstraints() {
        final var price = this.product.getPrice();
        if(price == null) {
            getHandler().append(new Error(PRICE_SHOULD_NOT_BE_NULL));
            return;
        }
        if(price.compareTo(BigDecimal.ZERO) <= 0) {
            getHandler().append(new Error(PRICE_MUST_BE_GREATER_THAN_0));
        }
    }
}
