package com.app.pedidoservice.presentation.controller;

import com.app.pedidoservice.application.dto.CriarPedidoRequestDTO;
import com.app.pedidoservice.application.dto.CriarPedidoResponseDTO;
import com.app.pedidoservice.application.dto.PedidoDTO;
import com.app.pedidoservice.application.usecase.BuscarPedidoPorIdUseCase;
import com.app.pedidoservice.application.usecase.CriarPedidoUseCase;
import com.app.pedidoservice.application.usecase.ListarPedidosUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoControllerV1 {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final ListarPedidosUseCase listarPedidosUseCase;
    private final BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;
    @PostMapping
    @Operation(
            summary = "Criar um novo pedido",
            description = "Cria um pedido, calcula o total e define o status inicial como PENDENTE.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                            content = @Content(schema = @Schema(implementation = CriarPedidoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    public ResponseEntity<CriarPedidoResponseDTO> criarPedido(@Valid @RequestBody CriarPedidoRequestDTO pedidoRequest) {
        CriarPedidoResponseDTO response = criarPedidoUseCase.executar(pedidoRequest);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar um pedido pelo ID",
            description = "Retorna os detalhes do pedido pelo identificador único.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                            content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            }
    )
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(
            @Parameter(description = "ID do pedido", example = "123") @PathVariable Long id) {
        PedidoDTO response = buscarPedidoPorIdUseCase.executar(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os pedidos com paginação",
            description = "Retorna a lista de pedidos paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
            }
    )
    public ResponseEntity<Page<PedidoDTO>> listarPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PedidoDTO> response = listarPedidosUseCase.executar(pageable);

        return ResponseEntity.ok(response);
    }
}
