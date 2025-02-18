package com.ambev.project_order.application.usecase;

import com.ambev.project_order.domain.exception.PedidoNotFoundException;
import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtualizarStatusPedidoUseCase {
    private final PedidoRepository pedidoRepository;

    @Transactional
    public void executar(String codigoPedido, String novoStatus) {
        log.info("Atualizando status do pedido: {} para {}", codigoPedido, novoStatus);

        Pedido pedido = pedidoRepository.findByCodigoPedido(codigoPedido)
                .orElseThrow(() -> {
                    log.warn("Pedido não encontrado: {}", codigoPedido);
                    return new PedidoNotFoundException("Pedido não encontrado: " + codigoPedido);
                });

        pedido.setStatus(novoStatus);
        pedidoRepository.save(pedido);
        log.info("Status do pedido atualizado com sucesso: {} para {}", codigoPedido, novoStatus);
    }
}