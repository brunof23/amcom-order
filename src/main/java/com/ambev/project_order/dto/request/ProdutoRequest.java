package com.ambev.project_order.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoRequest {
    private String codigoProduto;
    private String nomeProduto;
    private BigDecimal valorUnitario;
    private int quantidade;
}