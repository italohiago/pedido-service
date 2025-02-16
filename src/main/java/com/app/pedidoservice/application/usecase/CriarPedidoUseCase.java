package com.app.pedidoservice.application.usecase;

import com.app.pedidoservice.application.dto.CriarPedidoRequestDTO;
import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.domain.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class CriarPedidoUseCase {

    private final PedidoService pedidoService;

    public CriarPedidoUseCase(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public CriarPedidoResponseDTO executar(CriarPedidoRequestDTO pedidoRequest) {
        return pedidoService.processarPedido(pedidoRequest);
    }
}
