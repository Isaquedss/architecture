package br.com.pet.adm.application.command;

import java.util.Map;

/**
 * Command — solicita ingestão de documento numa base específica.
 */
public record IngestToBaseCommand(
        String              baseName,
        String              content,
        Map<String, Object> metadata
) {
    public IngestToBaseCommand {
        if (baseName == null || baseName.isBlank())
            throw new IllegalArgumentException("Nome da base é obrigatório");
        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Conteúdo é obrigatório");
        if (metadata == null) metadata = Map.of();
    }
}
