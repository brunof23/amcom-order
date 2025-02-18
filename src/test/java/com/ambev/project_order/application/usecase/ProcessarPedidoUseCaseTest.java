package com.ambev.project_order.application.usecase;

import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.repository.PedidoRepository;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.infrastructure.adapter.messaging.MessageProducer;
import com.ambev.project_order.infrastructure.mapper.PedidoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessarPedidoUseCaseTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private ProcessarPedidoUseCase processarPedidoUseCase;

    private PedidoRequest pedidoRequest;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedidoRequest = new PedidoRequest();
        pedidoRequest.setCodigoPedido("12345");

        pedido = new Pedido();
        pedido.setCodigoPedido("12345");
        pedido.setValorTotal(BigDecimal.valueOf(200.0));
    }

    @Test
    void deveProcessarPedidoComSucesso() {
        when(pedidoRepository.findByCodigoPedido("12345")).thenReturn(Optional.empty());

        when(pedidoMapper.toPedido(pedidoRequest)).thenReturn(pedido);

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        PedidoResponse pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(200.0))
                .status("PROCESSADO")
                .build();
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(pedidoResponse);

        PedidoResponse response = processarPedidoUseCase.executar(pedidoRequest);

        assertNotNull(response, "A resposta do pedido não deveria ser nula");
        assertEquals("12345", response.getCodigoPedido());
        assertEquals(BigDecimal.valueOf(200.0), response.getValorTotal());
        assertEquals("PROCESSADO", response.getStatus());

        verify(pedidoRepository, times(1)).save(pedido);

        verify(messageProducer, times(1)).sendMessage(response);
    }



    @Test
    void deveLancarExcecaoQuandoPedidoJaProcessado() {
        when(pedidoRepository.findByCodigoPedido("12345")).thenReturn(Optional.of(pedido));

        assertThrows(IllegalArgumentException.class, () -> processarPedidoUseCase.executar(pedidoRequest));
        verify(messageProducer, never()).sendMessage(any());
    }
}
