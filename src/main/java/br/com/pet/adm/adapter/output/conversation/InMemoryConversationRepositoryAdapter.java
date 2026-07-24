package br.com.pet.adm.adapter.output.conversation;

import br.com.pet.adm.application.port.output.ConversationRepositoryPort;
import br.com.pet.adm.domain.entity.Conversation;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Driven Adapter — armazena conversas em memória (dev/testes).
 * Quer persistir no banco? Cria PostgresConversationRepositoryAdapter.
 */
public class InMemoryConversationRepositoryAdapter implements ConversationRepositoryPort {

    private final Map<String, Conversation> store = new ConcurrentHashMap<>();

    @Override
    public Conversation save(Conversation conversation) {
        store.put(conversation.conversationId(), conversation);
        return conversation;
    }

    @Override
    public Optional<Conversation> findById(String conversationId) {
        return Optional.ofNullable(store.get(conversationId));
    }

    @Override
    public void deleteById(String conversationId) {
        store.remove(conversationId);
    }
}
