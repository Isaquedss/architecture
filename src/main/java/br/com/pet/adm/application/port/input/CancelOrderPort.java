package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.CancelOrderCommand;

/**
 * Driving Port — expõe o caso de uso de cancelamento de pedido.
 */
public interface CancelOrderPort {
    void cancel(CancelOrderCommand command);
}
