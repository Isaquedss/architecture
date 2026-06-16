package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.DeactivateBankCommand;
import br.com.pet.adm.application.port.input.DeactivateBankPort;
import br.com.pet.adm.application.port.output.BankWriteRepositoryPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.domain.entity.Bank;
import br.com.pet.adm.domain.event.BankDeactivatedEvent;
import br.com.pet.adm.domain.exception.BankNotFoundException;

/**
 * Command Handler — processa inativação de banco.
 */
public class DeactivateBankHandler implements DeactivateBankPort {

    private final BankWriteRepositoryPort writeRepository;
    private final EventPublisherPort      eventPublisher;

    public DeactivateBankHandler(BankWriteRepositoryPort writeRepository,
                                 EventPublisherPort eventPublisher) {
        this.writeRepository = writeRepository;
        this.eventPublisher  = eventPublisher;
    }

    @Override
    public void deactivate(DeactivateBankCommand command) {

        Bank bank = writeRepository.findById(command.cdBank())
                .orElseThrow(() -> new BankNotFoundException(command.cdBank()));

        // regra de negócio encapsulada na Entity
        bank.deactivate();

        writeRepository.save(bank);

        eventPublisher.publish(
            new BankDeactivatedEvent(bank.getCdBank(), bank.getDsBank(), command.userId()));
    }
}
