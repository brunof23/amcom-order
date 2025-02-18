package com.ambev.project_order.application.service;

import com.ambev.project_order.application.usecase.AtualizarStatusPedidoUseCase;
import com.ambev.project_order.application.usecase.ListarTodosPedidosUseCase;
import com.ambev.project_order.application.usecase.ProcessarPedidoUseCase;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private ProcessarPedidoUseCase processarPedidoUseCase;

    @Mock
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Mock
    private ListarTodosPedidosUseCase listarTodosPedidosUseCase;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        pedidoRequest = PedidoRequest.builder()
                .codigoPedido("12345")
                .build();

        pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .status("PROCESSADO")
                .valorTotal(BigDecimal.valueOf(200.0))
                .build();
    }

    @Test
    void deveProcessarPedidoComSucesso() {
        when(processarPedidoUseCase.executar(pedidoRequest)).thenReturn(pedidoResponse);

        PedidoResponse response = pedidoService.processarPedido(pedidoRequest);

        assertNotNull(response);
        assertEquals("12345", response.getCodigoPedido());
        assertEquals("PROCESSADO", response.getStatus());
        verify(processarPedidoUseCase, times(1)).executar(pedidoRequest);
    }

    @Test
    void deveAtualizarStatusPedidoComSucesso() {
        pedidoService.atualizarStatusPedido("12345", "ENTREGUE");

        verify(atualizarStatusPedidoUseCase, times(1)).executar("12345", "ENTREGUE");
    }

    @Test
    void deveListarTodosOsPedidosComSucesso() {
        when(listarTodosPedidosUseCase.executar()).thenReturn(List.of(pedidoResponse));

        List<PedidoResponse> pedidos = pedidoService.listarTodos();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals("12345", pedidos.get(0).getCodigoPedido());
        verify(listarTodosPedidosUseCase, times(1)).executar();
    }
}
