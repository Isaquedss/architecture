package br.com.pet.adm.application.query.result;

/**
 * Query Result — modelo resumido para listagem de pedidos.
 */
public record OrderSummaryResult(
    String orderId,
    String customerId,
    String status,
    String total
) {}
