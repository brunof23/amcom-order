package com.ambev.project_order.application.usecase;

import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.repository.PedidoRepository;
import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.infrastructure.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarTodosPedidosUseCase {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public List<PedidoResponse> executar() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedidoMapper::toPedidoResponse)
                .collect(Collectors.toList());
    }
}