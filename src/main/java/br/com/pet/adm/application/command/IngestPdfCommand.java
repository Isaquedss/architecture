package br.com.pet.adm.application.command;

import java.io.InputStream;
import java.util.Map;

public record IngestPdfCommand(InputStream inputStream,
                               String filename,
                               Map<String, Object> metadata) {
    public IngestPdfCommand {
        if (inputStream == null)
            throw new IllegalArgumentException("InputStream é obrigatório");
        if (filename == null || filename.isBlank())
            throw new IllegalArgumentException("Nome do arquivo é obrigatório");
        if (metadata == null) metadata = Map.of();
    }
}