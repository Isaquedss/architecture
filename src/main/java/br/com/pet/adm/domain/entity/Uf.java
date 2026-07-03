package br.com.pet.adm.domain.entity;

import br.com.pet.adm.domain.valueobject.StatusFlag;
import lombok.Getter;

import java.util.Date;

/**
 * Entity rica do Domain — zero anotações JPA ou Spring.
 * Identidade definida pelo cdBank.
 * Contém regras de negócio do banco.
 */
@Getter
public class Uf {

    private Long             id;
    private String           ufName;
    private StatusFlag       status;
    private final Date       creationDate;
    private Date             changeDate;
    private Date             inactivationDate;
    private Long             userId;

    public Uf(String ufName) {
        validate(ufName);
        this.ufName = ufName;
        this.status       = StatusFlag.ACTIVE;  // regra: nasce ativo
        this.creationDate = new Date();
        this.changeDate   = new Date();
    }

    // construtor para reconstituição (vindo do banco de dados)
    public Uf(Long id,
              String ufName,
              StatusFlag status,
              Date creationDate,
              Date changeDate,
              Date inactivationDate,
              Long userId) {

        this.id = id;
        this.ufName = ufName;
        this.status = status;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
        this.inactivationDate = inactivationDate;
        this.userId = userId;
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank())
            throw new IllegalArgumentException("Nome UF não pode ser vazio");
        this.ufName     = newName;
        this.changeDate = new Date();
    }

    public void deactivate() {
        if (this.status == StatusFlag.INACTIVE)
            throw new IllegalStateException("UF já está inativo");
        this.status           = StatusFlag.INACTIVE;
        this.inactivationDate = new Date();
        this.changeDate       = new Date();
    }

    public void reactivate() {
        if (this.status == StatusFlag.ACTIVE)
            throw new IllegalStateException("UF já está ativo");
        this.status           = StatusFlag.ACTIVE;
        this.inactivationDate = null;
        this.changeDate       = new Date();
    }

    public boolean isActive() {
        return this.status == StatusFlag.ACTIVE;
    }


    private void validate(String ufName) {
        if (ufName == null || ufName.isBlank())
            throw new IllegalArgumentException("Nome UF é obrigatório");
    }
}
