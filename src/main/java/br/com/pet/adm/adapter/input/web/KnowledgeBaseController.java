package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.IngestRequest;
import br.com.pet.adm.application.command.IngestToBaseCommand;
import br.com.pet.adm.application.command.QueryBaseCommand;
import br.com.pet.adm.application.port.input.IngestToBasePort;
import br.com.pet.adm.application.port.input.QueryBasePort;
import br.com.pet.adm.domain.valueobject.KnowledgeBase;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rag/bases")
@RequiredArgsConstructor
@Tag(name = "Knowledge Bases", description = "Gerenciamento de bases de conhecimento")
public class KnowledgeBaseController {

    private final IngestToBasePort ingestToBasePort;
    private final QueryBasePort queryBasePort;

    // lista todas as bases disponíveis
    @GetMapping
    public ResponseEntity<List<KnowledgeBase>> listBases() {
        return ResponseEntity.ok(ingestToBasePort.listBases());
    }

    // indexa documento em uma base específica
    @PostMapping("/{baseName}/ingest")
    public ResponseEntity<String> ingest(
            @PathVariable String baseName,
            @RequestBody IngestRequest request) {

        ingestToBasePort.ingest(new IngestToBaseCommand(
                baseName,
                request.content(),
                request.metadata()
        ));
        return ResponseEntity.ok("Documento indexado na base '%s' com sucesso!".formatted(baseName));
    }

    // pergunta direcionada a uma base específica
    @GetMapping("/{baseName}/ask")
    public ResponseEntity<RagAnswer> ask(
            @PathVariable String baseName,
            @RequestParam String question) {

        return ResponseEntity.ok(
                queryBasePort.query(new QueryBaseCommand(baseName, question))
        );
    }

}