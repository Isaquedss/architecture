package br.com.pet.adm.adapter.config;

import br.com.pet.adm.application.command.handler.CancelOrderHandler;
import br.com.pet.adm.application.command.handler.CreateOrderHandler;
import br.com.pet.adm.application.port.input.CancelOrderPort;
import br.com.pet.adm.application.port.input.CreateOrderPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.application.port.output.OrderReadRepositoryPort;
import br.com.pet.adm.application.port.output.OrderWriteRepositoryPort;
import br.com.pet.adm.application.query.handler.FindOrderByIdHandler;
import br.com.pet.adm.application.query.handler.FindOrdersByCustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config — instancia os Order Handlers para o Spring.
 */
@Configuration
public class OrderConfig {

    @Bean
    public CreateOrderPort createOrderPort(OrderWriteRepositoryPort writeRepo,
                                           EventPublisherPort eventPublisher) {
        return new CreateOrderHandler(writeRepo, eventPublisher);
    }

    @Bean
    public CancelOrderPort cancelOrderPort(OrderWriteRepositoryPort writeRepo,
                                           EventPublisherPort eventPublisher) {
        return new CancelOrderHandler(writeRepo, eventPublisher);
    }

    @Bean
    public FindOrderByIdHandler findOrderByIdHandler(OrderReadRepositoryPort readRepo) {
        return new FindOrderByIdHandler(readRepo);
    }

    @Bean
    public FindOrdersByCustomerHandler findOrdersByCustomerHandler(OrderReadRepositoryPort readRepo) {
        return new FindOrdersByCustomerHandler(readRepo);
    }
}
