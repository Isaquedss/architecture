package br.com.pet.adm.domain.event;

import br.com.pet.adm.domain.valueobject.Money;

import java.util.Date;

/**
 * Domain Event — pedido foi finalizado.
 */
public class OrderPlacedEvent {

    private final String orderId;
    private final String customerId;
    private final Money  total;
    private final Date   occurredAt;

    public OrderPlacedEvent(String orderId, String customerId, Money total) {
        this.orderId    = orderId;
        this.customerId = customerId;
        this.total      = total;
        this.occurredAt = new Date();
    }

    public String getOrderId()    { return orderId; }
    public String getCustomerId() { return customerId; }
    public Money  getTotal()      { return total; }
    public Date   getOccurredAt() { return occurredAt; }
}
