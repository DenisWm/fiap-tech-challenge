package com.fiap.tech.payment.webhook;

//Classe temporaria, aguardando controller paymentID para reorganizar

public class PaymentNotification {

    private String paymentId;
    private String status;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

