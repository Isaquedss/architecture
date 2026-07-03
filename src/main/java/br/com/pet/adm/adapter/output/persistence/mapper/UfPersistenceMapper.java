package br.com.pet.adm.adapter.output.persistence.mapper;

import br.com.pet.adm.adapter.output.persistence.entity.UfEntity;
import br.com.pet.adm.domain.entity.Uf;
import br.com.pet.adm.domain.valueobject.StatusFlag;
import org.springframework.stereotype.Component;

@Component
public class UfPersistenceMapper {

    public UfEntity toEntity(Uf bank) {
        return UfEntity.builder()
                .ufName(bank.getUfName())
                .activeFlag(bank.getStatus().toFlag())
                .creationDate(bank.getCreationDate())
                .changeDate(bank.getChangeDate())
                .inactivationDate(bank.getInactivationDate())
                .userId(bank.getUserId())
                .build();
    }

    public Uf toDomain(UfEntity entity) {
        return new Uf(
                entity.getId(),
                entity.getUfName(),
                StatusFlag.from(entity.getActiveFlag()),
                entity.getCreationDate(),
                entity.getChangeDate(),
                entity.getInactivationDate(),
                entity.getUserId()
        );
    }
}
