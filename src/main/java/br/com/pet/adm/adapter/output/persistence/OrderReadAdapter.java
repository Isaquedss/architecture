package br.com.pet.adm.adapter.output.persistence;

import br.com.pet.adm.adapter.output.persistence.entity.OrderEntity;
import br.com.pet.adm.adapter.output.persistence.entity.OrderItemEntity;
import br.com.pet.adm.adapter.output.persistence.repository.OrderJpaRepository;
import br.com.pet.adm.application.port.output.OrderReadRepositoryPort;
import br.com.pet.adm.application.query.result.ItemResult;
import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Driven Adapter — leitura otimizada de pedidos (CQRS Read Side).
 */
@Component
@RequiredArgsConstructor
public class OrderReadAdapter implements OrderReadRepositoryPort {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Optional<OrderDetailResult> findDetailById(String orderId) {
        return jpaRepository.findById(orderId).map(this::toDetail);
    }

    @Override
    public Page<OrderSummaryResult> findByCustomer(String customerId, int page, int pageSize) {
        return jpaRepository
                .findAll(PageRequest.of(page, pageSize))
                .map(this::toSummary);
    }

    private OrderDetailResult toDetail(OrderEntity e) {
        List<ItemResult> items = e.getItems().stream().map(this::toItemResult).toList();
        return new OrderDetailResult(
                e.getOrderId(),
                e.getCustomerId(),
                e.getStatus(),
                "BRL " + e.getTotal(),
                items
        );
    }

    private OrderSummaryResult toSummary(OrderEntity e) {
        return new OrderSummaryResult(
                e.getOrderId(),
                e.getCustomerId(),
                e.getStatus(),
                "BRL " + e.getTotal()
        );
    }

    private ItemResult toItemResult(OrderItemEntity i) {
        return new ItemResult(
                i.getProductId(),
                i.getQuantity(),
                i.getCurrency() + " " + i.getUnitPrice(),
                i.getCurrency() + " " + i.getUnitPrice().multiply(java.math.BigDecimal.valueOf(i.getQuantity()))
        );
    }
}
