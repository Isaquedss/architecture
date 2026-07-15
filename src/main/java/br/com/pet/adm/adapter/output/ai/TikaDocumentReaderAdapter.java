package br.com.pet.adm.adapter.output.ai;

import br.com.pet.adm.application.port.output.DocumentReaderPort;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;
import java.util.List;

/**
 * Driven Adapter — extrai texto de PDFs usando Apache Tika via Spring AI.
 */
public class TikaDocumentReaderAdapter implements DocumentReaderPort {

    @Override
    public List<String> extract(InputStream inputStream, String filename) {
        var resource = new InputStreamResource(inputStream, filename);
        var reader   = new TikaDocumentReader(resource);

        return reader.get()
                .stream()
                .map(Document::getText)
                .filter(text -> text != null && !text.isBlank())
                .toList();
    }
}
