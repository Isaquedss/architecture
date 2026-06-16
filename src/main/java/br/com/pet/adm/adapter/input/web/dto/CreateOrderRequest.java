package br.com.pet.adm.adapter.input.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * DTO de entrada para criação de pedido.
 */
@Data
public class CreateOrderRequest {

    @NotBlank(message = "Cliente é obrigatório")
    private String customerId;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private String productId;
        private int    quantity;
        private double unitPrice;
        private String currency = "BRL";
    }
}
