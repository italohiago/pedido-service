package com.app.pedidoservice.domain.service;

import com.app.pedidoservice.application.dto.CriarPedidoRequestDTO;
import com.app.pedidoservice.domain.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class PedidoValidacaoService {

    public void validarPedido(CriarPedidoRequestDTO pedidoRequest) {
        if (Optional.ofNullable(pedidoRequest.getItens()).orElseThrow(() ->
                new BadRequestException("O pedido deve conter pelo menos um item.")).isEmpty()) {
            throw new BadRequestException("O pedido deve conter pelo menos um item.");
        }
        log.debug("Pedido validado com sucesso para o cliente: {}", pedidoRequest.getCliente().getNomeCliente());
    }
}