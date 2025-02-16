package com.app.pedidoservice.domain.service;

import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoEventPublisher {

    private final KafkaTemplate<String, CriarPedidoResponseDTO> kafkaTemplate;

    @Value("${spring.kafka.producer.pedidos-novos.topic}")
    private String pedidosNovosTopic;

    @Value("${spring.kafka.producer.pedidos-novos.dlq-topic}")
    private String pedidosNovosDlqTopic;

    @Value("${spring.kafka.producer.pedidos-concluidos.topic}")
    private String pedidosConcluidosTopic;

    public void publicarPedido(CriarPedidoResponseDTO pedido) {
        log.info("Publicando pedido na fila: {}", pedido.getChavePedido());
        enviarParaKafka(pedidosNovosTopic, pedido, 0);
    }

    public void publicarPedidoConcluido(CriarPedidoResponseDTO pedido) {
        log.info("Publicando pedido concluído na fila: {}", pedido.getChavePedido());
        enviarParaKafka(pedidosConcluidosTopic, pedido, 0);
    }

    private void enviarParaKafka(String topic, CriarPedidoResponseDTO pedido, int tentativa) {
        kafkaTemplate.send(topic, pedido.getChavePedido(), pedido)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        RecordMetadata metadata = result.getRecordMetadata();
                        log.info("Pedido enviado para Kafka: {} (Tópico: {}, Partição: {}, Offset: {})",
                                pedido.getChavePedido(), topic, metadata.partition(), metadata.offset());
                    } else {
                        if (tentativa < 5) {
                            log.warn("Tentativa {} falhou para o pedido {}. Tentando novamente...", tentativa + 1, pedido.getChavePedido());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ignored) {}
                            enviarParaKafka(topic, pedido, tentativa + 1);
                        } else {
                            log.error("Erro após {} tentativas. Enviando pedido {} para DLQ.", tentativa, pedido.getChavePedido());
                            enviarParaDLQ(pedido);
                        }
                    }
                });
    }

    private void enviarParaDLQ(CriarPedidoResponseDTO pedido) {
        kafkaTemplate.send(pedidosNovosDlqTopic, pedido.getChavePedido(), pedido)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Pedido {} enviado para DLQ com sucesso.", pedido.getChavePedido());
                    } else {
                        log.error("Erro ao enviar pedido {} para DLQ: {}", pedido.getChavePedido(), ex.getMessage(), ex);
                    }
                });
    }
}