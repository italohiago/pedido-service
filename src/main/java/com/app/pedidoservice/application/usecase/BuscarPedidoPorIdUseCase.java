package com.app.pedidoservice.application.usecase;

import com.app.pedidoservice.application.dto.PedidoDTO;
import com.app.pedidoservice.domain.exception.PedidoNaoEncontradoException;
import com.app.pedidoservice.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarPedidoPorIdUseCase {

    private final PedidoService pedidoService;

    public PedidoDTO executar(Long id) {
        return pedidoService.buscarPedidoPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado para o ID: " + id));
    }
}
