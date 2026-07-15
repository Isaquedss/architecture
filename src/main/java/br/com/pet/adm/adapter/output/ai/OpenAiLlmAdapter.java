package br.com.pet.adm.adapter.output.ai;

import br.com.pet.adm.application.port.input.LlmPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;

@RequiredArgsConstructor
public class OpenAiLlmAdapter implements LlmPort {

    private final ChatClient chatClient;

    @Override
    public String complete(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
