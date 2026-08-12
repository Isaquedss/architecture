package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.IngestDocumentCommand;
import br.com.pet.adm.application.port.input.AskQuestionPort;
import br.com.pet.adm.application.port.input.IngestDocumentPort;
import br.com.pet.adm.application.port.input.LlmPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import br.com.pet.adm.domain.valueobject.ScoredChunk;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RagHandler implements IngestDocumentPort, AskQuestionPort {

    /**
     * Threshold mínimo de similaridade para considerar um chunk relevante.
     * Chunks com score abaixo disso são ignorados, evitando alucinações.
     * Valor recomendado: 0.70 (70% de similaridade coseno).
     */
    private static final double RELEVANCE_THRESHOLD = 0.70;

    private final DocumentStorePort documentStore;
    private final LlmPort llm;

    @Override
    public void ingest(IngestDocumentCommand command) {
        documentStore.save(command.content(), command.metadata());
    }

    @Override
    public RagAnswer ask(String question) {
        List<ScoredChunk> scoredChunks = documentStore.findSimilarWithScore(question, 4);
        return buildAnswerFromScored(question, scoredChunks);
    }

    @Override
    public RagAnswer askWithFilter(String question, Map<String, Object> filters) {
        List<ScoredChunk> scoredChunks = filters == null || filters.isEmpty()
                ? documentStore.findSimilarWithScore(question, 4)
                : documentStore.findSimilarWithScoreAndFilter(question, 4, filters);

        return buildAnswerFromScored(question, scoredChunks);
    }

    // ── privados ──────────────────────────────────────────────────────────

    private RagAnswer buildAnswerFromScored(String question, List<ScoredChunk> scoredChunks) {
        // Filtra apenas os chunks com score acima do threshold
        List<String> relevantChunks = scoredChunks.stream()
                .filter(chunk -> chunk.isRelevant(RELEVANCE_THRESHOLD))
                .map(ScoredChunk::content)
                .toList();

        if (relevantChunks.isEmpty()) {
            // Nenhum chunk passou pelo threshold — evita alucinação
            return new RagAnswer(question, "Não encontrei informações relevantes sobre isso.");
        }

        String answer = llm.complete(buildPrompt(question, relevantChunks));
        return new RagAnswer(question, answer);
    }

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