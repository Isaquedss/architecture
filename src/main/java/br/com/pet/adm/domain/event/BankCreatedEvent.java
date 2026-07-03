package br.com.pet.adm.domain.event;

import lombok.Getter;

import java.util.Date;

/**
 * Domain Event — algo que aconteceu no domínio (passado, imutável).
 */
@Getter
public class BankCreatedEvent {

    private final String cdBank;
    private final String dsBank;
    private final Date   occurredAt;

    public BankCreatedEvent(String cdBank, String dsBank) {
        this.cdBank     = cdBank;
        this.dsBank     = dsBank;
        this.occurredAt = new Date();
    }

}
