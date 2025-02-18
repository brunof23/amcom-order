package com.ambev.project_order.application.usecase;

import com.ambev.project_order.domain.model.Pedido;
import com.ambev.project_order.domain.repository.PedidoRepository;
import com.ambev.project_order.dto.request.PedidoRequest;
import com.ambev.project_order.dto.response.PedidoResponse;
import com.ambev.project_order.infrastructure.mapper.PedidoMapper;
import com.ambev.project_order.infrastructure.adapter.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessarPedidoUseCase {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final MessageProducer messageProducer;

    @Transactional
    public PedidoResponse executar(PedidoRequest request) {
        log.info("Processando pedido com código: {}", request.getCodigoPedido());

        if (pedidoRepository.findByCodigoPedido(request.getCodigoPedido()).isPresent()) {
            log.warn("Pedido já processado anteriormente: {}", request.getCodigoPedido());
            throw new IllegalArgumentException("Pedido já processado: " + request.getCodigoPedido());
        }

        Pedido pedido = pedidoMapper.toPedido(request);
        log.debug("Pedido mapeado: {}", pedido);

        pedidoRepository.save(pedido);
        log.info("Pedido salvo no banco com sucesso: {}", pedido.getCodigoPedido());

        PedidoResponse response = pedidoMapper.toPedidoResponse(pedido);

        try {
            messageProducer.sendMessage(response);
            log.info("Mensagem enviada para o Kafka para o pedido: {}", pedido.getCodigoPedido());
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem para Kafka: {}", e.getMessage(), e);
        }

        return response;
    }
}