package com.ambev.project_order.infrastructure.adapter.controller;

import com.ambev.project_order.application.service.PedidoService;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody PedidoRequest request) {
        log.info("Recebida requisição para criar um pedido: {}", request.getCodigoPedido());
        PedidoResponse response = pedidoService.processarPedido(request);
        log.info("Pedido processado com sucesso: {}", response.getCodigoPedido());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        log.info("Recebida requisição para listar todos os pedidos");

        List<PedidoResponse> pedidos = pedidoService.listarTodos();

        log.info("Total de pedidos encontrados: {}", pedidos.size());
        return ResponseEntity.ok(pedidos);
    }
}
