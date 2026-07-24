package br.com.pet.adm.domain.valueobject;

import java.time.LocalDateTime;

public record ConversationMessage(
        Role role,
        String content,
        LocalDateTime occurredAt
) {
    public enum Role {
        USER,
        ASSISTANT
    }

    public ConversationMessage {
        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Conteúdo da mensagem é obrigatório");
    }
    public static ConversationMessage user(String content) {
        return new ConversationMessage(Role.USER, content, LocalDateTime.now());
    }

    public static ConversationMessage assistant(String content) {
        return new ConversationMessage(Role.ASSISTANT, content, LocalDateTime.now());
    }
}
