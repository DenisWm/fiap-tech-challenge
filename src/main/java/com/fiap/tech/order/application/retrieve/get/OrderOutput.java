package com.fiap.tech.order.application.retrieve.get;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductID;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Getter
public class OrderOutput {
    private String id;
    private Instant timestamp;
    private BigDecimal total;
    private List<OrderedItemOutput> orderedItems;
    private String clientId;
    private OrderStatus status;

    public OrderOutput(String id, Instant timestamp, BigDecimal total, List<OrderedItemOutput> orderedItems, String clientId, OrderStatus status) {
        this.id = id;
        this.timestamp = timestamp;
        this.total = total;
        this.orderedItems = orderedItems;
        this.clientId = clientId;
        this.status = status;
    }


    public static OrderOutput from(final Order aOrder) {
        return new OrderOutput(
                aOrder.getId().getValue(),
                aOrder.getTimestamp(),
                aOrder.getTotal(),
                new ArrayList<>(),
                aOrder.getClientId() != null ? aOrder.getClientId().getValue() : null,
                aOrder.getStatus()
        );
    }

    public OrderOutput withOrderedItems(final List<OrderedItem> orderedItems) {
        return new OrderOutput(id, timestamp, total, orderedItems.stream().map(OrderedItemOutput::from).toList(), clientId, status);
    }

    public List<OrderedItemOutput> getOrderedItems() {
        return orderedItems;
    }

    public record OrderedItemOutput(String id, Integer quantity, BigDecimal price, OrderedItemProductOutput product) {
        public static OrderedItemOutput from(final OrderedItem aOrderedItem) {
            return new OrderedItemOutput(
                    aOrderedItem.getId().getValue(),
                    aOrderedItem.getQuantity(),
                    aOrderedItem.getPrice(),
                    OrderedItemProductOutput.from(aOrderedItem.getProduct().getValue(), aOrderedItem.getProductName(), aOrderedItem.getProductPrice())
            );
        }

        public record OrderedItemProductOutput(
                String id,
                String name,
                BigDecimal price
        ) {

            public static OrderedItemProductOutput from(final String id, final String name, final BigDecimal price) {
                return new OrderedItemProductOutput(
                        id,
                        name,
                        price
                );
            }
        }
    }
}
