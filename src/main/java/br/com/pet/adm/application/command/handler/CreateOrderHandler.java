package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.CreateOrderCommand;
import br.com.pet.adm.application.command.ItemCommand;
import br.com.pet.adm.application.port.input.CreateOrderPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.application.port.output.OrderWriteRepositoryPort;
import br.com.pet.adm.domain.aggregate.Order;
import br.com.pet.adm.domain.factory.OrderFactory;
import br.com.pet.adm.domain.valueobject.Money;

/**
 * Command Handler — processa criação de pedido.
 */
public class CreateOrderHandler implements CreateOrderPort {

    private final OrderWriteRepositoryPort writeRepository;
    private final EventPublisherPort       eventPublisher;

    public CreateOrderHandler(OrderWriteRepositoryPort writeRepository,
                              EventPublisherPort eventPublisher) {
        this.writeRepository = writeRepository;
        this.eventPublisher  = eventPublisher;
    }

    @Override
    public String create(CreateOrderCommand command) {

        // cria o Aggregate via Factory
        Order order = OrderFactory.create(command.customerId());

        // adiciona itens pelo Aggregate Root
        for (ItemCommand item : command.items()) {
            order.addItem(
                item.productId(),
                item.quantity(),
                Money.of(item.unitPrice(), item.currency())
            );
        }

        // regra de negócio: finaliza o pedido
        order.place();

        // persiste
        writeRepository.save(order);

        // publica os Domain Events gerados pelo Aggregate
        order.getDomainEvents().forEach(eventPublisher::publish);
        order.clearEvents();

        return order.getOrderId();
    }
}
