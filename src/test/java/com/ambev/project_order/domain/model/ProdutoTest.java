package com.ambev.project_order.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = Produto.builder()
                .codigoProduto("P001")
                .nomeProduto("Produto A")
                .valorUnitario(BigDecimal.valueOf(100.0))
                .quantidade(2)
                .build();
    }

    @Test
    void deveCalcularValorTotalCorretamente() {
        produto.calcularValorTotal();

        assertEquals(BigDecimal.valueOf(200.0), produto.getValorTotal());
    }

    @Test
    void deveRetornarZeroSeQuantidadeForZero() {
        produto.setQuantidade(0);
        produto.calcularValorTotal();

        assertEquals(BigDecimal.ZERO, produto.getValorTotal());
    }

    @Test
    void deveRetornarZeroSeValorUnitarioForNulo() {
        produto.setValorUnitario(null);
        produto.calcularValorTotal();

        assertEquals(BigDecimal.ZERO, produto.getValorTotal());
    }
}
