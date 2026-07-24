package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.ChatRequest;
import br.com.pet.adm.application.port.input.ChatPort;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rag/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Chat com histórico de conversa e RAG")
public class ChatController {

    private final ChatPort chatPort;

    // inicia uma nova conversa
    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> start() {
        String conversationId = chatPort.startConversation();
        return ResponseEntity.ok(Map.of("conversationId", conversationId));
    }

    // envia uma mensagem dentro de uma conversa
    @PostMapping("/{conversationId}")
    public ResponseEntity<RagAnswer> chat(
            @PathVariable String conversationId,
            @RequestBody ChatRequest request) {

        RagAnswer answer = chatPort.chat(
                conversationId,
                request.question(),
                request.filters()
        );
        return ResponseEntity.ok(answer);
    }

    // limpa o histórico
    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> clear(@PathVariable String conversationId) {
        chatPort.clearConversation(conversationId);
        return ResponseEntity.noContent().build();
    }

}
