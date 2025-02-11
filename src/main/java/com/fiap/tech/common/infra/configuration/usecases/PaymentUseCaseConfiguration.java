package com.fiap.tech.common.infra.configuration.usecases;

import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.payment.application.receive.DefaultReceivePaymentUseCase;
import com.fiap.tech.payment.application.receive.ReceivePaymentUseCase;
import com.fiap.tech.payment.domain.PaymentGateway;
import com.fiap.tech.product.domain.ProductGateway;
import com.google.common.annotations.Beta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PaymentUseCaseConfiguration {

    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final OrderedItemGateway orderedItemGateway;
    private final PaymentGateway paymentGateway;

    public PaymentUseCaseConfiguration(
            final OrderGateway orderGateway,
            final ProductGateway productGateway,
            final OrderedItemGateway orderedItemGateway,
            final PaymentGateway paymentGateway
    ) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
        this.productGateway = Objects.requireNonNull(productGateway);
        this.orderedItemGateway = Objects.requireNonNull(orderedItemGateway);
        this.paymentGateway = Objects.requireNonNull(paymentGateway);
    }

    @Bean
    public ReceivePaymentUseCase receivePaymentUseCase() {
        return new DefaultReceivePaymentUseCase(
                paymentGateway,
                orderGateway,
                orderedItemGateway,
                productGateway);
    }
}
