package com.fiap.tech.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.order.infra.models.OrderEncoderCompleted;
import com.fiap.tech.order.infra.models.OrderEncoderErro;
import com.fiap.tech.order.infra.models.OrderEncoderResult;
import com.fiap.tech.order.update.UpdateOrderStatusUseCase;
import com.fiap.tech.order.application.UpdateOrderStatusCommand;
import com.fiap.tech.order.update.UpdateOrderStatusUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderEncodedListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEncodedListener.class);
    private static final String LISTENER_ID = "orderEncodedListener";
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final ObjectMapper objectMapper;

    public OrderEncodedListener(final UpdateOrderStatusUseCase updateOrderStatusUseCase, final ObjectMapper objectMapper) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.order-encoded.queue}")
    public void onOrderEncodedMessage(@Payload final String message) {
        log.info("[message:order.listener.income] [status:received] [payload:{}]", message);

        try {
            final var aResult = objectMapper.readValue(message, OrderEncoderResult.class);

            if (aResult instanceof OrderEncoderCompleted dto) {
                final var aCmd = new UpdateOrderStatusCommand(
                        OrderStatus.COMPLETED,
                        dto.id(),
                        dto.order().resourceId(),
                        dto.order().encodedOrderFolder(),
                        dto.order().filePath());

                this.updateOrderStatusUseCase.execute(aCmd);
            } else if (aResult instanceof OrderEncoderErro dto) {
                log.error("[message:order.listener.income] [status:error] [payload:{}]", message);
            } else {
                log.error("[message:order.listener.income] [status:unknown] [payload:{}]", message);
            }
        } catch (Exception e) {
            log.error("[message:order.listener.income] [status:parsing_error] [payload:{}]", message, e);
        }
    }
}
