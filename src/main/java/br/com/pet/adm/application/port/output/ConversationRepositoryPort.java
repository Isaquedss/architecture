package br.com.pet.adm.application.port.output;

import br.com.pet.adm.domain.entity.Conversation;

import java.util.Optional;

/**
 * Driven Port — abstrai o armazenamento de conversas.
 */
public interface ConversationRepositoryPort {

    Conversation save(Conversation conversation);

    Optional<Conversation> findById(String conversationId);

    void deleteById(String conversationId);
}
