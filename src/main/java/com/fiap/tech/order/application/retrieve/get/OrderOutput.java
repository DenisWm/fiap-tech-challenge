package com.fiap.tech.order.application.retrieve.get;

import com.fiap.tech.client.domain.Client;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.product.domain.Product;
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
    private OrderedItemOutput.OrderedItemClientOutput client;
    private OrderStatus status;

    public OrderOutput(String id, Instant timestamp, BigDecimal total, List<OrderedItemOutput> orderedItems, OrderedItemOutput.OrderedItemClientOutput client, OrderStatus status) {
        this.id = id;
        this.timestamp = timestamp;
        this.total = total;
        this.orderedItems = orderedItems;
        this.client = client;
        this.status = status;
    }


    public static OrderOutput from(final Order aOrder) {
        return new OrderOutput(
                aOrder.getId().getValue(),
                aOrder.getTimestamp(),
                aOrder.getTotal(),
                new ArrayList<>(),
                null,
                aOrder.getStatus()
        );
    }

    public List<OrderedItemOutput> getOrderedItems() {
        return orderedItems;
    }

    public OrderOutput withOrderedItemsProductsAndClient(List<OrderedItem> orderedItems, final List<Product> products, Client client) {
        List<OrderedItemOutput> orderedItemsOutput = new ArrayList<>();
        for (int i = 0; i < orderedItems.size(); i++) {
            final var orderedItem = orderedItems.get(i);
            final var product = products.stream()
                    .filter(p -> p.getId().getValue().equals(orderedItem.getProduct().getValue())).findFirst().orElse(null);
            orderedItemsOutput.add(OrderedItemOutput.from(orderedItem, product));
        }
        return new OrderOutput(id, timestamp, total, orderedItemsOutput, OrderedItemOutput.OrderedItemClientOutput.from(client), status);
    }

    public record OrderedItemOutput(String id, Integer quantity, BigDecimal subTotal, OrderedItemProductOutput product) {
        public static OrderedItemOutput from(final OrderedItem aOrderedItem, final Product aProduct) {
            return new OrderedItemOutput(
                    aOrderedItem.getId().getValue(),
                    aOrderedItem.getQuantity(),
                    aOrderedItem.getSubTotal(),
                    OrderedItemProductOutput.from(aProduct.getId().getValue(), aProduct.getName(), aProduct.getPrice())
            );
        }

        public record OrderedItemProductOutput(
                String id,
                String name,
                BigDecimal price
        ) {

            public static OrderedItemProductOutput from(final String id, final String name, BigDecimal price) {
                return new OrderedItemProductOutput(
                        id,
                        name,
                        price
                );
            }
        }

        public record OrderedItemClientOutput(
                String id,
                String name,
                String cpf
        ) {

            public static OrderedItemClientOutput from(final Client client) {
                return new OrderedItemClientOutput(
                        client.getId().getValue(),
                        client.getName(),
                        client.getCpf()
                );
            }
        }
    }
}
