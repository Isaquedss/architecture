package br.com.pet.adm.application.command;

/**
 * Dado de entrada do CreateOrderCommand.
 */
public record ItemCommand(
    String productId,
    int    quantity,
    double unitPrice,
    String currency
) {}
