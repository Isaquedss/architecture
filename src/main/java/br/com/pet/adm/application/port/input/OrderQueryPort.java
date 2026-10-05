package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.query.FindOrderByIdQuery;
import br.com.pet.adm.application.query.FindOrdersByCustomerQuery;
import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import org.springframework.data.domain.Page;

/**
 * Driving Port — expõe os casos de uso de consulta de pedidos.
 */
public interface OrderQueryPort {
    OrderDetailResult findById(FindOrderByIdQuery query);

    Page<OrderSummaryResult> findByCustomer(FindOrdersByCustomerQuery query);
}
