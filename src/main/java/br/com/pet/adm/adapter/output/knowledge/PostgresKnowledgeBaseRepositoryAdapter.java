package br.com.pet.adm.adapter.output.knowledge;

import br.com.pet.adm.application.port.output.KnowledgeBaseRepositoryPort;
import br.com.pet.adm.domain.valueobject.KnowledgeBase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PostgresKnowledgeBaseRepositoryAdapter implements KnowledgeBaseRepositoryPort {

    private final KnowledgeBaseJpaRepository jpaRepository;

    @Override
    public void register(KnowledgeBase base) {
        // ignora se já existir
        if (jpaRepository.existsById(base.name())) return;

        jpaRepository.save(KnowledgeBaseEntity.builder()
                .name(base.name())
                .description(base.description())
                .build());
    }

    @Override
    public Optional<KnowledgeBase> findByName(String name) {
        return jpaRepository.findById(name)
                .map(e -> new KnowledgeBase(e.getName(), e.getDescription()));
    }

    @Override
    public List<KnowledgeBase> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(e -> new KnowledgeBase(e.getName(), e.getDescription()))
                .toList();
    }

    @Override
    public boolean exists(String name) {
        return jpaRepository.existsById(name);
    }
}
