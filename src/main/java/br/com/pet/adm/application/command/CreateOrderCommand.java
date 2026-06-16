package br.com.pet.adm.application.command;

import java.util.List;

/**
 * CQRS Command — intenção de criar um pedido.
 */
public record CreateOrderCommand(
    String           customerId,
    List<ItemCommand> items
) {
    public CreateOrderCommand {
        if (customerId == null || customerId.isBlank())
            throw new IllegalArgumentException("Cliente é obrigatório");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Pedido deve ter pelo menos um item");
    }
}
