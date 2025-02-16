package com.app.pedidoservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class PedidoCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${pedido.cache.tempo-expiracao}")
    private long tempoExpiracaoEmMinutos;

    public boolean pedidoJaExiste(String chavePedido) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(chavePedido));
    }

    public void armazenarPedidoNoCache(String chavePedido) {
        redisTemplate.opsForValue().set(chavePedido, "PROCESSADO", tempoExpiracaoEmMinutos, TimeUnit.MINUTES);
    }
}