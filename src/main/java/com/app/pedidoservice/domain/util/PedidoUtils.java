package com.app.pedidoservice.domain.util;

import com.app.pedidoservice.application.dto.CriarPedidoRequestDTO;
import com.app.pedidoservice.application.dto.ItemPedidoDTO;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class PedidoUtils {
    public static BigDecimal calcularValorTotal(List<ItemPedidoDTO> itens) {
        return itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static String gerarChavePedido(CriarPedidoRequestDTO pedidoRequest) {
        return "pedido:" + pedidoRequest.getCliente().hashCode() + ":" + pedidoRequest.getItens().hashCode();
    }
}