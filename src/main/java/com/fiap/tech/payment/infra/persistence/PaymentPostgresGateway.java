package com.fiap.tech.payment.infra.persistence;


import com.fiap.tech.common.infra.utils.SpecificationUtils;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentGateway;
import com.fiap.tech.payment.domain.PaymentID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;

@Component
public class PaymentPostgresGateway implements PaymentGateway {

    private final PaymentRepository paymentRepository;
    public PaymentPostgresGateway(final PaymentRepository paymentRepository) {
        this.paymentRepository = Objects.requireNonNull(paymentRepository);
    }

    @Override
    public Payment create(final Payment payment) {
        return paymentRepository
                .save(PaymentJpaEntity.from(payment))
                .toAggregate();
    }


    @Override
    public Optional<Payment> findById(PaymentID anId) {
        return this.paymentRepository.findById(anId.getValue()).map(PaymentJpaEntity::toAggregate);
    }

    @Override
    public Optional<Order> findOrderById(OrderID orderId) {
        return Optional.empty();
    }

    private Specification<PaymentJpaEntity> assembleSpecification(final String payment) {
        return SpecificationUtils.equal("paymentId", payment);
    }
}
