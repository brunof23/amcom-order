package com.ambev.project_order.infrastructure.adapter.messaging;

import com.ambev.project_order.dto.response.PedidoResponse;

public interface MessageProducer {
    void sendMessage(PedidoResponse pedidoResponse);
}