package br.com.pet.adm.application.query.handler;

import br.com.pet.adm.application.port.input.OrderQueryPort;
import br.com.pet.adm.application.query.FindOrderByIdQuery;
import br.com.pet.adm.application.query.FindOrdersByCustomerQuery;
import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import org.springframework.data.domain.Page;

/**
 * Compõe os query handlers de pedido e implementa {@link OrderQueryPort}.
 * Os handlers concretos têm cada um um único método {@code handle}, então
 * esta classe atua como adapter de composição em vez de os alterar diretamente.
 */
public class OrderQueryHandler implements OrderQueryPort {

    private final FindOrderByIdHandler findOrderByIdHandler;
    private final FindOrdersByCustomerHandler findOrdersByCustomerHandler;

    public OrderQueryHandler(FindOrderByIdHandler findOrderByIdHandler,
                             FindOrdersByCustomerHandler findOrdersByCustomerHandler) {
        this.findOrderByIdHandler = findOrderByIdHandler;
        this.findOrdersByCustomerHandler = findOrdersByCustomerHandler;
    }

    @Override
    public OrderDetailResult findById(FindOrderByIdQuery query) {
        return findOrderByIdHandler.handle(query);
    }

    @Override
    public Page<OrderSummaryResult> findByCustomer(FindOrdersByCustomerQuery query) {
        return findOrdersByCustomerHandler.handle(query);
    }
}
