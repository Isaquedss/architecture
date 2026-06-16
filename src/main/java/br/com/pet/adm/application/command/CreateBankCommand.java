package br.com.pet.adm.application.command;

/**
 * CQRS Command — intenção de criar um banco.
 * Imutável, validado no construtor.
 */
public record CreateBankCommand(
    String cdBank,
    String dsBank,
    Long   userId
) {
    public CreateBankCommand {
        if (cdBank == null || cdBank.isBlank())
            throw new IllegalArgumentException("Código é obrigatório");
        if (dsBank == null || dsBank.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
    }
}
