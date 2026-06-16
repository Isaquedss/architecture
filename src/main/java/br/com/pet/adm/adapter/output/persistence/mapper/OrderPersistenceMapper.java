package br.com.pet.adm.adapter.output.persistence.mapper;

import br.com.pet.adm.adapter.output.persistence.entity.OrderEntity;
import br.com.pet.adm.adapter.output.persistence.entity.OrderItemEntity;
import br.com.pet.adm.domain.aggregate.Order;
import br.com.pet.adm.domain.entity.OrderItem;
import br.com.pet.adm.domain.valueobject.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper entre Order Aggregate e JPA Entities.
 */
@Component
public class OrderPersistenceMapper {

    // Domain Aggregate → JPA Entity
    public OrderEntity toEntity(Order order) {
        OrderEntity entity = OrderEntity.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .status(order.getStatus().name())
                .total(order.calculateTotal().getAmount())
                .items(new ArrayList<>())
                .build();

        for (OrderItem item : order.getItems()) {
            OrderItemEntity itemEntity = OrderItemEntity.builder()
                    .order(entity)
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice().getAmount())
                    .currency(item.getUnitPrice().getCurrency())
                    .build();
            entity.getItems().add(itemEntity);
        }

        return entity;
    }

    // JPA Entity → Domain Aggregate
    public Order toDomain(OrderEntity entity) {
        Order order = new Order(entity.getOrderId(), entity.getCustomerId());
        for (OrderItemEntity item : entity.getItems()) {
            order.addItem(
                    item.getProductId(),
                    item.getQuantity(),
                    Money.of(item.getUnitPrice().doubleValue(), item.getCurrency())
            );
        }
        return order;
    }
}
