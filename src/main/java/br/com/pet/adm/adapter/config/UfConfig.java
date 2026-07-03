package br.com.pet.adm.adapter.config;

import br.com.pet.adm.application.command.handler.CreatedUfHandler;
import br.com.pet.adm.application.port.input.CreatedUfPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.application.port.output.UfWriteRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UfConfig {

    @Bean
    public CreatedUfPort   createdUfPort(UfWriteRepositoryPort writeRepo, EventPublisherPort eventPublisher) {
        return new CreatedUfHandler(writeRepo, eventPublisher);
    }
}
