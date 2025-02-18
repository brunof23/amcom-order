package com.ambev.project_order.infrastructure.adapter.controller.menssaging.kafka;

import com.ambev.project_order.application.service.PedidoService;
import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.infrastructure.adapter.messaging.kafka.KafkaMessageConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaMessageConsumerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private KafkaMessageConsumer kafkaMessageConsumer;

    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(200.0))
                .status("PROCESSADO")
                .build();
    }

    @Test
    void testReceiveMessage() {
        kafkaMessageConsumer.receiveMessage(pedidoResponse);

        verify(pedidoService, times(1)).atualizarStatusPedido("12345", "ENTREGUE");
    }

    @Test
    void testReceiveMessageWithException() {
        doThrow(new RuntimeException("Erro ao atualizar")).when(pedidoService).atualizarStatusPedido(anyString(), anyString());

        kafkaMessageConsumer.receiveMessage(pedidoResponse);

        verify(pedidoService, times(1)).atualizarStatusPedido("12345", "ENTREGUE");
    }
}
