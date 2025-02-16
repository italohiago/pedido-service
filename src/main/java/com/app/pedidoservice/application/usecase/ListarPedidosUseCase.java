package com.app.pedidoservice.application.usecase;

import com.app.pedidoservice.application.dto.PedidoDTO;
import com.app.pedidoservice.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarPedidosUseCase {

    private final PedidoService pedidoService;

    public Page<PedidoDTO> executar(Pageable pageable) {
        return pedidoService.listarTodosPedidos(pageable);
    }
}
