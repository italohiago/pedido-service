package com.app.pedidoservice.domain.service;

import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.domain.mapper.PedidoMapper;
import com.app.pedidoservice.domain.model.PedidoEntity;
import com.app.pedidoservice.domain.model.StatusPedido;
import com.app.pedidoservice.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoConsumerService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final PedidoMapper pedidoMapper;

    public PedidoEntity processarNovoPedido(CriarPedidoResponseDTO pedidoDTO) {
        // Verificar se o pedido já existe
        Optional<PedidoEntity> pedidoExistente = pedidoRepository.findByChavePedido(pedidoDTO.getChavePedido());

        if (pedidoExistente.isPresent()) {
            log.warn("Pedido {} já existe no banco. Ignorando processamento...", pedidoDTO.getChavePedido());
            return pedidoExistente.get();
        }

        // Buscar ou criar o cliente
        var cliente = clienteService.buscarOuCriarCliente(pedidoDTO.getIdCliente(), pedidoDTO.getNomeCliente());

        // Converter DTO para entidade
        PedidoEntity pedido = pedidoMapper.toEntity(pedidoDTO, cliente);

        // Salvar como PROCESSANDO
        pedido.setStatus(StatusPedido.PROCESSANDO);
        pedido.setAtualizadoEm(LocalDateTime.now());
        pedidoRepository.save(pedido);
        log.info("Pedido {} salvo como PROCESSANDO.", pedido.getChavePedido());

        // Simular processamento
        processarPedido(pedido);

        // Atualizar para CONCLUÍDO
        pedido.setStatus(StatusPedido.CONCLUIDO);
        pedido.setAtualizadoEm(LocalDateTime.now());
        pedidoRepository.save(pedido);
        log.info("Pedido {} processado e salvo com status CONCLUÍDO!", pedido.getChavePedido());

        return pedido;
    }

    private void processarPedido(PedidoEntity pedido) {
        log.info("Processando lógica de negócio para o pedido {}", pedido.getChavePedido());
        try {
            Thread.sleep(5000); // Simula um delay no processamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
