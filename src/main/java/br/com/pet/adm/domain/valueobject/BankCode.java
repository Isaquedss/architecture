package br.com.pet.adm.domain.valueobject;

import java.util.Objects;

/**
 * Value Object — encapsula e valida o código do banco.
 * Substitui String cdBank crua sem validação.
 */
public final class BankCode {

    private final String value;

    public BankCode(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Código do banco é obrigatório");
        if (!value.matches("\\d{3}"))
            throw new IllegalArgumentException("Código do banco deve ter 3 dígitos numéricos");
        this.value = value;
    }

    public static BankCode of(String value) {
        return new BankCode(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BankCode)) return false;
        return Objects.equals(value, ((BankCode) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
