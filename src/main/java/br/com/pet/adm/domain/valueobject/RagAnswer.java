package br.com.pet.adm.domain.valueobject;

public record RagAnswer(String question, String answer) {

    public RagAnswer {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question cannot be null or blank");
        }
        if (answer == null || answer.isBlank()) {
            throw new IllegalArgumentException("Answer cannot be null or blank");
        }
    }
}
