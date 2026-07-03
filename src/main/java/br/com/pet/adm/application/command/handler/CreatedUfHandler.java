package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.CreateUfCommand;
import br.com.pet.adm.application.port.input.CreatedUfPort;
import br.com.pet.adm.application.port.output.EventPublisherPort;
import br.com.pet.adm.application.port.output.UfWriteRepositoryPort;
import br.com.pet.adm.domain.entity.Uf;
import br.com.pet.adm.domain.event.UfCreatedEvent;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class CreatedUfHandler implements CreatedUfPort {

    private final UfWriteRepositoryPort writeRepository;
    private final EventPublisherPort eventPublisher;

    @Override
    public String create(CreateUfCommand command) {
        try {
            if (writeRepository.findUfName(command.ufName()) != null)
                throw new IllegalArgumentException(
                        "UF já cadastrada com o nome: " + command.ufName());

            Uf uf = new Uf(command.ufName());

            Uf saved = writeRepository.save(uf);

            eventPublisher.publish(new UfCreatedEvent(saved.getUfName(), new Date()));

            return saved.getUfName();

        }  catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
