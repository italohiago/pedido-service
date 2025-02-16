package com.app.pedidoservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criar um novo pedido")
public class CriarPedidoRequestDTO {

    @Schema(description = "O cliente que está realizando o pedido")
    @NotNull(message = "O cliente é obrigatório.")
    private ClientePedidoDTO cliente;

    @Schema(description = "Lista de itens do pedido")
    @NotEmpty(message = "A lista de itens não pode estar vazia.")
    private List<ItemPedidoDTO> itens;
}
