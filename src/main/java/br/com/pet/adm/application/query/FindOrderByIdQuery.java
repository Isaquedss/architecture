package br.com.pet.adm.application.query;

/**
 * CQRS Query — intenção de buscar um pedido por ID.
 */
public record FindOrderByIdQuery(String orderId) {
    public FindOrderByIdQuery {
        if (orderId == null || orderId.isBlank())
            throw new IllegalArgumentException("ID é obrigatório");
    }
}
