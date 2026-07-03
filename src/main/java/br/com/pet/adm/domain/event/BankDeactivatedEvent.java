package br.com.pet.adm.domain.event;

import lombok.Getter;

import java.util.Date;

/**
 * Domain Event — banco foi inativado.
 */
@Getter
public class BankDeactivatedEvent {

    private final String cdBank;
    private final String dsBank;
    private final Long   userId;
    private final Date   occurredAt;

    public BankDeactivatedEvent(String cdBank, String dsBank, Long userId) {
        this.cdBank     = cdBank;
        this.dsBank     = dsBank;
        this.userId     = userId;
        this.occurredAt = new Date();
    }

}
