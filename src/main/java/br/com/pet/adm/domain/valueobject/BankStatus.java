package br.com.pet.adm.domain.valueobject;

/**
 * Value Object — definido pelo valor, imutável.
 * Substitui o Integer activeFlag espalhado pelo código.
 */
public enum BankStatus {

    ACTIVE, INACTIVE;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public static BankStatus from(Integer flag) {
        if (flag == null || flag == 0) return INACTIVE;
        return ACTIVE;
    }

    public Integer toFlag() {
        return this == ACTIVE ? 1 : 0;
    }
}
