package br.com.pet.adm.application.port.output;

import br.com.pet.adm.application.query.result.BankResult;
import br.com.pet.adm.application.criteria.BankSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Driven Port — contrato de leitura otimizado (CQRS Read Side).
 * Retorna DTOs diretamente, sem instanciar o Domain Model.
 */
public interface BankReadRepositoryPort {
    Page<BankResult> findAll(BankSearchCriteria criteria);
    Optional<BankResult> findById(String cdBank);
}
