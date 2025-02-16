package com.app.pedidoservice.domain.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusPedido {
    PENDENTE(1, "Pendente"),
    PROCESSANDO(2, "Processando"),
    CONCLUIDO(3, "Concluído");

    private final int id;
    private final String nome;

    StatusPedido(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static StatusPedido fromId(int id) {
        return Arrays.stream(StatusPedido.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("StatusPedido inválido: " + id));
    }

    @Override
    public String toString() {
        return nome;
    }
}