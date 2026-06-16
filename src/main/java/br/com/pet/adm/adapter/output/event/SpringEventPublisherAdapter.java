package br.com.pet.adm.adapter.output.event;

import br.com.pet.adm.application.port.output.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Driven Adapter — publica Domain Events via Spring Events.
 * Quer trocar por Kafka? Cria KafkaEventPublisherAdapter.
 */
@Component
@RequiredArgsConstructor
public class SpringEventPublisherAdapter implements EventPublisherPort {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}
