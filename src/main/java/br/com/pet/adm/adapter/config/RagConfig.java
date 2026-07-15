package br.com.pet.adm.adapter.config;

import br.com.pet.adm.adapter.output.ai.OllamaLlmAdapter;
import br.com.pet.adm.adapter.output.ai.PgVectorDocumentStoreAdapter;
import br.com.pet.adm.adapter.output.ai.TikaDocumentReaderAdapter;
import br.com.pet.adm.application.command.handler.PdfIngestionHandler;
import br.com.pet.adm.application.command.handler.RagHandler;
import br.com.pet.adm.application.port.input.AskQuestionPort;
import br.com.pet.adm.application.port.input.IngestDocumentPort;
import br.com.pet.adm.application.port.input.IngestPdfPort;
import br.com.pet.adm.application.port.input.LlmPort;
import br.com.pet.adm.application.port.output.DocumentReaderPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
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
}
