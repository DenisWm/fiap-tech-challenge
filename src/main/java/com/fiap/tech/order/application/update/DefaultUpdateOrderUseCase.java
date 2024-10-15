package com.fiap.tech.order.application.update;

import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.handler.Notification;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.product.application.update.UpdateProductCommand;

import java.util.Objects;

import static com.fiap.tech.common.application.utils.IDNotFoundUtils.notFound;

public class DefaultUpdateOrderUseCase extends UpdateOrderUseCase {

    private final OrderGateway orderGateway;

    public DefaultUpdateOrderUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public UpdateOrderOutput execute(final UpdateOrderCommand command) {
        final var anId = OrderID.from(command.id());
        final var newStatus = OrderStatus.valueOf(command.status());

        final var anOrder = orderGateway.findById(anId)
                .orElseThrow(notFound(anId, Order.class));

        final var notification = Notification.create();
        notification.append(validateOrderStatus(anOrder, newStatus));
        notification.validate(() -> anOrder.update(newStatus));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not update Order", notification);
        }

        return UpdateOrderOutput.from(this.orderGateway.update(anOrder));
    }

    private Notification validateOrderStatus(final Order anOrder, final OrderStatus newStatus) {
        final var notification = Notification.create();

        if (OrderStatus.COMPLETED.equals(anOrder.getStatus())) {
            notification.append(new Error("Cannot update an order that is already completed"));
        }

        if (OrderStatus.RECEIVED.equals(newStatus)) {
            notification.append(new Error("Cannot update order status to 'received'"));
        }

        return notification;
    }
}
