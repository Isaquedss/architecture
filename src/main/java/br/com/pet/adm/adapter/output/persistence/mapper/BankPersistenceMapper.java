package br.com.pet.adm.adapter.output.persistence.mapper;

import br.com.pet.adm.adapter.output.persistence.entity.BankEntity;
import br.com.pet.adm.domain.entity.Bank;
import br.com.pet.adm.domain.valueobject.BankStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Mapper manual entre Domain Entity e JPA Entity.
 * Isola completamente o Domain do JPA.
 */
@Component
public class BankPersistenceMapper {

    // Domain → JPA Entity (para salvar)
    public BankEntity toEntity(Bank bank) {
        return BankEntity.builder()
                .cdBank(bank.getCdBank())
                .dsBank(bank.getDsBank())
                .activeFlag(bank.getStatus().toFlag())
                .creationDate(bank.getCreationDate() != null ? bank.getCreationDate() : new Date())
                .changeDate(bank.getChangeDate() != null ? bank.getChangeDate() : new Date())
                .inactivationDate(bank.getInactivationDate())
                .userId(bank.getUserId())
                .build();
    }

    // JPA Entity → Domain (para usar na regra de negócio)
    public Bank toDomain(BankEntity entity) {
        return new Bank(
                entity.getCdBank(),
                entity.getDsBank(),
                BankStatus.from(entity.getActiveFlag()),
                entity.getCreationDate(),
                entity.getChangeDate(),
                entity.getInactivationDate(),
                entity.getUserId()
        );
    }
}
