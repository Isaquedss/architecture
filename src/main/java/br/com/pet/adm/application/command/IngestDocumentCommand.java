package br.com.pet.adm.application.command;

import java.util.Map;

public record IngestDocumentCommand(String content, Map<String, Object> metadata) {

    public IngestDocumentCommand {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
        if (metadata == null) metadata = Map.of();
    }
}
