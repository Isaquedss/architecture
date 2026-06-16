package br.com.pet.adm.application.query.result;

import java.util.List;

/**
 * Query Result — modelo de leitura detalhado para Order.
 * Pode conter dados de JOINs e campos formatados.
 */
public record OrderDetailResult(
    String           orderId,
    String           customerId,
    String           status,
    String           total,
    List<ItemResult> items
) {}
