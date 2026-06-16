package br.com.pet.adm.adapter.output.event;

import br.com.pet.adm.domain.event.OrderCancelledEvent;
import br.com.pet.adm.domain.event.OrderPlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener de Domain Events do Order.
 */
@Component
public class OrderEventListener {

    @EventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        System.out.println("[📦 EVENT] Pedido finalizado: " + event.getOrderId()
                + " | Cliente: " + event.getCustomerId()
                + " | Total: " + event.getTotal()
                + " em " + event.getOccurredAt());
    }

    @EventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        System.out.println("[🗑️ EVENT] Pedido cancelado: " + event.getOrderId()
                + " | Cliente: " + event.getCustomerId()
                + " em " + event.getOccurredAt());
    }
}
