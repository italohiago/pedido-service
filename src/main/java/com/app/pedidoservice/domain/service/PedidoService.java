package com.app.pedidoservice.domain.service;

import com.app.pedidoservice.application.dto.CriarPedidoRequestDTO;
import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.application.dto.PedidoDTO;
import com.app.pedidoservice.domain.exception.PedidoDuplicadoException;
import com.app.pedidoservice.domain.factory.PedidoFactory;
import com.app.pedidoservice.domain.mapper.PedidoMapper;
import com.app.pedidoservice.domain.model.PedidoEntity;
import com.app.pedidoservice.domain.repository.PedidoRepository;
import com.app.pedidoservice.domain.util.PedidoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoCacheService pedidoCacheService;
    private final PedidoValidacaoService pedidoValidacaoService;
    private final PedidoFactory pedidoFactory;
    private final PedidoEventPublisher pedidoEventPublisher;
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public CriarPedidoResponseDTO processarPedido(CriarPedidoRequestDTO pedidoRequest) {
        log.info("Iniciando processamento do pedido para o cliente: {}", pedidoRequest.getCliente().getNomeCliente());

        pedidoValidacaoService.validarPedido(pedidoRequest);

        String chavePedido = PedidoUtils.gerarChavePedido(pedidoRequest);
        log.debug("Chave gerada para o pedido: {}", chavePedido);

        if (pedidoCacheService.pedidoJaExiste(chavePedido)) {
            log.warn("Pedido duplicado detectado para o cliente: {} - Chave: {}",
                    pedidoRequest.getCliente().getNomeCliente(), chavePedido);
            throw new PedidoDuplicadoException("Pedido duplicado detectado. Aguarde alguns minutos antes de tentar novamente.");
        }

        try {
            BigDecimal valorTotal = PedidoUtils.calcularValorTotal(pedidoRequest.getItens());

            CriarPedidoResponseDTO pedidoResponse = pedidoFactory.criarPedidoResponse(pedidoRequest, chavePedido, valorTotal);

            //Armazenando o pedido no cache
            pedidoCacheService.armazenarPedidoNoCache(chavePedido);
            //Publicando o evento no tópico de eventos
            pedidoEventPublisher.publicarPedido(pedidoResponse);

            log.info("Pedido processado com sucesso para o cliente: {} - Chave: {}",
                    pedidoRequest.getCliente().getNomeCliente(), chavePedido);
            return pedidoResponse;

        } catch (Exception e) {
            log.error("Erro inesperado ao processar o pedido para o cliente {}: {}",
                    pedidoRequest.getCliente().getNomeCliente(), e.getMessage(), e);
            throw new RuntimeException("Erro ao processar o pedido.");
        }
    }

    public Page<PedidoDTO> listarTodosPedidos(Pageable pageable) {
        Page<PedidoEntity> pedidosPage = pedidoRepository.findAll(pageable);
        return pedidosPage.map(pedidoMapper::toPedidoDTO);
    }


    public Optional<PedidoDTO> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toPedidoDTO);
    }
}