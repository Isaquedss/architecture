package br.com.pet.adm.application.port.output;

import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Driven Port — contrato de leitura otimizado para Order (CQRS Read Side).
 */
public interface OrderReadRepositoryPort {
    Optional<OrderDetailResult> findDetailById(String orderId);
    Page<OrderSummaryResult> findByCustomer(String customerId, int page, int pageSize);
}
