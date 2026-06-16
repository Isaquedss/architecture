package br.com.pet.adm.application.port.output;

import br.com.pet.adm.domain.entity.Bank;

import java.util.Optional;

/**
 * Driven Port — contrato de escrita definido pelo núcleo.
 * A Infra (JPA) implementa isso, nunca o contrário.
 */
public interface BankWriteRepositoryPort {
    Bank save(Bank bank);
    Optional<Bank> findById(String cdBank);
    boolean existsById(String cdBank);
}
