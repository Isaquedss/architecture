package br.com.pet.adm.application.port.input;

import br.com.pet.adm.domain.valueobject.RagAnswer;

import java.util.Map;

/**
        * Driving Port — expõe o caso de uso de chat com histórico.
 */
public interface ChatPort {

    // inicia uma nova conversa, retorna o conversationId
    String startConversation();

    // pergunta dentro de uma conversa existente
    RagAnswer chat(String conversationId, String question, Map<String, Object> filters);

    // limpa o histórico de uma conversa
    void clearConversation(String conversationId);
}
