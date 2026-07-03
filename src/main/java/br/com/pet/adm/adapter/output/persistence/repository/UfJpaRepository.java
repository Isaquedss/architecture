package br.com.pet.adm.adapter.output.persistence.repository;

import br.com.pet.adm.adapter.output.persistence.entity.UfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UfJpaRepository extends JpaRepository<UfEntity, String>, JpaSpecificationExecutor<UfEntity> {
    UfEntity findByUfNameIgnoreCase(String ufName);
}
