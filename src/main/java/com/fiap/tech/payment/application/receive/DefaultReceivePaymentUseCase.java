package com.fiap.tech.payment.application.receive;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.order.domain.event.OrderPayed;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentGateway;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.product.domain.ProductGateway;

import java.util.List;
import java.util.Objects;

import static com.fiap.tech.order.domain.event.OrderPayed.*;

public class DefaultReceivePaymentUseCase extends ReceivePaymentUseCase {

    private final PaymentGateway paymentGateway;
    private final OrderGateway orderGateway;
    private final OrderedItemGateway orderedItemGateway;
    private final ProductGateway productGateway;

    public DefaultReceivePaymentUseCase(final PaymentGateway paymentGateway,
                                        final OrderGateway orderGateway,
                                        final OrderedItemGateway orderedItemGateway,
                                        final ProductGateway productGateway) {
        this.paymentGateway = Objects.requireNonNull(paymentGateway);
        this.orderGateway = Objects.requireNonNull(orderGateway);
        this.orderedItemGateway = Objects.requireNonNull(orderedItemGateway);
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public ReceivePaymentOutput execute(final ReceivePaymentCommand aCmd) {
        return orderGateway.findById(OrderID.from(aCmd.orderID()))
                .map(order -> processOrderPayment(order, aCmd))
                .orElseGet(() -> ReceivePaymentOutput.from(paymentGateway.create(Payment.unidentified())));
    }

    private ReceivePaymentOutput processOrderPayment(Order order, ReceivePaymentCommand aCmd) {
        final var payment = Payment.receivePayment(
                PaymentID.from(aCmd.paymentID()),
                aCmd.amount(),
                aCmd.createdAt(),
                aCmd.status()
        );

        final var updatedOrder = order.setPaymentId(PaymentID.from(aCmd.paymentID()));

        final var orderedItems = fetchOrderedItems(updatedOrder);
        final var items = mapToOrderPayedItems(orderedItems);

        updatedOrder.registerEvent(new OrderPayed(updatedOrder.getId().getValue(), items));
        orderGateway.update(updatedOrder);

        return ReceivePaymentOutput.from(paymentGateway.create(payment));
    }

    private List<OrderedItem> fetchOrderedItems(final Order updatedOrder) {
        var orderedItemIds = updatedOrder.getOrderedItems().stream()
                .map(OrderedItemID::getValue)
                .toList();
        return orderedItemGateway.findByIds(orderedItemIds);
    }

    private List<Item> mapToOrderPayedItems(final List<OrderedItem> orderedItems) {
        var productIds = orderedItems.stream()
                .map(item -> item.getProduct().getValue())
                .toList();

        var products = productGateway.findByIds(productIds);

        return products.stream()
                .map(product -> {
                    int quantity = orderedItems.stream()
                            .filter(item -> item.getProduct().equals(product.getId()))
                            .map(OrderedItem::getQuantity)
                            .findFirst()
                            .orElse(0);

                    return Item.with(product.getId().getValue(), product.getName(), quantity);
                })
                .toList();
    }

}
