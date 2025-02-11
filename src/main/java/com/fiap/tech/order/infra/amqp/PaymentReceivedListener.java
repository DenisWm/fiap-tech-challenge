package com.fiap.tech.order.infra.amqp;


import com.fiap.tech.common.infra.configuration.Json;
import com.fiap.tech.order.application.update.UpdateOrderCommand;
import com.fiap.tech.order.infra.models.PaymentReceivedResult;
import com.fiap.tech.order.infra.models.ProductionStatusChangedResult;
import com.fiap.tech.payment.application.receive.ReceivePaymentCommand;
import com.fiap.tech.payment.application.receive.ReceivePaymentUseCase;
import com.fiap.tech.payment.domain.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PaymentReceivedListener {

    private final static Logger log = LoggerFactory.getLogger(PaymentReceivedListener.class);

    public static final String LISTENER_ID = "paymentReceiveListener";

    private final ReceivePaymentUseCase receivePaymentUseCase;

    public PaymentReceivedListener(final ReceivePaymentUseCase receivePaymentUseCase) {
        this.receivePaymentUseCase = Objects.requireNonNull(receivePaymentUseCase);
    }

    @RabbitListener(id = LISTENER_ID, queues = "payment.response.queue")
    public void onProductionStatusChangedMessage(@Payload final String message) {
        try {
            final var aResult = Json.readValue(message, PaymentReceivedResult.class);

            log.info("[message:payment.received.income] [status:{}] [payload:{}]", aResult.paymentStatus(), message);

            final var aCmd = ReceivePaymentCommand.with(
                    aResult.paymentID(),
                    aResult.orderID(),
                    aResult.clientID(),
                    aResult.amount(),
                    aResult.createdAt(),
                    PaymentStatus.valueOf(aResult.paymentStatus())
            );

            this.receivePaymentUseCase.execute(aCmd);

        } catch(Exception e) {
            log.error("[message:payment.received.income] [status:unknown] [payload:{}] [message:{}]", message, e.getMessage());
        }
    }
}
