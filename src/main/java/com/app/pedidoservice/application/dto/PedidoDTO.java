package com.app.pedidoservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para representar um pedido")
public class PedidoDTO {

    @Schema(description = "ID do pedido", example = "12345")
    private Long id;

    @Schema(description = "Chave única do pedido", example = "abc123xyz")
    private String chavePedido;

    @Schema(description = "Status do pedido", example = "PENDENTE")
    private String status;

    @Schema(description = "Valor total do pedido", example = "149.90")
    private BigDecimal valorTotal;

    @Schema(description = "Data e hora da criação do pedido", example = "2025-02-14T10:30:00")
    private LocalDateTime criadoEm;

    @Schema(description = "Data e hora da última atualização do pedido", example = "2025-02-14T12:45:00")
    private LocalDateTime atualizadoEm;

    @Schema(description = "Dados do cliente que realizou o pedido")
    private ClientePedidoDTO cliente;

    @Schema(description = "Lista de itens que compõem o pedido")
    private List<ItemPedidoDTO> itens;
}