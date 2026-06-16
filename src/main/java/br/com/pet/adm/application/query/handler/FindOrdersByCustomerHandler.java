package br.com.pet.adm.application.query.handler;

import br.com.pet.adm.application.port.output.OrderReadRepositoryPort;
import br.com.pet.adm.application.query.FindOrdersByCustomerQuery;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import org.springframework.data.domain.Page;

/**
 * Query Handler — lista pedidos de um cliente.
 */
public class FindOrdersByCustomerHandler {

    private final OrderReadRepositoryPort readRepository;

    public FindOrdersByCustomerHandler(OrderReadRepositoryPort readRepository) {
        this.readRepository = readRepository;
    }

    public Page<OrderSummaryResult> handle(FindOrdersByCustomerQuery query) {
        return readRepository.findByCustomer(
            query.customerId(),
            query.page(),
            query.pageSize()
        );
    }
}
