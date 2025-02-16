package com.app.pedidoservice.domain.factory;

import com.app.pedidoservice.application.dto.CriarPedidoRequestDTO;
import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.domain.model.StatusPedido;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PedidoFactory {

    public CriarPedidoResponseDTO criarPedidoResponse(CriarPedidoRequestDTO pedidoRequest, String chavePedido, BigDecimal valorTotal) {
        return new CriarPedidoResponseDTO(
                chavePedido,
                pedidoRequest.getCliente().getNomeCliente(),
                pedidoRequest.getCliente().getIdCliente(),
                pedidoRequest.getItens(),
                valorTotal,
                LocalDateTime.now(),
                StatusPedido.PENDENTE.name()
        );
    }
}