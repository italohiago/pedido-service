package com.app.pedidoservice.domain.repository;

import com.app.pedidoservice.domain.model.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    Optional<PedidoEntity> findByChavePedido(String chavePedido);

    Page<PedidoEntity> findAll(Pageable pageable);
}