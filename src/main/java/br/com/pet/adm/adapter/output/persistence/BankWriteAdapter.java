package br.com.pet.adm.adapter.output.persistence;

import br.com.pet.adm.adapter.output.persistence.entity.BankEntity;
import br.com.pet.adm.adapter.output.persistence.mapper.BankPersistenceMapper;
import br.com.pet.adm.adapter.output.persistence.repository.BankJpaRepository;
import br.com.pet.adm.application.port.output.BankWriteRepositoryPort;
import br.com.pet.adm.domain.entity.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Driven Adapter — implementa BankWriteRepositoryPort usando JPA.
 * Quer trocar JPA por Mongo? Cria MongoBankWriteAdapter.
 */
@Component
@RequiredArgsConstructor
public class BankWriteAdapter implements BankWriteRepositoryPort {

    private final BankJpaRepository     jpaRepository;
    private final BankPersistenceMapper mapper;

    @Override
    public Bank save(Bank bank) {
        BankEntity entity = mapper.toEntity(bank);
        BankEntity saved  = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Bank> findById(String cdBank) {
        return jpaRepository.findById(cdBank).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(String cdBank) {
        return jpaRepository.existsById(cdBank);
    }
}
