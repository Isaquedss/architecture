package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.ChatRequest;
import br.com.pet.adm.application.port.input.AgentChatPort;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rag/agent-chat")
@RequiredArgsConstructor
@Tag(name = "Agent Chat", description = "Chat com histórico, RAG e tool calling (consultas de bancos e pedidos)")
public class AgentChatController {

    private final AgentChatPort agentChatPort;

    // inicia uma nova conversa
    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> start() {
        String conversationId = agentChatPort.startConversation();
        return ResponseEntity.ok(Map.of("conversationId", conversationId));
    }

    // envia uma mensagem dentro de uma conversa
    @PostMapping("/{conversationId}")
    public ResponseEntity<RagAnswer> chat(
            @PathVariable String conversationId,
            @RequestBody ChatRequest request) {

        RagAnswer answer = agentChatPort.chat(
                conversationId,
                request.question(),
                request.filters()
        );
        return ResponseEntity.ok(answer);
    }

    // limpa o histórico
    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> clear(@PathVariable String conversationId) {
        agentChatPort.clearConversation(conversationId);
        return ResponseEntity.noContent().build();
    }

}
