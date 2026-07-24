package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.port.input.ChatPort;
import br.com.pet.adm.application.port.input.LlmPort;
import br.com.pet.adm.application.port.output.ConversationRepositoryPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.domain.entity.Conversation;
import br.com.pet.adm.domain.valueobject.ConversationMessage;
import br.com.pet.adm.domain.valueobject.RagAnswer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler — orquestra o chat com histórico + RAG.
 * Java puro, sem @Component.
 */
public class ChatHandler implements ChatPort {

    private static final int MAX_HISTORY_MESSAGES = 6; // últimas 6 mensagens no contexto

    private final ConversationRepositoryPort conversationRepository;
    private final DocumentStorePort documentStore;
    private final LlmPort llm;

    public ChatHandler(ConversationRepositoryPort conversationRepository,
                       DocumentStorePort documentStore,
                       LlmPort llm) {
        this.conversationRepository = conversationRepository;
        this.documentStore          = documentStore;
        this.llm                    = llm;
    }

    @Override
    public String startConversation() {
        Conversation conversation = new Conversation();
        conversationRepository.save(conversation);
        return conversation.conversationId();
    }

    @Override
    public RagAnswer chat(String conversationId, String question, Map<String, Object> filters) {
        // 1. Recupera ou cria a conversa
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Conversa não encontrada: " + conversationId));

        // 2. Busca contexto relevante no vector store
        List<String> ragContext = filters == null || filters.isEmpty()
                ? documentStore.findSimilar(question, 4)
                : documentStore.findSimilarWithFilter(question, 4, filters);

        // 3. Monta o histórico das últimas mensagens
        String history = buildHistory(conversation.getLastMessages(MAX_HISTORY_MESSAGES));

        // 4. Constrói o prompt com histórico + contexto RAG + pergunta atual
        String prompt = buildPrompt(history, ragContext, question);

        // 5. Chama o LLM
        String answer = ragContext.isEmpty()
                ? "Não encontrei informações sobre isso."
                : llm.complete(prompt);

        // 6. Salva as mensagens no histórico
        conversation.addMessage(ConversationMessage.user(question));
        conversation.addMessage(ConversationMessage.assistant(answer));
        conversationRepository.save(conversation);

        return new RagAnswer(question, answer);
    }

    @Override
    public void clearConversation(String conversationId) {
        conversationRepository.deleteById(conversationId);
    }

    // ── privado ──────────────────────────────────────────────────────────

    private String buildHistory(List<ConversationMessage> messages) {
        if (messages.isEmpty()) return "";

        return messages.stream()
                .map(m -> m.role().name() + ": " + m.content())
                .collect(Collectors.joining("\n"));
    }

    private String buildPrompt(String history, List<String> ragContext, String question) {
        String context = String.join("\n\n---\n\n", ragContext);
        String historySection = history.isBlank() ? "" : """

            Histórico da conversa:
            %s
            """.formatted(history);

        return """
            Use apenas o contexto abaixo para responder à pergunta.
            Se não souber, diga "Não encontrei informações sobre isso."

            Contexto:
            %s
            %s
            Pergunta atual: %s
            """.formatted(context, historySection, question);
    }
}