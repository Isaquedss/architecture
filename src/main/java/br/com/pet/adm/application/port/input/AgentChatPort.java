package br.com.pet.adm.application.port.input;

import br.com.pet.adm.domain.valueobject.RagAnswer;

import java.util.Map;

/**
 * Driving Port — chat com histórico, RAG e tool calling (agente).
 */
public interface AgentChatPort {

    // inicia uma nova conversa, retorna o conversationId
    String startConversation();

    // pergunta dentro de uma conversa existente
    RagAnswer chat(String conversationId, String question, Map<String, Object> filters);

    // limpa o histórico de uma conversa
    void clearConversation(String conversationId);
}
