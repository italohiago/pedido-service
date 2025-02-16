package com.app.pedidoservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um cliente dentro do pedido")
public class ClientePedidoDTO {
    @Schema(description = "Id do cliente que está realizando o pedido", example = "90002")
    @NotEmpty(message = "O id do cliente é obrigatório.")
    private String idCliente;

    @Schema(description = "Nome do cliente que está realizando o pedido", example = "Italo Lopes")
    @NotEmpty(message = "O nome do cliente é obrigatório.")
    private String nomeCliente;
}
