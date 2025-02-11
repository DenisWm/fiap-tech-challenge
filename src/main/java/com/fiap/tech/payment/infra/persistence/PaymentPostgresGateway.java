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
        return save(payment);
    }

    private Payment save(Payment payment) {
        return paymentRepository
                .save(PaymentJpaEntity.from(payment))
                .toAggregate();
    }

    @Override
    public Payment update(Payment payment) {
        return save(payment);
    }

    @Override
    public Optional<Payment> findById(final PaymentID paymentID) {
        return paymentRepository.findById(paymentID.getValue())
                .map(PaymentJpaEntity::toAggregate);
    }
}
