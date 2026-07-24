package br.com.pet.adm.application.command.handler;

import br.com.pet.adm.application.command.IngestDocumentCommand;
import br.com.pet.adm.application.port.input.AskQuestionPort;
import br.com.pet.adm.application.port.input.IngestDocumentPort;
import br.com.pet.adm.application.port.input.LlmPort;
import br.com.pet.adm.application.port.output.DocumentStorePort;
import br.com.pet.adm.domain.valueobject.RagAnswer;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RagHandler implements IngestDocumentPort, AskQuestionPort {

    private final DocumentStorePort documentStore;
    private final LlmPort llm;

    @Override
    public void ingest(IngestDocumentCommand command) {
        documentStore.save(command.content(), command.metadata());
    }

    @Override
    public RagAnswer ask(String question) {
        List<String> context = documentStore.findSimilar(question, 4);
        // 2. Constrói o prompt com o contexto
        String prompt = buildPrompt(question, context);

        // 3. Chama o LLM via port (sem dependência direta do Spring AI)
        String answer = llm.complete(prompt);

        return new RagAnswer(question, answer);
    }

    @Override
    public RagAnswer askWithFilter(String question, Map<String, Object> filters) {
        List<String> context = filters == null || filters.isEmpty()
                ? documentStore.findSimilar(question, 4)
                : documentStore.findSimilarWithFilter(question, 4, filters);

        return buildAnswer(question, context);
    }

    private RagAnswer buildAnswer(String question, List<String> contextChunks) {
        if (contextChunks.isEmpty())
            return new RagAnswer(question, "Não encontrei informações sobre isso.");

        String answer = llm.complete(buildPrompt(question, contextChunks));
        return new RagAnswer(question, answer);
    }

    private String buildPrompt(String question, List<String> contextChunks) {
        String context = String.join("\n\n---\n\n", contextChunks);
        return """
            Use apenas o contexto abaixo para responder à pergunta.
            Se não souber, diga "Não encontrei informações sobre isso."

            Contexto:
            %s

            Pergunta: %s
            """.formatted(context, question);
    }


}
