package br.com.pet.adm.domain.exception;

/**
 * Exceção de negócio — pertence ao Domain.
 */
public class BankNotFoundException extends RuntimeException {

    public BankNotFoundException(String cdBank) {
        super("Banco não encontrado: " + cdBank);
    }
}
