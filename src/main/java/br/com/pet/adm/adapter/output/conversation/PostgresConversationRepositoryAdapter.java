package br.com.pet.adm.adapter.output.conversation;

import br.com.pet.adm.adapter.output.conversation.entity.ConversationEntity;
import br.com.pet.adm.adapter.output.conversation.repository.ConversationJpaRepository;
import br.com.pet.adm.application.port.output.ConversationRepositoryPort;
import br.com.pet.adm.domain.entity.Conversation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Driven Adapter — persiste conversas no PostgreSQL via JPA.
 * Troca o InMemoryConversationRepositoryAdapter sem alterar nada no Domain ou nos Handlers.
 */
@RequiredArgsConstructor
public class PostgresConversationRepositoryAdapter implements ConversationRepositoryPort {

    private final ConversationJpaRepository jpaRepository;
    private final ConversationPersistenceMapper mapper = new ConversationPersistenceMapper();

    @Override
    public Conversation save(Conversation conversation) {
        ConversationEntity entity = mapper.toEntity(conversation);
        ConversationEntity saved  = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Conversation> findById(String conversationId) {
        return jpaRepository.findById(conversationId).map(mapper::toDomain);
    }

    @Override
    public void deleteById(String conversationId) {
        jpaRepository.deleteById(conversationId);
    }
}