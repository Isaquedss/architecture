package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.CreateUfCommand;

public interface CreatedUfPort {
    String create(CreateUfCommand command);
}
