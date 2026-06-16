package br.com.pet.adm.application.port.output;

/**
 * Driven Port — o núcleo não sabe se é Spring Events, Kafka, RabbitMQ...
 */
public interface EventPublisherPort {
    void publish(Object event);
}
