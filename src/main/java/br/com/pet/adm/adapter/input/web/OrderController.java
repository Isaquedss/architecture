package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.CreateOrderRequest;
import br.com.pet.adm.application.command.CancelOrderCommand;
import br.com.pet.adm.application.command.CreateOrderCommand;
import br.com.pet.adm.application.command.ItemCommand;
import br.com.pet.adm.application.port.input.CancelOrderPort;
import br.com.pet.adm.application.port.input.CreateOrderPort;
import br.com.pet.adm.application.query.FindOrderByIdQuery;
import br.com.pet.adm.application.query.FindOrdersByCustomerQuery;
import br.com.pet.adm.application.query.handler.FindOrderByIdHandler;
import br.com.pet.adm.application.query.handler.FindOrdersByCustomerHandler;
import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Driving Adapter — converte HTTP → Commands/Queries para Order.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Endpoints de gerenciamento de pedidos")
public class OrderController {

    // COMMAND side
    private final CreateOrderPort createOrderPort;
    private final CancelOrderPort cancelOrderPort;

    // QUERY side
    private final FindOrderByIdHandler        findOrderByIdHandler;
    private final FindOrdersByCustomerHandler findOrdersByCustomerHandler;

    // --- COMMANDS ---

    @PostMapping
    @Operation(summary = "Cria e finaliza um novo pedido")
    public ResponseEntity<Map<String, String>> create(
            @RequestBody @Valid CreateOrderRequest request) {

        List<ItemCommand> items = request.getItems().stream()
                .map(i -> new ItemCommand(i.getProductId(), i.getQuantity(),
                        i.getUnitPrice(), i.getCurrency()))
                .toList();

        String orderId = createOrderPort.create(
                new CreateOrderCommand(request.getCustomerId(), items));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("orderId", orderId));
    }

    @DeleteMapping("/{orderId}/cancel")
    @Operation(summary = "Cancela um pedido")
    public ResponseEntity<Void> cancel(
            @PathVariable String orderId,
            @RequestParam(required = false) String cancelledBy) {

        cancelOrderPort.cancel(new CancelOrderCommand(orderId, cancelledBy));
        return ResponseEntity.noContent().build();
    }

    // --- QUERIES ---

    @GetMapping("/{orderId}")
    @Operation(summary = "Busca detalhe de um pedido")
    public ResponseEntity<OrderDetailResult> findById(@PathVariable String orderId) {
        return ResponseEntity.ok(
                findOrderByIdHandler.handle(new FindOrderByIdQuery(orderId)));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Lista pedidos de um cliente")
    public ResponseEntity<Page<OrderSummaryResult>> findByCustomer(
            @PathVariable String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(
                findOrdersByCustomerHandler.handle(
                        new FindOrdersByCustomerQuery(customerId, page, pageSize)));
    }
}
