package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.CancelOrderCommand;
import br.com.pet.adm.application.port.input.CancelOrderPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.application.port.output.OrderWriteRepositoryPort;
import br.com.pet.adm.domain.aggregate.Order;
import br.com.pet.adm.domain.exception.OrderNotFoundException;

/**
 * Command Handler — processa cancelamento de pedido.
 */
public class CancelOrderHandler implements CancelOrderPort {

    private final OrderWriteRepositoryPort writeRepository;
    private final EventPublisherPort       eventPublisher;

    public CancelOrderHandler(OrderWriteRepositoryPort writeRepository,
                              EventPublisherPort eventPublisher) {
        this.writeRepository = writeRepository;
        this.eventPublisher  = eventPublisher;
    }

    @Override
    public void cancel(CancelOrderCommand command) {

        Order order = writeRepository.findById(command.orderId())
                .orElseThrow(() -> new OrderNotFoundException(command.orderId()));

        order.cancel();

        writeRepository.save(order);

        order.getDomainEvents().forEach(eventPublisher::publish);
        order.clearEvents();
    }
}
