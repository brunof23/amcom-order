package com.ambev.project_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {
    private String codigoProduto;
    private String nomeProduto;
    private BigDecimal valorUnitario;
    private int quantidade;
    private BigDecimal valorTotal;
}
