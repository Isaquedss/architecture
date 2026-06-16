package br.com.pet.adm.domain.factory;

import br.com.pet.adm.domain.aggregate.Order;

import java.util.UUID;

/**
 * Factory — encapsula criação do Aggregate Order.
 * Garante que Order nasce sempre válida com ID gerado.
 */
public class OrderFactory {

    public static Order create(String customerId) {
        String orderId = "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new Order(orderId, customerId);
    }

    public static Order reconstitute(String orderId, String customerId) {
        return new Order(orderId, customerId);
    }
}
