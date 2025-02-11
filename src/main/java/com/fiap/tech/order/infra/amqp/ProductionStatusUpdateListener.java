package com.fiap.tech.order.infra.amqp;


import com.fiap.tech.common.infra.configuration.Json;
import com.fiap.tech.order.application.update.UpdateOrderCommand;
import com.fiap.tech.order.application.update.UpdateOrderUseCase;
import com.fiap.tech.order.infra.models.ProductionStatusChangedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductionStatusUpdateListener {

    private final static Logger log = LoggerFactory.getLogger(ProductionStatusUpdateListener.class);

    public static final String LISTENER_ID = "productionStatusChangedListener";

    private final UpdateOrderUseCase updateOrderStatusUseCase;

    public ProductionStatusUpdateListener(final UpdateOrderUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = Objects.requireNonNull(updateOrderStatusUseCase);
    }

    @RabbitListener(id = LISTENER_ID, queues = "production.status-changed.queue")
    public void onProductionStatusChangedMessage(@Payload final String message) {
        try {
            final var aResult = Json.readValue(message, ProductionStatusChangedResult.class);

            log.info("[message:production.status.changed.income] [status:{}] [payload:{}]", aResult.status(), message);

            final var aCmd = UpdateOrderCommand.with(
                    aResult.orderId(),
                    aResult.status()
            );

            this.updateOrderStatusUseCase.execute(aCmd);

        } catch(Exception e) {
            log.error("[message:production.status.changed.income] [status:unknown] [payload:{}] [message:{}]", message, e.getMessage());
        }
    }
}
