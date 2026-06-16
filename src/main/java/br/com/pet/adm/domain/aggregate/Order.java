package br.com.pet.adm.domain.aggregate;

import br.com.pet.adm.domain.entity.OrderItem;
import br.com.pet.adm.domain.event.OrderCancelledEvent;
import br.com.pet.adm.domain.event.OrderPlacedEvent;
import br.com.pet.adm.domain.valueobject.Money;
import br.com.pet.adm.domain.valueobject.OrderStatus;

import java.util.*;

/**
 * Aggregate Root — única porta de entrada para modificar o pedido.
 * Garante todas as invariantes de negócio.
 */
public class Order {

    private final String           orderId;
    private final String           customerId;
    private final List<OrderItem>  items;
    private OrderStatus            status;
    private final List<Object>     domainEvents;

    public Order(String orderId, String customerId) {
        if (orderId == null || orderId.isBlank())
            throw new IllegalArgumentException("ID do pedido é obrigatório");
        if (customerId == null || customerId.isBlank())
            throw new IllegalArgumentException("ID do cliente é obrigatório");
        this.orderId      = orderId;
        this.customerId   = customerId;
        this.items        = new ArrayList<>();
        this.status       = OrderStatus.OPEN;
        this.domainEvents = new ArrayList<>();
    }

    // --- comportamentos ---

    public void addItem(String productId, int quantity, Money unitPrice) {
        if (status != OrderStatus.OPEN)
            throw new IllegalStateException("Pedido fechado não aceita novos itens");
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva");

        // invariante: produto duplicado soma a quantidade
        items.stream()
            .filter(i -> i.getProductId().equals(productId))
            .findFirst()
            .ifPresentOrElse(
                existing -> existing.increaseQuantity(quantity),
                () -> items.add(new OrderItem(productId, quantity, unitPrice))
            );
    }

    public void removeItem(String productId) {
        if (status != OrderStatus.OPEN)
            throw new IllegalStateException("Pedido fechado não permite remoção");
        boolean removed = items.removeIf(i -> i.getProductId().equals(productId));
        if (!removed)
            throw new IllegalArgumentException("Produto não encontrado: " + productId);
    }

    public void place() {
        if (status != OrderStatus.OPEN)
            throw new IllegalStateException("Pedido já foi finalizado");
        if (items.isEmpty())
            throw new IllegalStateException("Pedido não pode ser finalizado sem itens");
        this.status = OrderStatus.PLACED;
        domainEvents.add(new OrderPlacedEvent(orderId, customerId, calculateTotal()));
    }

    public void cancel() {
        if (status == OrderStatus.CANCELLED)
            throw new IllegalStateException("Pedido já está cancelado");
        if (status == OrderStatus.DELIVERED)
            throw new IllegalStateException("Pedido entregue não pode ser cancelado");
        this.status = OrderStatus.CANCELLED;
        domainEvents.add(new OrderCancelledEvent(orderId, customerId));
    }

    public Money calculateTotal() {
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(Money.zero("BRL"), Money::add);
    }

    public int totalItems() {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    // --- identidade ---

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order)) return false;
        return Objects.equals(orderId, ((Order) o).orderId);
    }

    @Override
    public int hashCode() { return Objects.hash(orderId); }

    // --- domain events ---

    public List<Object> getDomainEvents()  { return Collections.unmodifiableList(domainEvents); }
    public void         clearEvents()      { domainEvents.clear(); }

    // --- getters ---

    public String          getOrderId()    { return orderId; }
    public String          getCustomerId() { return customerId; }
    public OrderStatus     getStatus()     { return status; }
    public List<OrderItem> getItems()      { return Collections.unmodifiableList(items); }
}
