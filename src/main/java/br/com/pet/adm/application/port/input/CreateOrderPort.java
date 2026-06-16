package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.CreateOrderCommand;

/**
 * Driving Port — expõe o caso de uso de criação de pedido.
 */
public interface CreateOrderPort {
    String create(CreateOrderCommand command);
}
