package com.ambev.project_order.infrastructure.adapter.controller.menssaging.kafka;

import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.infrastructure.adapter.messaging.kafka.KafkaMessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaMessageProducerTest {

    @Mock
    private KafkaTemplate<String, PedidoResponse> kafkaTemplate;

    @InjectMocks
    private KafkaMessageProducer kafkaMessageProducer;
    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(500))
                .status("PROCESSADO")
                .build();
    }

    @Test
    void sendMessage() {
        PedidoResponse pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .build();

        kafkaMessageProducer.sendMessage(pedidoResponse);

        verify(kafkaTemplate, times(1)).send("pedidos-processados", pedidoResponse);
    }
    @Test
    void testSendMessageException() {
        doThrow(new RuntimeException("Erro ao enviar mensagem para o Kafka"))
                .when(kafkaTemplate).send("pedidos-processados", pedidoResponse);

        kafkaMessageProducer.sendMessage(pedidoResponse);

        verify(kafkaTemplate, times(1)).send("pedidos-processados", pedidoResponse);
        verifyNoMoreInteractions(kafkaTemplate);
    }
}
