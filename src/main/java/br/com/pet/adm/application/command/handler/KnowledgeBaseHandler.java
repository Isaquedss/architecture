package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.QueryBaseCommand;
import br.com.pet.adm.application.port.input.IngestToBasePort;
import br.com.pet.adm.application.command.IngestToBaseCommand;
import br.com.pet.adm.application.port.input.LlmPort;
import br.com.pet.adm.application.port.input.QueryBasePort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.application.port.output.KnowledgeBaseRepositoryPort;
import br.com.pet.adm.domain.valueobject.KnowledgeBase;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class KnowledgeBaseHandler implements IngestToBasePort, QueryBasePort {


    private static final String BASE_METADATA_KEY = "knowledge_base";

    private final DocumentStorePort documentStore;
    private final LlmPort llm;
    private final KnowledgeBaseRepositoryPort baseRepository;

    @Override
    public void ingest(IngestToBaseCommand command) {
        // Valida se a base existe
        KnowledgeBase base = baseRepository.findByName(command.baseName())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Base de conhecimento não encontrada: " + command.baseName()));

        // Adiciona o nome da base como metadata
        Map<String, Object> metadata = new HashMap<>(command.metadata());
        metadata.put(BASE_METADATA_KEY, base.name());

        documentStore.save(command.content(), metadata);
    }

    @Override
    public RagAnswer query(QueryBaseCommand command) {
        // Valida se a base existe
        baseRepository.findByName(command.baseName())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Base de conhecimento não encontrada: " + command.baseName()));

        // Busca apenas na base especificada
        List<String> context = documentStore.findSimilarWithFilter(
                command.question(),
                4,
                Map.of(BASE_METADATA_KEY, command.baseName())
        );

        if (context.isEmpty())
            return new RagAnswer(command.question(), "Não encontrei informações sobre isso na base '%s'."
                    .formatted(command.baseName()));

        String answer = llm.complete(buildPrompt(command.question(), context));
        return new RagAnswer(command.question(), answer);
    }

    @Override
    public List<KnowledgeBase> listBases() {
        return baseRepository.findAll();
    }

    // ── privado ──────────────────────────────────────────────────────────

    private String buildPrompt(String question, List<String> contextChunks) {
        String context = String.join("\n\n---\n\n", contextChunks);
        return """
            Use apenas o contexto abaixo para responder à pergunta.
            Se não souber, diga "Não encontrei informações sobre isso."

            Contexto:
            %s

            Pergunta: %s
            """.formatted(context, question);
    }
}
