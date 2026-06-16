package br.com.pet.adm.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object rico — imutável, com comportamento.
 * Elimina erros de somar moedas diferentes.
 */
public final class Money {

    private final BigDecimal amount;
    private final String     currency;

    public Money(BigDecimal amount, String currency) {
        if (amount == null)
            throw new IllegalArgumentException("Valor é obrigatório");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Valor não pode ser negativo");
        if (currency == null || currency.isBlank())
            throw new IllegalArgumentException("Moeda é obrigatória");
        this.amount   = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency.toUpperCase();
    }

    public static Money of(double amount, String currency) {
        return new Money(BigDecimal.valueOf(amount), currency);
    }

    public static Money zero(String currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Resultado não pode ser negativo");
        return new Money(result, this.currency);
    }

    public Money multiply(int factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
    }

    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException(
                "Moedas diferentes: " + this.currency + " e " + other.currency);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Money)) return false;
        Money m = (Money) o;
        return Objects.equals(amount, m.amount) && Objects.equals(currency, m.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return currency + " " + amount;
    }

    public BigDecimal getAmount()   { return amount; }
    public String     getCurrency() { return currency; }
}
