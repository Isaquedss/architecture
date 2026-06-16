package br.com.pet.adm.adapter.output.persistence.repository;

import br.com.pet.adm.adapter.output.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA — opera em OrderEntity.
 */
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
}
