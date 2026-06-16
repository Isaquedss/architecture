package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.DeactivateBankCommand;

/**
 * Driving Port — expõe o caso de uso de inativação de banco.
 */
public interface DeactivateBankPort {
    void deactivate(DeactivateBankCommand command);
}
