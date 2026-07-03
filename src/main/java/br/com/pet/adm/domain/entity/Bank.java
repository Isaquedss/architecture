package br.com.pet.adm.domain.entity;

import br.com.pet.adm.domain.valueobject.StatusFlag;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * Entity rica do Domain — zero anotações JPA ou Spring.
 * Identidade definida pelo cdBank.
 * Contém regras de negócio do banco.
 */
@Getter
public class Bank {

    private final String     cdBank;          // identidade, nunca muda
    private String           dsBank;
    private StatusFlag status;
    private final Date       creationDate;
    private Date             changeDate;
    private Date             inactivationDate;
    private Long             userId;

    public Bank(String cdBank, String dsBank) {
        validate(cdBank, dsBank);
        this.cdBank       = cdBank;
        this.dsBank       = dsBank;
        this.status       = StatusFlag.ACTIVE;  // regra: nasce ativo
        this.creationDate = new Date();
        this.changeDate   = new Date();
    }

    // construtor para reconstituição (vindo do banco de dados)
    public Bank(String cdBank, String dsBank, StatusFlag status,
                Date creationDate, Date changeDate, Date inactivationDate, Long userId) {
        this.cdBank           = cdBank;
        this.dsBank           = dsBank;
        this.status           = status;
        this.creationDate     = creationDate;
        this.changeDate       = changeDate;
        this.inactivationDate = inactivationDate;
        this.userId           = userId;
    }

    // --- comportamentos de negócio ---

    public void rename(String newName) {
        if (newName == null || newName.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");
        this.dsBank     = newName;
        this.changeDate = new Date();
    }

    public void deactivate() {
        if (this.status == StatusFlag.INACTIVE)
            throw new IllegalStateException("Banco já está inativo");
        this.status           = StatusFlag.INACTIVE;
        this.inactivationDate = new Date();
        this.changeDate       = new Date();
    }

    public void reactivate() {
        if (this.status == StatusFlag.ACTIVE)
            throw new IllegalStateException("Banco já está ativo");
        this.status           = StatusFlag.ACTIVE;
        this.inactivationDate = null;
        this.changeDate       = new Date();
    }

    public boolean isActive() {
        return this.status == StatusFlag.ACTIVE;
    }

    // --- identidade ---

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bank)) return false;
        return Objects.equals(cdBank, ((Bank) o).cdBank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cdBank);
    }

    private void validate(String cdBank, String dsBank) {
        if (cdBank == null || cdBank.isBlank())
            throw new IllegalArgumentException("Código é obrigatório");
        if (dsBank == null || dsBank.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
    }

}
