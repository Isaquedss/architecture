package br.com.pet.adm.adapter.output.persistence;

import br.com.pet.adm.adapter.output.persistence.entity.OrderEntity;
import br.com.pet.adm.adapter.output.persistence.mapper.OrderPersistenceMapper;
import br.com.pet.adm.adapter.output.persistence.repository.OrderJpaRepository;
import br.com.pet.adm.application.port.output.OrderWriteRepositoryPort;
import br.com.pet.adm.domain.aggregate.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Driven Adapter — implementa OrderWriteRepositoryPort usando JPA.
 */
@Component
@RequiredArgsConstructor
public class OrderWriteAdapter implements OrderWriteRepositoryPort {

    private final OrderJpaRepository    jpaRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity saved  = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return jpaRepository.findById(orderId).map(mapper::toDomain);
    }
}
