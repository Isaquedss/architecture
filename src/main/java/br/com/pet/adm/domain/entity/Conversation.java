package br.com.pet.adm.domain.entity;

import br.com.pet.adm.domain.valueobject.ConversationMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record Conversation(String conversationId, List<ConversationMessage> messages) {

    public Conversation() {
        this(UUID.randomUUID().toString(), new ArrayList<>());
    }

    public Conversation(String conversationId, List<ConversationMessage> messages) {
        this.conversationId = conversationId;
        this.messages = new ArrayList<>(messages);
    }

    public void addMessage(ConversationMessage message) {
        messages.add(message);
    }

    // retorna as últimas N mensagens para não estourar o contexto do LLM
    public List<ConversationMessage> getLastMessages(int limit) {
        int from = Math.max(0, messages.size() - limit);
        return Collections.unmodifiableList(messages.subList(from, messages.size()));
    }

    @Override
    public List<ConversationMessage> messages() {
        return Collections.unmodifiableList(messages);
    }
}
