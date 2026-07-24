package br.com.pet.adm.application.port.input;

import br.com.pet.adm.domain.valueobject.RagAnswer;

import java.util.Map;

public interface AskQuestionPort {

    RagAnswer ask(String question);

    RagAnswer askWithFilter(String question, Map<String, Object> filters);
}
