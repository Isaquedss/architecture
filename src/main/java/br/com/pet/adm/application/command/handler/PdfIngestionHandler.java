package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.IngestPdfCommand;
import br.com.pet.adm.application.port.input.IngestPdfPort;
import br.com.pet.adm.application.port.output.DocumentReaderPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PdfIngestionHandler implements IngestPdfPort {

    private final DocumentReaderPort documentReader;
    private final DocumentStorePort documentStore;

    @Override
    public void ingest(IngestPdfCommand  command) {
        List<String> chunks =   documentReader.extract(command.inputStream(), command.filename());

        if (chunks.isEmpty()) {
            throw new IllegalArgumentException("O arquivo PDF não contém texto extraível.");
        }

        chunks.forEach(chunk -> {
            Map<String, Object> metadata = new HashMap<>(command.metadata());
            metadata.put("filename", command.filename());
            documentStore.save(chunk, metadata);
        });
    }
}
