package br.com.pet.adm.application.query;

/**
 * CQRS Query — intenção de buscar pedidos por cliente.
 */
public record FindOrdersByCustomerQuery(
    String customerId,
    int    page,
    int    pageSize
) {}
