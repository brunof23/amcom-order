package com.ambev.project_order.infrastructure.adapter.messaging.kafka;

import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.infrastructure.adapter.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer implements MessageProducer {
    private final KafkaTemplate<String, PedidoResponse> kafkaTemplate;

    @Override
    public void sendMessage(PedidoResponse pedidoResponse) {
        try {
            kafkaTemplate.send("pedidos-processados", pedidoResponse);
            log.info("Pedido enviado para Kafka com sucesso: {}", pedidoResponse.getCodigoPedido());
        } catch (Exception e) {
            log.error("Erro ao enviar o pedido para o Kafka: {}", e.getMessage(), e);
        }
    }
}