package com.app.pedidoservice.domain.consumer;

import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.domain.service.PedidoConsumerService;
import com.app.pedidoservice.domain.service.PedidoEventPublisher;
import com.app.pedidoservice.domain.mapper.PedidoMapper;
import com.app.pedidoservice.domain.model.PedidoEntity;
import com.app.pedidoservice.domain.model.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoEventConsumer {

    private final PedidoConsumerService pedidoConsumerService;
    private final PedidoEventPublisher pedidoEventPublisher;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(topics = "pedidos-novos", groupId = "pedido-group")
    public void consumirPedido(CriarPedidoResponseDTO pedidoDTO) {
        try {
            log.info("Recebendo pedido: {}", pedidoDTO.getChavePedido());

            // Processar o pedido e obter a entidade atualizada
            PedidoEntity pedidoProcessado = pedidoConsumerService.processarNovoPedido(pedidoDTO);

            // Se o pedido foi concluído, enviá-lo para o Kafka no tópico de pedidos concluídos
            if (pedidoProcessado.getStatus() == StatusPedido.CONCLUIDO) {
                CriarPedidoResponseDTO pedidoConcluidoDTO = pedidoMapper.toCriarPedidoResponseDTO(pedidoProcessado);
                pedidoEventPublisher.publicarPedidoConcluido(pedidoConcluidoDTO);
            }

        } catch (Exception ex) {
            log.error("Erro ao processar pedido {}: {}", pedidoDTO.getChavePedido(), ex.getMessage(), ex);
            throw ex;
        }
    }
}
