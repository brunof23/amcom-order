package com.ambev.project_order.infrastructure.adapter.controller;

import com.ambev.project_order.application.service.PedidoService;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        pedidoRequest = PedidoRequest.builder()
                .codigoPedido("12345")
                .produtos(List.of())
                .build();

        pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(500))
                .status("PROCESSADO")
                .build();
    }
    @Test
    void criarPedido() {
        PedidoRequest request = PedidoRequest.builder().codigoPedido("12345").build();
        PedidoResponse response = PedidoResponse.builder().codigoPedido("12345").build();

        when(pedidoService.processarPedido(request)).thenReturn(response);

        ResponseEntity<PedidoResponse> result = pedidoController.criarPedido(request);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("12345", result.getBody().getCodigoPedido());
    }

    @Test
    void listarPedidos() {
        List<PedidoResponse> pedidos = List.of(PedidoResponse.builder().codigoPedido("12345").build());

        when(pedidoService.listarTodos()).thenReturn(pedidos);

        ResponseEntity<List<PedidoResponse>> result = pedidoController.listarPedidos();

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().size());
        assertEquals("12345", result.getBody().get(0).getCodigoPedido());
    }
}
