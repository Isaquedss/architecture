package br.com.pet.adm.application.port.output;

import br.com.pet.adm.domain.aggregate.Order;

import java.util.Optional;

/**
 * Driven Port — contrato de escrita para Order.
 */
public interface OrderWriteRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(String orderId);
}
