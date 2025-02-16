package com.app.pedidoservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um item dentro do pedido")
public class ItemPedidoDTO {

    @Schema(description = "Identificador do item", example = "1")
    @NotNull(message = "O identificador do item é obrigatório.")
    private Long id;

    @Schema(description = "Nome do produto", example = "Notebook Dell Inspiron 15")
    @NotEmpty(message = "O nome do item é obrigatório.")
    private String nomeProduto;

    @Schema(description = "Preço unitário do produto", example = "3500.00")
    @NotNull(message = "O preço do item é obrigatório.")
    private BigDecimal precoUnitario;

    @Schema(description = "Quantidade do produto", example = "2")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
    private int quantidade;
}

