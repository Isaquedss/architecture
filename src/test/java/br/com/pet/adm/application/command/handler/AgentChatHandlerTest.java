package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.port.input.LlmPort;
import br.com.pet.adm.application.port.output.ConversationRepositoryPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.domain.entity.Conversation;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AgentChatHandlerTest {

    private final ConversationRepositoryPort repository = mock(ConversationRepositoryPort.class);
    private final DocumentStorePort documentStore = mock(DocumentStorePort.class);
    private final LlmPort llm = mock(LlmPort.class);
    private final AgentChatHandler handler = new AgentChatHandler(repository, documentStore, llm);

    @Test
    void chatHappyPathCallsLlmAndSavesConversation() {
        Conversation conversation = new Conversation();
        when(repository.findById(conversation.conversationId())).thenReturn(Optional.of(conversation));
        when(documentStore.findSimilar("pergunta", 4)).thenReturn(List.of("ctx"));
        when(llm.complete(anyString())).thenReturn("resposta");

        RagAnswer answer = handler.chat(conversation.conversationId(), "pergunta", null);

        assertEquals("resposta", answer.answer());
        verify(llm).complete(contains("ctx"));
        verify(repository).save(conversation);
    }

    @Test
    void chatWithoutRagContextStillCallsLlm() {
        Conversation conversation = new Conversation();
        when(repository.findById(conversation.conversationId())).thenReturn(Optional.of(conversation));
        when(documentStore.findSimilarWithFilter("p", 4, Map.of("a", "b"))).thenReturn(List.of());
        when(llm.complete(anyString())).thenReturn("via tool");

        assertEquals("via tool",
                handler.chat(conversation.conversationId(), "p", Map.of("a", "b")).answer());
    }

    @Test
    void chatUnknownConversationThrows() {
        when(repository.findById("x")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> handler.chat("x", "p", null));
    }
}
