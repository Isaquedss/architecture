package br.com.pet.adm.application.port.input;

import br.com.pet.adm.domain.valueobject.RagAnswer;

public interface AskQuestionPort {
    RagAnswer ask(String question);
}
