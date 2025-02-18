package com.ambev.project_order.infrastructure.mapper;

import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.model.Produto;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.request.ProdutoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.dto.response.ProdutoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoMapperTest {

    private PedidoMapper pedidoMapper;
    private PedidoRequest pedidoRequest;
    private ProdutoRequest produtoRequest;

    @BeforeEach
    void setUp() {
        pedidoMapper = new PedidoMapper();

        produtoRequest = ProdutoRequest.builder()
                .codigoProduto("P001")
                .nomeProduto("Produto A")
                .valorUnitario(BigDecimal.valueOf(100))
                .quantidade(2)
                .build();

        pedidoRequest = PedidoRequest.builder()
                .codigoPedido("12345")
                .produtos(List.of(produtoRequest))
                .build();
    }

    @Test
    void testToPedido() {
        Pedido pedido = pedidoMapper.toPedido(pedidoRequest);

        assertNotNull(pedido);
        assertEquals("12345", pedido.getCodigoPedido());
        assertEquals(1, pedido.getProdutos().size());
        assertEquals("PROCESSADO", pedido.getStatus());
        assertNotNull(pedido.getDataRecebimento());

        BigDecimal expectedTotal = BigDecimal.valueOf(200); // 100 * 2
        assertEquals(expectedTotal, pedido.getValorTotal());

        Produto produto = pedido.getProdutos().get(0);
        assertEquals("P001", produto.getCodigoProduto());
        assertEquals("Produto A", produto.getNomeProduto());
        assertEquals(BigDecimal.valueOf(100), produto.getValorUnitario());
        assertEquals(2, produto.getQuantidade());
        assertEquals(expectedTotal, produto.getValorTotal());
    }

    @Test
    void testToProduto() {
        Produto produto = pedidoMapper.toProduto(produtoRequest);

        assertNotNull(produto);
        assertEquals("P001", produto.getCodigoProduto());
        assertEquals("Produto A", produto.getNomeProduto());
        assertEquals(BigDecimal.valueOf(100), produto.getValorUnitario());
        assertEquals(2, produto.getQuantidade());

        BigDecimal expectedTotal = BigDecimal.valueOf(200); // 100 * 2
        assertEquals(expectedTotal, produto.getValorTotal());
    }

    @Test
    void testToPedidoResponse() {
        Pedido pedido = pedidoMapper.toPedido(pedidoRequest);
        PedidoResponse pedidoResponse = pedidoMapper.toPedidoResponse(pedido);

        assertNotNull(pedidoResponse);
        assertEquals("12345", pedidoResponse.getCodigoPedido());
        assertEquals(BigDecimal.valueOf(200), pedidoResponse.getValorTotal());
        assertEquals("PROCESSADO", pedidoResponse.getStatus());
        assertNotNull(pedidoResponse.getDataRecebimento());
        assertEquals(1, pedidoResponse.getProdutos().size());

        ProdutoResponse produtoResponse = pedidoResponse.getProdutos().get(0);
        assertEquals("P001", produtoResponse.getCodigoProduto());
        assertEquals("Produto A", produtoResponse.getNomeProduto());
        assertEquals(BigDecimal.valueOf(100), produtoResponse.getValorUnitario());
        assertEquals(2, produtoResponse.getQuantidade());
        assertEquals(BigDecimal.valueOf(200), produtoResponse.getValorTotal());
    }

    @Test
    void testToProdutoResponse() {
        Produto produto = pedidoMapper.toProduto(produtoRequest);
        ProdutoResponse produtoResponse = pedidoMapper.toProdutoResponse(produto);

        assertNotNull(produtoResponse);
        assertEquals("P001", produtoResponse.getCodigoProduto());
        assertEquals("Produto A", produtoResponse.getNomeProduto());
        assertEquals(BigDecimal.valueOf(100), produtoResponse.getValorUnitario());
        assertEquals(2, produtoResponse.getQuantidade());
        assertEquals(BigDecimal.valueOf(200), produtoResponse.getValorTotal());
    }
}
