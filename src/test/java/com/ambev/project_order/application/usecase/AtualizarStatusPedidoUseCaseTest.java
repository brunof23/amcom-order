package com.ambev.project_order.application.usecase;

import com.ambev.project_order.domain.exception.PedidoNotFoundException;
import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusPedidoUseCaseTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .codigoPedido("12345")
                .status("PROCESSADO")
                .valorTotal(BigDecimal.valueOf(200.0))
                .dataRecebimento(LocalDateTime.now())
                .build();
    }

    @Test
    void deveAtualizarStatusPedidoComSucesso() {
        when(pedidoRepository.findByCodigoPedido("12345")).thenReturn(Optional.of(pedido));

        atualizarStatusPedidoUseCase.executar("12345", "ENTREGUE");

        assertEquals("ENTREGUE", pedido.getStatus());

        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoForEncontrado() {
        when(pedidoRepository.findByCodigoPedido("99999")).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () ->
                atualizarStatusPedidoUseCase.executar("99999", "CANCELADO"));

        verify(pedidoRepository, never()).save(any());
    }
}
