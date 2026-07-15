package br.com.pet.adm.application.port.output;

import java.util.List;
import java.util.Map;

public interface DocumentStorePort {

    void save(String content, Map<String, Object> metadata);

    List<String> findSimilar(String query, int topK);
}
