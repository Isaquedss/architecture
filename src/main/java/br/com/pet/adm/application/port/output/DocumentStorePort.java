package br.com.pet.adm.application.port.output;

import br.com.pet.adm.domain.valueobject.ScoredChunk;

import java.util.List;
import java.util.Map;

public interface DocumentStorePort {

    void save(String content, Map<String, Object> metadata);


    List<String> findSimilar(String query, int topK);

    List<String> findSimilarWithFilter(String query, int topK, Map<String, Object> filters);

    // ── novos métodos com score ───────────────────────────────────────────

    /**
     * Busca os topK chunks mais similares, retornando também o score de cada um.
     */
    List<ScoredChunk> findSimilarWithScore(String query, int topK);

    /**
     * Busca com filtro de metadata + score.
     */
    List<ScoredChunk> findSimilarWithScoreAndFilter(String query, int topK, Map<String, Object> filters);
}
