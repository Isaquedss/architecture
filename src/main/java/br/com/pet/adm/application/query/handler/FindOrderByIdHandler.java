package br.com.pet.adm.application.query.handler;

import br.com.pet.adm.application.port.output.OrderReadRepositoryPort;
import br.com.pet.adm.application.query.FindOrderByIdQuery;
import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.domain.exception.OrderNotFoundException;

/**
 * Query Handler — busca detalhe de um pedido.
 */
public class FindOrderByIdHandler {

    private final OrderReadRepositoryPort readRepository;

    public FindOrderByIdHandler(OrderReadRepositoryPort readRepository) {
        this.readRepository = readRepository;
    }

    public OrderDetailResult handle(FindOrderByIdQuery query) {
        return readRepository.findDetailById(query.orderId())
                .orElseThrow(() -> new OrderNotFoundException(query.orderId()));
    }
}
