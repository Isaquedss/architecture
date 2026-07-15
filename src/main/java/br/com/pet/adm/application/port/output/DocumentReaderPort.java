package br.com.pet.adm.application.port.output;

import java.io.InputStream;
import java.util.List;

/**
 * Driven Port — abstrai a leitura/extração de texto de arquivos.
 */
public interface DocumentReaderPort {
    List<String> extract(InputStream inputStream, String filename);
}
