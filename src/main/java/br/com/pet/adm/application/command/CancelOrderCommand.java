package br.com.pet.adm.application.command;

/**
 * CQRS Command — intenção de cancelar um pedido.
 */
public record CancelOrderCommand(
    String orderId,
    String cancelledBy
) {
    public CancelOrderCommand {
        if (orderId == null || orderId.isBlank())
            throw new IllegalArgumentException("ID do pedido é obrigatório");
    }
}
