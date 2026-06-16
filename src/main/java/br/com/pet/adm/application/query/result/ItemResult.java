package br.com.pet.adm.application.query.result;

/**
 * Query Result — item dentro de um OrderDetailResult.
 */
public record ItemResult(
    String productId,
    int    quantity,
    String unitPrice,
    String subtotal
) {}
