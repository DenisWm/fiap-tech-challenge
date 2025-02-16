package com.fiap.tech.payment.domain;

import java.util.Optional;

public interface PaymentGateway {

    Payment create(Payment payment);

    Payment update(Payment payment);

    Optional<Payment> findById(PaymentID paymentID);

}
