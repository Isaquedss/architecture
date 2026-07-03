package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.CreateBankCommand;
import br.com.pet.adm.application.port.input.CreateBankPort;
import br.com.pet.adm.application.port.output.BankWriteRepositoryPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.domain.entity.Bank;
import br.com.pet.adm.domain.event.BankCreatedEvent;
import lombok.AllArgsConstructor;


/**
 * Command Handler — processa criação de banco.
 * Implementa o Driving Port (CreateBankPort).
 * Depende apenas dos Driven Ports (interfaces).
 */
@AllArgsConstructor
public class CreateBankHandler implements CreateBankPort {

    private final BankWriteRepositoryPort writeRepository;
    private final EventPublisherPort      eventPublisher;

    @Override
    public String create(CreateBankCommand command) {

        // verifica duplicidade
        if (writeRepository.existsById(command.cdBank()))
            throw new IllegalArgumentException(
                "Banco já cadastrado com o código: " + command.cdBank());

        // cria a Entity rica do Domain
        Bank bank = new Bank(command.cdBank(), command.dsBank());

        // persiste via Port
        Bank saved = writeRepository.save(bank);

        // publica Domain Event
        eventPublisher.publish(
            new BankCreatedEvent(saved.getCdBank(), saved.getDsBank()));

        return saved.getCdBank();
    }
}
