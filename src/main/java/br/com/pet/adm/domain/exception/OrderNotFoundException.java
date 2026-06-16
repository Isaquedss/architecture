package br.com.pet.adm.domain.exception;

/**
 * Exceção de negócio — pertence ao Domain.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderId) {
        super("Pedido não encontrado: " + orderId);
    }
}
