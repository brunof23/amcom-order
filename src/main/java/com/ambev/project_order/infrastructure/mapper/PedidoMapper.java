package com.ambev.project_order.infrastructure.mapper;

import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.model.Produto;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.request.ProdutoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.dto.response.ProdutoResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido toPedido(PedidoRequest request) {
        List<Produto> produtos = request.getProdutos().stream()
                .map(this::toProduto)
                .collect(Collectors.toList());

        Pedido pedido = Pedido.builder()
                .codigoPedido(request.getCodigoPedido())
                .status("PROCESSADO")
                .dataRecebimento(LocalDateTime.now())
                .produtos(produtos)
                .build();

        pedido.calcularValorTotal();

        produtos.forEach(produto -> produto.setPedido(pedido));
        return pedido;
    }

    public Produto toProduto(ProdutoRequest request) {
        Produto produto = Produto.builder()
                .codigoProduto(request.getCodigoProduto())
                .nomeProduto(request.getNomeProduto())
                .valorUnitario(request.getValorUnitario())
                .quantidade(request.getQuantidade())
                .build();

        produto.calcularValorTotal();
        return produto;
    }

    public PedidoResponse toPedidoResponse(Pedido pedido) {
        List<ProdutoResponse> produtosResponse = pedido.getProdutos().stream()
                .map(this::toProdutoResponse)
                .collect(Collectors.toList());

        return PedidoResponse.builder()
                .codigoPedido(pedido.getCodigoPedido())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus())
                .dataRecebimento(pedido.getDataRecebimento())
                .produtos(produtosResponse)
                .build();
    }

    public ProdutoResponse toProdutoResponse(Produto produto) {
        return ProdutoResponse.builder()
                .codigoProduto(produto.getCodigoProduto())
                .nomeProduto(produto.getNomeProduto())
                .valorUnitario(produto.getValorUnitario())
                .quantidade(produto.getQuantidade())
                .valorTotal(produto.getValorTotal())
                .build();
    }
}