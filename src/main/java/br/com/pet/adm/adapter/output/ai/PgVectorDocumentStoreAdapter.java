package br.com.pet.adm.adapter.output.ai;

import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.domain.valueobject.ScoredChunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PgVectorDocumentStoreAdapter implements DocumentStorePort {

    private final VectorStore vectorStore;

    @Override
    public void save(String content, Map<String, Object> metadata) {
        var splitter = new TokenTextSplitter();
        var document = Document.builder()
                .text(content)
                .metadata(metadata)
                .build();
        var chunks = splitter.apply(List.of(document));
        vectorStore.add(chunks);
    }

    @Override
    public List<String> findSimilar(String query, int topK) {
        return vectorStore
                .similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build())
                .stream()
                .map(Document::getText)
                .toList();
    }

    @Override
    public List<String> findSimilarWithFilter(String query, int topK, Map<String, Object> filters) {
        return vectorStore
                .similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .filterExpression(buildFilter(filters))
                        .build())
                .stream()
                .map(Document::getText)
                .toList();
    }

    // ── novos métodos com score ───────────────────────────────────────────

    @Override
    public List<ScoredChunk> findSimilarWithScore(String query, int topK) {
        return vectorStore
                .similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build())
                .stream()
                .map(doc -> new ScoredChunk(
                        doc.getText(),
                        extractScore(doc)
                ))
                .toList();
    }

    @Override
    public List<ScoredChunk> findSimilarWithScoreAndFilter(String query, int topK,
                                                           Map<String, Object> filters) {
        return vectorStore
                .similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .filterExpression(buildFilter(filters))
                        .build())
                .stream()
                .map(doc -> new ScoredChunk(doc.getText(), extractScore(doc)))
                .toList();
    }

    // ── privados ──────────────────────────────────────────────────────────

    /**
     * O Spring AI armazena o score no metadata do Document com a chave "distance".
     * Como usamos COSINE_DISTANCE, convertemos: score = 1 - distance.
     */
    private double extractScore(Document doc) {
        Object raw = doc.getMetadata().get("distance");
        if (raw instanceof Number number) {
            return Math.max(0.0, 1.0 - number.doubleValue());
        }
        return 0.0;
    }

    private org.springframework.ai.vectorstore.filter.Filter.Expression buildFilter(
            Map<String, Object> filters) {
        var b = new FilterExpressionBuilder();
        var expressions = filters.entrySet().stream()
                .map(e -> b.eq(e.getKey(), e.getValue()))
                .toList();
        return (expressions.size() == 1
                ? expressions.get(0)
                : expressions.stream().reduce(b::and).orElseThrow()
        ).build();
    }
}