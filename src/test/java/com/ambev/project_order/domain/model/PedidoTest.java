package com.ambev.project_order.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        produto1 = Produto.builder()
                .codigoProduto("P001")
                .nomeProduto("Produto A")
                .valorUnitario(BigDecimal.valueOf(100.0))
                .quantidade(2)
                .build();
        produto1.calcularValorTotal();

        produto2 = Produto.builder()
                .codigoProduto("P002")
                .nomeProduto("Produto B")
                .valorUnitario(BigDecimal.valueOf(200.0))
                .quantidade(1)
                .build();
        produto2.calcularValorTotal();

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);

        pedido = Pedido.builder()
                .codigoPedido("12345")
                .status("PROCESSADO")
                .dataRecebimento(LocalDateTime.now())
                .produtos(produtos)
                .build();
    }

    @Test
    void deveCalcularValorTotalCorretamente() {
        pedido.calcularValorTotal();

        assertEquals(BigDecimal.valueOf(400.0), pedido.getValorTotal());
    }

    @Test
    void deveRetornarZeroSeListaDeProdutosForVazia() {
        pedido.setProdutos(new ArrayList<>());
        pedido.calcularValorTotal();

        assertEquals(BigDecimal.ZERO, pedido.getValorTotal());
    }

    @Test
    void deveRetornarZeroSeListaDeProdutosForNula() {
        pedido.setProdutos(null);
        pedido.calcularValorTotal();

        assertEquals(BigDecimal.ZERO, pedido.getValorTotal());
    }
}
