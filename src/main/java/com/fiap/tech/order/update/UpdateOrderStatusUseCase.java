package com.fiap.tech.order.update;

import com.fiap.tech.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStatusUseCase {
    private static final Logger log = LoggerFactory.getLogger(UpdateOrderStatusUseCase.class);

    public void execute(com.fiap.tech.order.application.UpdateOrderStatusCommand command) {
        log.info("Atualizando pedido {} para status {}", command.orderId(), command.status());
        log.info("Pedido {} atualizado com sucesso para status {}", command.orderId(), command.status());
    }
}
