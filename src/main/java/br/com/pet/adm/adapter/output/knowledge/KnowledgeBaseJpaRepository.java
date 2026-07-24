package br.com.pet.adm.adapter.output.knowledge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeBaseJpaRepository extends JpaRepository<KnowledgeBaseEntity, String> {
}
