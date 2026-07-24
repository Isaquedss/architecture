package br.com.pet.adm.adapter.output.knowledge;

import br.com.pet.adm.application.port.output.KnowledgeBaseRepositoryPort;
import br.com.pet.adm.domain.valueobject.KnowledgeBase;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Salva as informações em memória, para fins de teste. Não é persistente.
public class InMemoryKnowledgeBaseRepositoryAdapter implements KnowledgeBaseRepositoryPort {

    private final Map<String, KnowledgeBase> store = new ConcurrentHashMap<>();

    public InMemoryKnowledgeBaseRepositoryAdapter() {
        // registra as bases padrão
        register(KnowledgeBase.ARQUITETURA);
        register(KnowledgeBase.NEGOCIO);
        register(KnowledgeBase.TECNICO);
    }

    @Override
    public void register(KnowledgeBase base) {
        store.put(base.name(), base);
    }

    @Override
    public Optional<KnowledgeBase> findByName(String name) {
        return Optional.ofNullable(store.get(name));
    }

    @Override
    public List<KnowledgeBase> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean exists(String name) {
        return store.containsKey(name);
    }
}
