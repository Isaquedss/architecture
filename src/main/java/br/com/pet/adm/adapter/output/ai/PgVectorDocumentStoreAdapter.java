package br.com.pet.adm.adapter.output.ai;

import br.com.pet.adm.application.port.output.DocumentStorePort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;

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
        var chunks   = splitter.apply(List.of(document));
        vectorStore.add(chunks);
    }

    @Override
    public List<String> findSimilar(String query, int topK) {
        return vectorStore
                .similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
                )
                .stream()
                .map(Document::getText)
                .toList();
    }
}
