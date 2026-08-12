package br.com.pet.adm.domain.valueobject;

/**
 * Value Object — representa um chunk recuperado do vector store com seu score de similaridade.
 * Score varia de 0.0 (nenhuma similaridade) a 1.0 (idêntico).
 */
public record ScoredChunk(String content, double score) {

    public ScoredChunk {
        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Conteúdo do chunk é obrigatório");
        if (score < 0.0 || score > 1.0)
            throw new IllegalArgumentException("Score deve estar entre 0.0 e 1.0");
    }

    /**
     * Retorna true se o chunk for relevante o suficiente para ser usado no prompt.
     */
    public boolean isRelevant(double threshold) {
        return score >= threshold;
    }
}