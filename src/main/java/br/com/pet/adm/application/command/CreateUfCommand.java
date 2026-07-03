package br.com.pet.adm.application.command;

public record CreateUfCommand(
        String ufName
) {
    public CreateUfCommand {
        if (ufName == null || ufName.isBlank())
            throw new IllegalArgumentException("Nome da UF é obrigatório");
    }
}
