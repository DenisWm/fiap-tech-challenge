package com.fiap.tech.order.application;

import com.fiap.tech.order.domain.OrderStatus;

public record UpdateOrderStatusCommand(
        OrderStatus status,
        String orderId,
        String resourceId,
        String encodedOrderFolder,
        String filePath) {
}
