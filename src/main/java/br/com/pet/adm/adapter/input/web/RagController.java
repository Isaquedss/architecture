package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.IngestRequest;
import br.com.pet.adm.application.command.IngestDocumentCommand;
import br.com.pet.adm.application.command.IngestPdfCommand;
import br.com.pet.adm.application.port.input.AskQuestionPort;
import br.com.pet.adm.application.port.input.IngestDocumentPort;
import br.com.pet.adm.application.port.input.IngestPdfPort;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
@Tag(name = "RAG", description = "Ingestão de documentos e perguntas via IA")
public class RagController {

    private final IngestDocumentPort ingestDocumentPort;
    private final IngestPdfPort ingestPdfPort;
    private final AskQuestionPort askQuestionPort;

    @PostMapping("/ingest")
    public ResponseEntity<String> ingest(@RequestBody IngestRequest request) {
        ingestDocumentPort.ingest(
                new IngestDocumentCommand(request.content(), request.metadata())
        );
        return ResponseEntity.ok("Documento indexado com sucesso!");
    }

    @PostMapping(value = "/ingest/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> ingestPdf(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "source", required = false) String source) throws IOException {

        ingestPdfPort.ingest(new IngestPdfCommand(
                file.getInputStream(),
                file.getOriginalFilename(),
                source != null ? Map.of("source", source) : Map.of()
        ));

        return ResponseEntity.ok("PDF '%s' indexado com sucesso!".formatted(file.getOriginalFilename()));
    }

    @GetMapping("/ask")
    public ResponseEntity<RagAnswer> ask(
            @RequestParam String question,
            @RequestParam(required = false) Map<String, String> allParams) {

        Map<String, Object> filters = allParams.entrySet().stream()
                .filter(e -> !e.getKey().equals("question"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        RagAnswer answer = filters.isEmpty()
                ? askQuestionPort.ask(question)
                : askQuestionPort.askWithFilter(question, filters);

        return ResponseEntity.ok(answer);
    }
}
