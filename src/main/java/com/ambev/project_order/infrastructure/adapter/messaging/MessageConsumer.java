package com.ambev.project_order.infrastructure.adapter.messaging;

import com.ambev.project_order.dto.response.PedidoResponse;

public interface MessageConsumer {
    void receiveMessage(PedidoResponse pedidoResponse);
}