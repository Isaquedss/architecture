package br.com.pet.adm.adapter.config;

import br.com.pet.adm.adapter.output.ai.OllamaLlmAdapter;
import br.com.pet.adm.adapter.output.ai.PgVectorDocumentStoreAdapter;
import br.com.pet.adm.adapter.output.ai.TikaDocumentReaderAdapter;
import br.com.pet.adm.adapter.output.conversation.InMemoryConversationRepositoryAdapter;
import br.com.pet.adm.adapter.output.knowledge.KnowledgeBaseJpaRepository;
import br.com.pet.adm.adapter.output.knowledge.PostgresKnowledgeBaseRepositoryAdapter;
import br.com.pet.adm.application.command.handler.ChatHandler;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {

    @Bean
    public DocumentStorePort documentStorePort(VectorStore vectorStore) {
        return new PgVectorDocumentStoreAdapter(vectorStore);
    }

    @Bean
    public LlmPort llmPort(ChatClient.Builder builder) {
        return new OllamaLlmAdapter(builder.build());
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
    public ConversationRepositoryPort conversationRepositoryPort() {
        return new InMemoryConversationRepositoryAdapter();
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
