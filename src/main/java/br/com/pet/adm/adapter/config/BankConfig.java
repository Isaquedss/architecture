package br.com.pet.adm.adapter.config;

import br.com.pet.adm.application.command.handler.CreateBankHandler;
import br.com.pet.adm.application.command.handler.DeactivateBankHandler;
import br.com.pet.adm.application.port.input.CreateBankPort;
import br.com.pet.adm.application.port.input.DeactivateBankPort;
import br.com.pet.adm.application.port.input.FindAllBanksPort;
import br.com.pet.adm.application.port.output.BankReadRepositoryPort;
import br.com.pet.adm.application.port.output.BankWriteRepositoryPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.application.query.handler.FindAllBanksHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config — único lugar onde Handlers (Java puro) são instanciados pelo Spring.
 * Separa a configuração da lógica.
 */
@Configuration
public class BankConfig {

    @Bean
    public CreateBankPort createBankPort(BankWriteRepositoryPort writeRepo,
                                         EventPublisherPort eventPublisher) {
        return new CreateBankHandler(writeRepo, eventPublisher);
    }

    @Bean
    public DeactivateBankPort deactivateBankPort(BankWriteRepositoryPort writeRepo,
                                                  EventPublisherPort eventPublisher) {
        return new DeactivateBankHandler(writeRepo, eventPublisher);
    }

    @Bean
    public FindAllBanksPort findAllBanksPort(BankReadRepositoryPort readRepo) {
        return new FindAllBanksHandler(readRepo);
    }
}
