package br.com.pet.adm.application.port.output;

import br.com.pet.adm.domain.valueobject.KnowledgeBase;

import java.util.List;
import java.util.Optional;

/**
 * Driven Port — abstrai o repositório de bases de conhecimento.
 */
public interface KnowledgeBaseRepositoryPort {

    void register(KnowledgeBase base);

    Optional<KnowledgeBase> findByName(String name);

    List<KnowledgeBase> findAll();

    boolean exists(String name);
}
