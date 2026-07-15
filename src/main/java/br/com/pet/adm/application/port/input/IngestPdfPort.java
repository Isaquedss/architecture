package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.IngestPdfCommand;

public interface IngestPdfPort {
    void ingest(IngestPdfCommand command);
}
