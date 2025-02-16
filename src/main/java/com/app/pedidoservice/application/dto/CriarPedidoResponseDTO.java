package com.app.pedidoservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de resposta ao criar um pedido")
public class CriarPedidoResponseDTO {

    @Schema(description = "ID do pedido gerado", example = "12345")
    private Long pedidoId;

    @Schema(description = "Hashcode do pedido gerado", example = "12345")
    private String chavePedido;

    @Schema(description = "Nome do cliente", example = "Italo Lopes")
    private String nomeCliente;

    @Schema(description = "Id do cliente", example = "90002")
    private String idCliente;

    @Schema(description = "Lista de itens que compõem o pedido")
    private List<ItemPedidoDTO> itens;

    @Schema(description = "Valor total do pedido", example = "149.90")
    private BigDecimal valorTotal;

    @Schema(description = "Data e hora da criação do pedido", example = "2025-02-14T10:30:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Status atual do pedido", example = "PENDENTE")
    private String status;

    public CriarPedidoResponseDTO(String chavePedido, String nomeCliente, String idCliente,
                                  List<ItemPedidoDTO> itens, BigDecimal valorTotal, LocalDateTime localDateTime, String name) {
        this.chavePedido = chavePedido;
        this.nomeCliente = nomeCliente;
        this.idCliente = idCliente;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.dataCriacao = localDateTime;
        this.status = name;
    }
}
