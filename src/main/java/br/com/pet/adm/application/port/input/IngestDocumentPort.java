package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.IngestDocumentCommand;

public interface IngestDocumentPort {
    void ingest(IngestDocumentCommand command);
}
