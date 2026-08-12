package br.com.pet.adm.adapter.output.conversation;

import br.com.pet.adm.adapter.output.conversation.entity.ConversationEntity;
import br.com.pet.adm.adapter.output.conversation.entity.ConversationMessageEntity;
import br.com.pet.adm.domain.entity.Conversation;
import br.com.pet.adm.domain.valueobject.ConversationMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper manual — isola o Domain do JPA, igual ao padrão já usado em BankPersistenceMapper.
 */
public class ConversationPersistenceMapper {

    // Domain → JPA Entity
    public ConversationEntity toEntity(Conversation conversation) {
        ConversationEntity entity = ConversationEntity.builder()
                .conversationId(conversation.conversationId())
                .messages(new ArrayList<>())
                .build();

        conversation.messages().forEach(msg -> {
            ConversationMessageEntity msgEntity = toMessageEntity(msg, entity);
            entity.getMessages().add(msgEntity);
        });

        return entity;
    }

    // JPA Entity → Domain
    public Conversation toDomain(ConversationEntity entity) {
        List<ConversationMessage> messages = entity.getMessages().stream()
                .map(this::toMessageDomain)
                .toList();

        return new Conversation(entity.getConversationId(), messages);
    }

    // ── privados ─────────────────────────────────────────────────────────

    private ConversationMessageEntity toMessageEntity(ConversationMessage msg,
                                                      ConversationEntity parent) {
        return ConversationMessageEntity.builder()
                .conversation(parent)
                .role(ConversationMessageEntity.Role.valueOf(msg.role().name()))
                .content(msg.content())
                .occurredAt(msg.occurredAt())
                .build();
    }

    private ConversationMessage toMessageDomain(ConversationMessageEntity entity) {
        ConversationMessage.Role role =
                ConversationMessage.Role.valueOf(entity.getRole().name());
        return new ConversationMessage(role, entity.getContent(), entity.getOccurredAt());
    }
}