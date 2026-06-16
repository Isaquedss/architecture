package br.com.pet.adm.domain.event;

import java.util.Date;

/**
 * Domain Event — pedido foi cancelado.
 */
public class OrderCancelledEvent {

    private final String orderId;
    private final String customerId;
    private final Date   occurredAt;

    public OrderCancelledEvent(String orderId, String customerId) {
        this.orderId    = orderId;
        this.customerId = customerId;
        this.occurredAt = new Date();
    }

    public String getOrderId()    { return orderId; }
    public String getCustomerId() { return customerId; }
    public Date   getOccurredAt() { return occurredAt; }
}
