package br.com.pet.adm.adapter.output.persistence.repository;

import br.com.pet.adm.adapter.output.persistence.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA — opera em BankEntity, nunca no Domain.
 * Equivalente ao seu BankRepository atual, isolado na Infra.
 */
@Repository
public interface BankJpaRepository
        extends JpaRepository<BankEntity, String>,
                JpaSpecificationExecutor<BankEntity> {
}
