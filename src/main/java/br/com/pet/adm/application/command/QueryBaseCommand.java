package br.com.pet.adm.application.command;

/**
 * Command — solicita uma pergunta direcionada a uma base específica.
 */
public record QueryBaseCommand(
        String baseName,
        String question
) {
    public QueryBaseCommand {
        if (baseName == null || baseName.isBlank())
            throw new IllegalArgumentException("Nome da base é obrigatório");
        if (question == null || question.isBlank())
            throw new IllegalArgumentException("Pergunta é obrigatória");
    }
}
