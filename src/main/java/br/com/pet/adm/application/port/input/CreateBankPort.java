package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.CreateBankCommand;

/**
 * Driving Port — expõe o caso de uso de criação de banco.
 * O Controller depende disso, nunca do Use Case diretamente.
 */
public interface CreateBankPort {
    String create(CreateBankCommand command);
}
