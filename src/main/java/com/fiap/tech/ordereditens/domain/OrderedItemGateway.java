package com.fiap.tech.ordereditens.domain;

import java.util.List;
import java.util.Optional;

public interface OrderedItemGateway {

    OrderedItem create(OrderedItem orderedItem);
    OrderedItem update(OrderedItem orderedItem);
    Optional<OrderedItem> findById(OrderedItemID anId);
    List<OrderedItem> findByIds(List<String> orderedItemIds);

}
