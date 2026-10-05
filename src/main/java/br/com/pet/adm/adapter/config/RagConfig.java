package br.com.pet.adm.adapter.config;

import br.com.pet.adm.adapter.output.ai.OllamaLlmAdapter;
import br.com.pet.adm.adapter.output.ai.PgVectorDocumentStoreAdapter;
import br.com.pet.adm.adapter.output.ai.TikaDocumentReaderAdapter;
import br.com.pet.adm.adapter.output.conversation.PostgresConversationRepositoryAdapter;
import br.com.pet.adm.adapter.output.conversation.repository.ConversationJpaRepository;
import br.com.pet.adm.adapter.output.knowledge.KnowledgeBaseJpaRepository;
import br.com.pet.adm.adapter.output.knowledge.PostgresKnowledgeBaseRepositoryAdapter;
import br.com.pet.adm.adapter.input.ai.tools.BankQueryTools;
import br.com.pet.adm.adapter.input.ai.tools.OrderQueryTools;
import br.com.pet.adm.application.command.handler.AgentChatHandler;
import br.com.pet.adm.application.command.handler.ChatHandler;
import br.com.pet.adm.application.query.handler.FindOrderByIdHandler;
import br.com.pet.adm.application.query.handler.FindOrdersByCustomerHandler;
import br.com.pet.adm.application.query.handler.OrderQueryHandler;
import br.com.pet.adm.application.command.handler.KnowledgeBaseHandler;
import br.com.pet.adm.application.command.handler.PdfIngestionHandler;
import br.com.pet.adm.application.command.handler.RagHandler;
import br.com.pet.adm.application.port.input.*;
import br.com.pet.adm.application.port.output.ConversationRepositoryPort;
import br.com.pet.adm.application.port.output.DocumentReaderPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.application.port.output.KnowledgeBaseRepositoryPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RagConfig {

    @Bean
    public DocumentStorePort documentStorePort(VectorStore vectorStore) {
        return new PgVectorDocumentStoreAdapter(vectorStore);
    }

    @Bean
    @Primary
    public LlmPort llmPort(ChatClient.Builder builder) {
        return new OllamaLlmAdapter(builder.build());
    }

    // ── Agente (tool calling) ─────────────────────────────────────────────

    @Bean
    public BankQueryTools bankQueryTools(FindAllBanksPort findAllBanksPort) {
        return new BankQueryTools(findAllBanksPort);
    }

    @Bean
    public OrderQueryPort orderQueryPort(FindOrderByIdHandler findOrderByIdHandler,
                                         FindOrdersByCustomerHandler findOrdersByCustomerHandler) {
        return new OrderQueryHandler(findOrderByIdHandler, findOrdersByCustomerHandler);
    }

    @Bean
    public OrderQueryTools orderQueryTools(OrderQueryPort orderQueryPort) {
        return new OrderQueryTools(orderQueryPort);
    }

    @Bean
    public LlmPort agentLlmPort(ChatClient.Builder builder,
                                BankQueryTools bankQueryTools,
                                OrderQueryTools orderQueryTools) {
        return new OllamaLlmAdapter(builder.defaultTools(bankQueryTools, orderQueryTools).build());
    }

    @Bean
    public AgentChatPort agentChatPort(ConversationRepositoryPort conversationRepository,
                                       DocumentStorePort documentStore,
                                       @Qualifier("agentLlmPort") LlmPort agentLlm) {
        return new AgentChatHandler(conversationRepository, documentStore, agentLlm);
    }

    @Bean
    public AskQuestionPort askQuestionPort(DocumentStorePort documentStore, LlmPort llm) {
        return new RagHandler(documentStore, llm);
    }

    @Bean
    public IngestDocumentPort ingestDocumentPort(DocumentStorePort documentStore, LlmPort llm) {
        return new RagHandler(documentStore, llm);
    }

    // ── PDF ──────────────────────────────────────────────────────────────

    @Bean
    public DocumentReaderPort documentReaderPort() {
        return new TikaDocumentReaderAdapter();
    }

    @Bean
    public IngestPdfPort ingestPdfPort(DocumentReaderPort documentReader,
                                       DocumentStorePort documentStore) {
        return new PdfIngestionHandler(documentReader, documentStore);
    }

    // ── Chat com histórico ────────────────────────────────────────────────

    @Bean
    public ConversationRepositoryPort conversationRepositoryPort(
            ConversationJpaRepository jpaRepository) {
        return new PostgresConversationRepositoryAdapter(jpaRepository);
    }

    @Bean
    public ChatPort chatPort(ConversationRepositoryPort conversationRepository,
                             DocumentStorePort documentStore,
                             LlmPort llm) {
        return new ChatHandler(conversationRepository, documentStore, llm);
    }

    // ── Múltiplas bases ───────────────────────────────────────────────────

    @Bean
    public KnowledgeBaseRepositoryPort knowledgeBaseRepositoryPort(
            KnowledgeBaseJpaRepository jpaRepository) {
        return new PostgresKnowledgeBaseRepositoryAdapter(jpaRepository);
    }

    @Bean
    public IngestToBasePort ingestToBasePort(DocumentStorePort documentStore,
                                             LlmPort llm,
                                             KnowledgeBaseRepositoryPort baseRepository) {
        return new KnowledgeBaseHandler(documentStore, llm, baseRepository);
    }

    @Bean
    public QueryBasePort queryBasePort(DocumentStorePort documentStore,
                                       LlmPort llm,
                                       KnowledgeBaseRepositoryPort baseRepository) {
        return new KnowledgeBaseHandler(documentStore, llm, baseRepository);
    }
}
