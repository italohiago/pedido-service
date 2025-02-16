package com.app.pedidoservice.domain.service;

import com.app.pedidoservice.domain.model.ClienteEntity;
import com.app.pedidoservice.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteEntity buscarOuCriarCliente(String idExterno, String nome) {
        return clienteRepository.findByIdExterno(idExterno)
                .map(clienteExistente -> {
                    if (!clienteExistente.getNome().equals(nome)) {
                        log.info("Atualizando nome do cliente {} de '{}' para '{}'",
                                clienteExistente.getIdExterno(), clienteExistente.getNome(), nome);
                        clienteExistente.setNome(nome);
                        return clienteRepository.save(clienteExistente);
                    }
                    return clienteExistente;
                })
                .orElseGet(() -> {
                    ClienteEntity novoCliente = ClienteEntity.builder()
                            .idExterno(idExterno)
                            .nome(nome)
                            .build();
                    return clienteRepository.save(novoCliente);
                });
    }
}
