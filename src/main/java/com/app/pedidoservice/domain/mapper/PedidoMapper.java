package com.app.pedidoservice.domain.mapper;

import com.app.pedidoservice.application.dto.ClientePedidoDTO;
import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.application.dto.ItemPedidoDTO;
import com.app.pedidoservice.application.dto.PedidoDTO;
import com.app.pedidoservice.domain.model.ClienteEntity;
import com.app.pedidoservice.domain.model.PedidoEntity;
import com.app.pedidoservice.domain.model.PedidoItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoEntity toEntity(CriarPedidoResponseDTO dto, ClienteEntity cliente) {
        List<PedidoItemEntity> itens = dto.getItens().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());

        PedidoEntity pedido = PedidoEntity.builder()
                .chavePedido(dto.getChavePedido())
                .valorTotal(dto.getValorTotal())
                .criadoEm(dto.getDataCriacao())
                .cliente(cliente)
                .itens(itens)
                .build();

        // Associar os itens ao pedido
        itens.forEach(item -> item.setPedido(pedido));

        return pedido;
    }

    private PedidoItemEntity toItemEntity(ItemPedidoDTO itemDTO) {
        return PedidoItemEntity.builder()
                .produtoId(String.valueOf(itemDTO.getId()))
                .nomeProduto(itemDTO.getNomeProduto())
                .quantidade(itemDTO.getQuantidade())
                .precoUnitario(itemDTO.getPrecoUnitario())
                .build();
    }

    public CriarPedidoResponseDTO toCriarPedidoResponseDTO(PedidoEntity pedido) {
        List<ItemPedidoDTO> itens = pedido.getItens().stream()
                .map(item -> new ItemPedidoDTO(
                        Long.parseLong(item.getProdutoId()),
                        item.getNomeProduto(),
                        item.getPrecoUnitario(),
                        item.getQuantidade()
                ))
                .collect(Collectors.toList());

        return new CriarPedidoResponseDTO(
                pedido.getChavePedido(),
                pedido.getCliente().getNome(),
                pedido.getCliente().getIdExterno(),
                itens,
                pedido.getValorTotal(),
                pedido.getCriadoEm(),
                pedido.getStatus().name()
        );
    }

    public PedidoDTO toPedidoDTO(PedidoEntity pedido) {
        List<ItemPedidoDTO> itens = pedido.getItens().stream()
                .map(item -> new ItemPedidoDTO(
                        Long.parseLong(item.getProdutoId()),
                        item.getNomeProduto(),
                        item.getPrecoUnitario(),
                        item.getQuantidade()
                ))
                .collect(Collectors.toList());

        return new PedidoDTO(
                pedido.getId(),
                pedido.getChavePedido(),
                pedido.getStatus().name(),
                pedido.getValorTotal(),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm(),
                new ClientePedidoDTO(pedido.getCliente().getIdExterno(), pedido.getCliente().getNome()),
                itens
        );
    }
}