package br.com.pet.adm.application.command;

/**
 * CQRS Command — intenção de inativar um banco.
 */
public record DeactivateBankCommand(
    String cdBank,
    Long   userId
) {
    public DeactivateBankCommand {
        if (cdBank == null || cdBank.isBlank())
            throw new IllegalArgumentException("Código é obrigatório");
    }
}
